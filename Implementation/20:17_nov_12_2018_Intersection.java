
// Authors: SEALS (Jessica, Melissa, Tracy, Will)
// *****************************************************************************
// *****************************************************************************
// **** Intersection
// *****************************************************************************
// *****************************************************************************

import java.util.*;

public class Intersection {

  // row and column of this intersection instance in the grid.
  private int row;
  private int col;

  // Queues representing the four incoming segments. The int values of the
  // direction code for S, E, N, W - (0,1,2,3) respectively - correspond to the
  // indices of this arraylist of Queue.

  // Used ArrayList of Queue<Car> instead of array of Queue<Car> because generic
  // array creation is not allowed in Java.
  // Link for reference: https://tinyurl.com/generic-array-creation
  // Compiler errors: 
  // ./Intersection.java:20: error: generic array creation
  private ArrayList<Queue<Car>> incomingSegment = new ArrayList<Queue<Car>>();

  // Four next intersections of this intersection. The int values of the
  // direction code for S, E, N, W - (0,1,2,3) respectively - correspond to the
  // indices of this array of Intersection instances.
  private Intersection[] nextIntersection = new Intersection[4];

  // 2x2 intersection grids of the NorthWestward, NorthEastward, SouthEastward,
  // SouthWestward in clockwise order.
  private Car[][] intersectionGrid;

  public int getRow() {
    return row;
  } // end of getRow()


  public int getCol() {
    return col;
  } // end of getCol()


  public void setNextIntersection(Intersection[] nextIntersection) {
    this.nextIntersection = nextIntersection;
  } // end of setNextIntersection()


  // Constructor
  public Intersection(int row, int col) {
    this.row = row;
    this.col = col;

    for (int i = 0; i < 4; i++) {
      Queue<Car> segment = new LinkedList<Car>();
      incomingSegment.add(i, segment);
    } // end of for(i = 0; i < 4; i++)

    intializeIntersectionGrid();
  } // end of Intersection() constructor


  private void intializeIntersectionGrid() {
    intersectionGrid = new Car[2][2];
    for (int row = 0; row < intersectionGrid.length; row ++) {
      for (int col = 0; col < intersectionGrid[0].length; col++) {
        intersectionGrid[row][col] = null;
      } // end of for (col in intersectionGrid)
    } // end of for (row in intersectionGrid)
  } // end of initializeIntersectionGrid()


  public boolean intersectionIsEmpty() {
    for (int row = 0; row < intersectionGrid.length; row ++) {
      for (int col = 0; col < intersectionGrid[0].length; col++) {
        if (intersectionGrid[row][col] != null) {
          return false;
        }
      } // end of for (col in intersectionGrid)
    } // end of for (row in intersectionGrid)
    return true;
  } // end of intersectionIsEmpty()


  public ArrayList<Car> getApproachingCars() {
    // Check the 4 queues that represent the incoming segments, and only add
    // cars that are the first to be in the queue (FIFO) AND have finished
    // traversing the segments (timeLeftInSegment = 0).
    ArrayList<Car> approachingCars = new ArrayList<Car>();
    for (Queue<Car> segment : incomingSegment) {
      Car potentialCar = segment.peek();
      if (potentialCar == null) continue;
      // temporarily not checking for whether Car's timeToTraverseSegment is 0.
      approachingCars.add(potentialCar);
    } // end of for (segment in incomingSegment)
    return approachingCars;
  } // end of getApproachingCars()


  public boolean nextSegmentIsFull(Car car) {
    int nextDirection = calculateNextDirection(car.getDirection(),
      car.getTurningDirection());
    Intersection intersection = nextIntersection[nextDirection];
    if (intersection == null) return true;
    return intersection.checkIfQueueIsFull(nextDirection);
  } // end of nextSegmentIsFull()


  private boolean checkIfQueueIsFull(int nextDirection) {
    return false; // temporarily
  } // end of checkIfQueueuIsFull()


  private int calculateNextDirection(int carDirection, int carTurningCode) {
    switch(carDirection) {
      // Check for SOUTHWARD
      case TrafficTesterView.SOUTHWARD:
        if (carTurningCode == TrafficTesterView.TURN_RIGHTWARD) {
          return TrafficTesterView.WESTWARD;
        } else if (carTurningCode == TrafficTesterView.TURN_LEFTWARD) {
          return TrafficTesterView.EASTWARD;
        }
        return TrafficTesterView.SOUTHWARD;
      // Check for EASTWARD
      case TrafficTesterView.EASTWARD:
        if (carTurningCode == TrafficTesterView.TURN_RIGHTWARD) {
          return TrafficTesterView.SOUTHWARD;
        } else if (carTurningCode == TrafficTesterView.TURN_LEFTWARD) {
          return TrafficTesterView.NORTHWARD;
        }
        return TrafficTesterView.EASTWARD;
      // Check for NORTHWARD
      case TrafficTesterView.NORTHWARD:
        if (carTurningCode == TrafficTesterView.TURN_RIGHTWARD) {
          return TrafficTesterView.EASTWARD;
        } else if (carTurningCode == TrafficTesterView.TURN_LEFTWARD) {
          return TrafficTesterView.WESTWARD;
        }
        return TrafficTesterView.NORTHWARD;
      // Check for WESTWARD
      case TrafficTesterView.WESTWARD:
        if (carTurningCode == TrafficTesterView.TURN_RIGHTWARD) {
          return TrafficTesterView.NORTHWARD;
        } else if (carTurningCode == TrafficTesterView.TURN_LEFTWARD) {
          return TrafficTesterView.SOUTHWARD;
        }
        return TrafficTesterView.WESTWARD;
    } // end of switch(carDirection)
    return -1;
  } // end of calculateNextDirection()


