// Authors: SEALS (Jessica, Melissa, Tracy, Will)

// *****************************************************************************
// *****************************************************************************
// **** Grid
// *****************************************************************************
// *****************************************************************************

public class Grid {
  private int numIntersections;
  // The 2D representations of intersections.
  private Intersection[][] grid;
  private IntersectionController[] intersectionControllers;

  // Constructor
  // @param numIntersections: the number of intersections in one direction.
  public Grid (int numIntersections) {
    this.numIntersections = numIntersections;
    grid = new Intersection[numIntersections][numIntersections];
    intersectionControllers = 
      new IntersectionController[numIntersections * numIntersections];

    // Construct instances of intersection and populate the grid.
    int intersection_id = numIntersections * numIntersections;
    for (int row = 1; row < numIntersections; row++) {
      for (int col = 1; col < numIntersections; col++) {
        intersection = new Intersection(intersection_id,
          numIntersections - row - 1, col - 1);
        grid[row - 1][col - 1] = intersection;

        // Construct a corresponding intersection controller.
        intersectionControllers[intersection_id - 1] = 
          new IntersectionController(intersection);
        intersection_id--;
      }
    } // end of for (each row and column of grid)

    connectIntersections();
  }

  private void connectIntersections() {
    for (int row = 1; row <= numIntersections; row++) {
      for (int col = 1; col <= numIntersections; col ++) {
        Intersection intersection = grid[row-1][col-1];
        Intersection[] nextIntersections = new Intersection[4];
        // Southward next intersection
        if (row + 1 <= numIntersections) {
          nextIntersections[0] = grid[row][col - 1];
        } else {
          nextIntersections[0] = null;
        }

        // Eastward next intersection
        if (col - 1 >= 1) {
          nextIntersections[1] = grid[row - 1][col - 2];
        } else {
          nextIntersections[1] = null;
        }

        // Northward next intersection
        if (row - 1 >= 1) {
          nextIntersections[2] = grid[row - 2][col - 1];
        } else {
          nextIntersections[2] = null;
        }

        // Westward next intersection
        if (col + 1 <= numIntersections) {
          nextIntersections[3] = grid[row - 1][col];
        } else {
          nextIntersections[3] = null;
        }

        intersection.setNextIntersections(nextIntersections);
      }
    } // end of for (each row and column of grid)
  }

  public void update() {
    for (IntersectionController controller : intersectionControllers) {
      controller.update();
    } // end of for (each controller in intersectionControllers)
  }
  
  public String toString() {
    String summary;
    for (int i = 1; i <= numIntersections; i++) {
      for (int j = 1; j <= numIntersections; j++) {
        summary.append(intersection[i,j].toString());
      }
    } // end of for (each intersection in grid)
    return summary;
  }
}



// The class name: Grid
// Responsibility:
// 1. Receive message from Simulation instance to construct the instance of Grid
//    with information about the number of Intersection instances
// 2. Initialize 2D array of Intersection instances, sending message to each
//    instance with an integer ID representing the Intersection
// 3. Initialize an instance of IntersectionController for each Intersection with
//    an integer ID representing its corresponding Intersection instance
// 4. Receive message from Simulation instance to update the Intersection instances
//    at each time-step by doing (4) below
// 5. Send message to each IntersectionController instance to update the state of
//    its corresponding Intersection at each time-step
// 6. Receive message from each Intersection instance containing a string summary 
//    of the segments and the cars occupying the segments
// 7. Send message to Simulation instance at each time-step containing summary of 
//    each Intersection instance.
// Collaboration:
// 1. Simulation Class
// 2. Intersection Class
// 3. IntersectionController Class
// Pseudocode:
// grid sends message to each traffic controller to update
grid.update() {
    for intersection_controller : intersection_controllers:
        intersection_controller.update();
}
