// Authors: SEALS (Jessica, Melissa, Tracy, Will)

// *****************************************************************************
// *****************************************************************************
// **** Grid
// *****************************************************************************
// *****************************************************************************


// Converting from grid coordinates to Intersection coordinates:
// row and col are iterators from 1 to numIntersections inclusive.
// grid[i,j] = grid[row - 1][col - 1]
//           = Intersection(row: numIntersections - row + 1, col)

// Converting from intersection coordinates to grid coordinates:
// grid[row,col] = (numIntersections - intersection[row], intersection[col] - 1)

// grid[i, j] = grid[row - 1][col - 1]
// = (numIntersections - intersection[row] + 1, intersection[col])
// grid[i, j] = 
public class Grid {
  private int numIntersections;
  // The 2D representations of intersections.
  private Intersection[][] grid;
  private IntersectionController[] intersectionControllers;


  public Intersection getIntersection(int row, int col) {
    return grid[numIntersections - row + 1][col];
  } // end of getIntersection()


  // Constructor
  // @param numIntersections: the number of intersections in one direction.
  public Grid (int numIntersections, int maxSegmentCapacity) {
    this.numIntersections = numIntersections;
    grid = new Intersection[numIntersections][numIntersections];
    intersectionControllers = 
      new IntersectionController[numIntersections * numIntersections];

    // Construct instances of intersection and populate the grid.
    int intersection_id = numIntersections * numIntersections;
    for (int row = 1; row <= numIntersections; row++) {
      for (int col = 1; col <= numIntersections; col++) {
        Intersection intersection = new Intersection(intersection_id,
          numIntersections - row + 1, col, maxSegmentCapacity);
        grid[row - 1][col - 1] = intersection;

        // Construct a corresponding intersection controller.
        intersectionControllers[intersection_id - 1] = 
          new IntersectionController(intersection);
        intersection_id--;
      }
    } // end of for (each row and column of grid)

    connectIntersections();
  } // end of Grid()


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
  } // end of connectIntersections()


  public void update() {
    for (IntersectionController controller : intersectionControllers) {
      controller.update();
    } // end of for (each controller in intersectionControllers)
  } // end of update()
  

  public String toString() {
    String summary = "";
    // Intersections are processed in the order of  
    // row 1 intersections, from left to right
    // row 2 intersections, from left to right, etc, for all rows.
    // but row i corresponds to row [num_intersections - i] of the grid.
    // and col i corresponds to col [i - 1] of the grid.
    int rowNumber = 1;
    for (int row = numIntersections - rowNumber; row >= 0; row--) {
      for (int col = 1; col <= numIntersections; col++) {
        Intersection intersection = grid[row][col - 1];
        summary += intersection.toString();
      }
    } // end of for (each intersection in grid)
    return summary;
  } // end of toString()
}
