// Authors: SEALS (Jessica, Melissa, Tracy, Will)

// *****************************************************************************
// *****************************************************************************
// **** Grid
// *****************************************************************************
// *****************************************************************************

public class Grid {
  private int maxGrid
  // The 2D representations of intersections.
  private Intersection[][] grid;

  // Constructor
  // @param numIntersections: the number of intersections in one direction.
  public Grid (int numIntersections) {
    grid = new Intersection[numIntersections][numIntersections];

    // Construct instances of intersection and populate the grid.
    int intersection_id = numIntersections * numIntersections;
    for (int row = 1; row < numIntersections; row++) {
      for (int col = 1; col < numIntersections; col++) {
        grid[row - 1][col - 1] = new Intersection(intersection_id);
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
          nextIntersections[0] = grid[row][col-1];
        } else {
          nextIntersections[0] = null;
        }
      }
    } // end of for (each row and column of grid)
  }
  

}

// The class name: Simulation
// Responsibility:
// 1. Receive message from Setup instance to construct the instance of Simulation
//    class
// 2. Send message to Grid to initialize instance of Grid class with number of
//    Intersections based on constructor parameters
// 3. Initialize a list of instances of Car class with the following variables for
//    each car:
//    3.1 Integer ID representing the instance of Car
//    3.2 Integer representing the coordinate of the segment or instance of
//        Intersection the car is in, with the initial value specified by user
//        input
//    3.3 Initialize integer representing time left in a segment,
//        timeLeftInSegment, with the initial value of timeToTraverseSegment
//    3.4 Initialize integer representing the direction (N,S,W,E) the instance of
//        Car is aiming towards
//    3.5 Initialize integer representing the number of blocks the car will travel
//        before turning
//    3.6 Initialize integer representing direction of turn
//    3.7 Initialize string representing a summary of the car's status in each 
//        simulated time-step. This string is initially empty, and remains 
//        empty except in cases where the car moves to a new Intersection 
//        instance or leaves the grid.
// 4. Send messages to newly initialized cars to enqueue themselves in queues of
//    the Intersection instance corresponding to their intersection ID
// 5. Send messages to instances of Car to update positions of cars and decrement
//    the cars' timeToTraverseSegments appropriately at each time-step
// 6. Send message to Grid instance to update the state of the Intersection
//    instances at each time-step
// 7. Receives message from each instance of Car about whether that car has left
//    the grid. If the car has left the grid, the car will be removed from the
//    list of active cars and added to a list of cars that have left the grid, 
//    and the numCarsExited variable is incremented. The Simulation also receives 
//    a message containing the car's entry and exit time-steps, and uses them to 
//    compute that car's time to exit the grid, and adds this to 
//    sumOfAllCarTimesToExit. The Simulation sends a message to the Car instance to
//    update that car's summary string.
// 8. Receive message from Grid instance at each time-step containing summary of 
//    each Intersection instance. This summary is printed at each time-step.
// 9. At the end of the Simulation, calculate and print the average time cars that 
//    have exited the grid have taken to exit the grid.
// Collaboration:
// 1. Setup Class
// 2. Grid Class
// 2. Car Class
// Pseudocode:
// updates the position of each car in the simulation
// sends message to grid to update itself
simulation.update() {
    grid.update();
    for all car in activeCars:
        car.update();
        // If car is exiting the grid
        if (car.hasLeftGrid()) {
            car.appendToSummary("car#" + car.ID + " has left the grid");
            activeCars.remove(car);
            exitedCars.add(car);
            numCarsExited++;
            timeToExit = car.getExitTime() - car.getEntryTime();
            sumOfAllCarTimesToExit += timeToExit;
        }
}

// prints summary of state of intersections and cars at each time-step
simulation.printSummary() {
  print(grid.toString());
}

// prints average time cars took to exit the grid
simulation.printAverage() {
 print("The average time cars took to exit the grid was ");
 print(sumOfAllCarTimesToExit/numCarsExited);
 print(" simulated time-steps");
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
// grid receives message from each instance of Intersection
grid.toString() {
  String summary;
  for (int i = 1; i <= grid.numRows; i++) {
    for (int j = 1; j <= grid.numCols; j++) {
      summary.append(intersection[i,j].toString());
    }
  }
  return summary;
}