  public void moveCarIntoIntersectionGrid(Car car) {
    Queue<Car> segment = incomingSegment.get(car.getDirection());
    Car carToDequeue = segment.poll();
    if (carToDequeue != car) {
      // throw error
    }

    // switch (car.getDirection()) {
    //   case TrafficTesterView.SOUTHWARD:
    //     intersectionGrid[0][0] = car;
    //     break;
    //   case TrafficTesterView.EASTWARD:
    //     intersectionGrid[1][0] = car;
    //     break;
    //   case TrafficTesterView.NORTHWARD:
    //     intersectionGrid[1][1] = car;
    //     break;
    //   case TrafficTesterView.WESTWARD:
    //     intersectionGrid[0][1] = car;
    //     break;
    // } // end of switch (car.getDirection())

    car.appendToSummary("  car#" + car.getId() + " is removed from " + 
                  TrafficTesterView.convertToSegmentDirection(
                    car.getDirection()) + " queue of intersection ["
                    + col + ", " + row + "]\n");
    car.setDirection(calculateNextDirection(car.getDirection(),
        car.getTurningDirection()));
    Intersection intersection = nextIntersection[car.getDirection()];
    car.moveToNextIntersection(intersection);
     
    // if (car.getTurningDirection() == 1) {
    //   car.setDirection(calculateNextDirection(car.getDirection(),
    //     car.getTurningDirection()));
    // } // end of if (car.getTurningDirection() == 1)
  } // end of moveCarIntoIntersectionGrid()


  public void updateIntersectionGrid() {
    if (intersectionGrid[0][0] != null) {
      Car car = intersectionGrid[0][0];
      updateNorthWestwardSlot(car);
    } 

    if (intersectionGrid[0][1] != null) {
      Car car = intersectionGrid[0][1];
      updateNorthEastwardSlot(car);
    } 

    if (intersectionGrid[1][0] != null) {
      Car car = intersectionGrid[1][0];
      updateSouthWestwardSlot(car);
    } 

    if (intersectionGrid[1][1] != null) {
      Car car = intersectionGrid[1][1];
      updateSouthEastwardSlot(car);
    } 
  } // end of updateIntersectionGrid ()


  private void resetCarDirIfTurningLeft(Car car) {
    if (car.getTurningDirection() == -1) {
      car.setDirection(calculateNextDirection(car.getDirection(),
        car.getTurningDirection()));
      car.setTurningDirection(0);
    } // end of if (car.getTurningDirection() == -1)
  } // end of resetCarDirIfTurningLeft()


  private void updateNorthWestwardSlot(Car car) {
    intersectionGrid[0][0] = null;
    switch(car.getDirection()) {
      case TrafficTesterView.SOUTHWARD:
        intersectionGrid[1][0] = car;
        break;
      case TrafficTesterView.EASTWARD:
        intersectionGrid[0][1] = car;
        break;
      default:
        Intersection intersection = nextIntersection[car.getDirection()];
        car.moveToNextIntersection(intersection);
        break;
    } // end of switch(car.getDirection())
    resetCarDirIfTurningLeft(car);
  } // end of updateNorthWestwardSlot()


  private void updateSouthWestwardSlot(Car car) {
    intersectionGrid[1][0] = null;
    switch(car.getDirection()) {
      case TrafficTesterView.EASTWARD:
        intersectionGrid[1][1] = car;
        break;
      case TrafficTesterView.NORTHWARD:
        intersectionGrid[0][0] = car;
        break;
      default:
        Intersection intersection = nextIntersection[car.getDirection()];
        car.moveToNextIntersection(intersection);
        break;
    } // end of switch(car.getDirection())
    resetCarDirIfTurningLeft(car);
  } // end of updateSouthWestwardSlot()


  private void updateNorthEastwardSlot(Car car) {
    intersectionGrid[0][1] = null;
    switch(car.getDirection()) {
      case TrafficTesterView.SOUTHWARD:
        intersectionGrid[1][1] = car;
        break;
      case TrafficTesterView.WESTWARD:
        intersectionGrid[0][0] = car;
        break;
      default:
        Intersection intersection = nextIntersection[car.getDirection()];
        car.moveToNextIntersection(intersection);
        break;
    } // end of switch(car.getDirection())
    resetCarDirIfTurningLeft(car);
  } // end of updateNorthEastwardSlot()


  private void updateSouthEastwardSlot(Car car) {
    intersectionGrid[1][1] = null;
    switch(car.getDirection()) {
      case TrafficTesterView.NORTHWARD:
        intersectionGrid[0][1] = car;
        break;
      case TrafficTesterView.WESTWARD:
        intersectionGrid[1][0] = car;
        break;
      default:
        Intersection intersection = nextIntersection[car.getDirection()];
        car.moveToNextIntersection(intersection);
        break;
    } // end of switch(car.getDirection())
    resetCarDirIfTurningLeft(car);
  } // end of updateSouthEastwardSlot()


  public void addCarToQueue(Car car) {
    incomingSegment.get(car.getDirection()).add(car);
  } // end of addCarToQueue ()

  // Returns summary of the intersection queues and cars
  public String toString() {
    String summary = "At the intersection located at col " + col + " and row " 
                     + row + "\n";

    for (int i = 0; i < incomingSegment.size(); i++) {
      String direction =
        TrafficTesterView.convertToSegmentDirection(i);
      if (incomingSegment.get(i).size() == 0) {
        summary += ("  incoming segment having direction " + direction + 
          " is empty\n");
      } else {
        summary += ("  incoming segment having direction " + direction + 
          " is nonempty\n");
      }

      for (Car car : incomingSegment.get(i)) {
        summary += (car.toString());
      }
    }
    summary +="\n";

    return summary;
  } // end of toString()

}
