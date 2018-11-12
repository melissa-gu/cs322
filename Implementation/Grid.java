
// Authors: SEALS (Jessica, Melissa, Tracy, Will)
// *****************************************************************************
// *****************************************************************************
// **** Grid
// *****************************************************************************
// *****************************************************************************


// NOTE on coordinates-conversion:
// a. Notation:
// Coordinates of intersection are formatted as intersection[col, row], with:
// 1 <= intersection.row <= numIntersections,
// 1 <= intersection.col <= numIntersections.
// Coordinates of the corresponding intersection element stored in the grid are
// formatted as grid[i][j]. When looping through grid with double for loops, 
// counters are grid.row = i + 1, grid.col = j + 1;
// 0 <= grid.i < numIntersections, 0 <= grid.j < numIntersections
// 
// b. Conversion rules
// 1. grid[i][j] = grid[row - 1][col - 1]
// 2. intersection.row = numIntersections - grid.i
//                     = numIntersections - grid.row + 1
//    intersection.col = grid.j + 1 = grid.col
// 3. grid.row = grid.i + 1 = (numIntersections - intersection.row) + 1.
//    grid.col = grid.j + 1 = intersection.col
import java.util.*;

public class Grid {
  private int numIntersections;
  // The 2D representations of intersections.
  private Intersection[][] grid;
  private ArrayList<IntersectionController> intersectionControllers = 
    new ArrayList<IntersectionController>();


  public Intersection getIntersection(int row, int col) {
    return grid[numIntersections - row][col - 1];
  } // end of getIntersection()


  // Constructor
  // @param numIntersections: the number of intersections in one direction.
  public Grid (int numIntersections) {
    this.numIntersections = numIntersections;
    grid = new Intersection[numIntersections][numIntersections];

    // Construct instances of intersection and populate the grid.
    int intersection_id = numIntersections * numIntersections;
    for (int row = 1; row <= numIntersections; row++) {
      for (int col = 1; col <= numIntersections; col++) {
        Intersection intersection = new Intersection(intersection_id,
          numIntersections - row + 1, col);
        grid[row - 1][col - 1] = intersection;
        intersection_id--;
      } // end of for (each col of grid)
    } // end of for (each row of grid)

    // Add intersection controllers in order of processing
    for (int row = numIntersections; row >= 1; row--) {
      for (int col = 1; col <= numIntersections; col++) {
        IntersectionController controller = 
          new IntersectionController(grid[row-1][col-1]);
        intersectionControllers.add(controller);
      } // end of for (each col of grid)
    } 
    connectIntersections();
  } // end of Grid()


  private void connectIntersections() {
    for (int row = 1; row <= numIntersections; row++) {
      for (int col = 1; col <= numIntersections; col ++) {
        Intersection intersection = grid[row-1][col-1];
        Intersection[] nextIntersection = new Intersection[4];
        // Southward next intersection
        if (row + 1 <= numIntersections) {
          nextIntersection[0] = grid[row][col - 1];
        } else {
          nextIntersection[0] = null;
        }

        // Eastward next intersection
        if (col - 1 >= 1) {
          nextIntersection[1] = grid[row - 1][col - 2];
        } else {
          nextIntersection[1] = null;
        }

        // Northward next intersection
        if (row - 1 >= 1) {
          nextIntersection[2] = grid[row - 2][col - 1];
        } else {
          nextIntersection[2] = null;
        }

        // Westward next intersection
        if (col + 1 <= numIntersections) {
          nextIntersection[3] = grid[row - 1][col];
        } else {
          nextIntersection[3] = null;
        }

        intersection.setNextIntersection(nextIntersection);
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
