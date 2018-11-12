
// Authors: SEALS (Jessica, Melissa, Tracy, Will)
// *****************************************************************************
// *****************************************************************************
// **** Intersection
// *****************************************************************************
// *****************************************************************************

import java.util.*;

public class Intersection {
  // id identifies which intersection this Intersection instance is.
  private int id;

  // row and column of this intersection instance in the grid.
  private int row;
  private int col;

  // Queues representing the four incoming segments. The int values of the
  // direction code for S, E, N, W - (0,1,2,3) respectively - correspond to the
  // indices of this array of Queue.
  private ArrayList<Queue<Car>> incomingSegments = 
    new ArrayList<Queue<Car>>();

  // Four next intersections of this intersection. The int values of the
  // direction code for S, E, N, W - (0,1,2,3) respectively - correspond to the
  // indices of this array of Intersection instances.
  private Intersection[] nextIntersection = new Intersection[4];

  // 2x2 intersection grids of the NW, NE, SE, SW in clockwise order.
  private Car[][] intersectionGrid;

  // Setters and getters
  public int getId() {
    return id;
  } // end of getId()


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
  public Intersection(int id, int row, int col) {
    this.id = id;
    this.row = row;
    this.col = col;

    for (int i = 0; i < 4; i++) {
      Queue<Car> segment = new LinkedList<Car>();
      incomingSegments.add(segment);
    } // end of for(i = 0; i < 4; i++)

    intializeIntersectionGrid();
  } // end of Intersection() constructor


  private void intializeIntersectionGrid() {
    intersectionGrid = new Car[2][2];
    for (int row = 0; row < intersectionGrid.length; row ++) {
      for (int col = 0; col < intersectionGrid[0].length; col++) {
        intersectionGrid[row][col] = null;
      }
    } // end of for (row, col in intersectionGrid)
  } // end of initializeIntersectionGrid()


  public boolean intersectionIsEmpty() {
    for (int row = 0; row < intersectionGrid.length; row ++) {
      for (int col = 0; col < intersectionGrid[0].length; col++) {
        if (intersectionGrid[row][col] != null) {
          return false;
        }
      }
    } // end of for (row, col in intersectionGrid)
    return true;
  } // end of intersectionIsEmpty()


  public ArrayList<Car> getApproachingCars() {
    // Check the 4 queues that represent the incoming segments, and only add
    // cars that are the first to be in the queue (FIFO) AND have finished
    // traversing the segments (timeLeftInSegment = 0).
    ArrayList<Car> approachingCars = new ArrayList<Car>();
    for (Queue<Car> segment : incomingSegments) {
      Car potentialCar = segment.peek();
      if (potentialCar == null) continue;
      // temporarily not checking for whether Car's timeToTraverseSegment is 0.
      approachingCars.add(potentialCar);
    } // end of for (segment in incomingSegments)
    return approachingCars;
  } // end of getApproachingCars()


  public boolean nextSegmentIsFull(Car car) {
    int nextDirection = calculateNextDirection(car.getDirection(),
      car.getTurningDirection());
    Intersection nextIntersection = nextIntersection[nextDirection];
    if (nextIntersection == null) return true;
    return nextIntersection.checkIfQueueIsFull(nextDirection);
  } // end of nextSegmentIsFull()


  private boolean checkIfQueueIsFull(int nextDirection) {
    return false; // temporarily
  } // end of checkIfQueueuIsFull()


  private int calculateNextDirection(int carDirection, int carTurningCode) {
    switch(carDirection) {
      case 0:
        if (carTurningCode == 1) {
          return 3;
        } else if (carTurningCode == -1) {
          return 1;
        }
        return 0;
      case 1:
        if (carTurningCode == 1) {
          return 0;
        } else if (carTurningCode == -1) {
          return 2;
        }
        return 1;
      case 2:
        if (carTurningCode == 1) {
          return 1;
        } else if (carTurningCode == -1) {
          return 3;
        }
        return 2;
      case 3:
        if (carTurningCode == 1) {
          return 2;
        } else if (carTurningCode == -1) {
          return 0;
        }
        return 3;
    } // end of switch(carDirection)
    return -1;
  } // end of calculateNextDirection()


  public void moveCarIntoIntersection(Car car) {
    Queue<Car> segment = incomingSegments.get(car.getDirection());
    Car carToDequeue = segment.poll();
    if (carToDequeue != car) {
      // throw error
    }

    switch (car.getDirection()) {
      case 0:
        intersectionGrid[0][0] = car;
        break;
      case 1:
        intersectionGrid[1][0] = car;
        break;
      case 2:
        intersectionGrid[1][1] = car;
        break;
      case 3:
        intersectionGrid[0][1] = car;
        break;
    } // end of switch (car.getDirection())
     
    if (car.getTurningDirection() == 1) {
      car.setDirection(calculateNextDirection(car.getDirection(),
        car.getTurningDirection()));
    } // end of if (car.getTurningDirection() == 1)
  } // end of moveCarIntoIntersection()


  public void updateIntersectionGrid() {
    // Check the NW slot
    if (intersectionGrid[0][0] != null) {
      Car car = intersectionGrid[0][0];
      updateNWSlot(car);
    } 

    if (intersectionGrid[0][1] != null) {
      Car car = intersectionGrid[0][1];
      updateNESlot(car);
    } 

    if (intersectionGrid[1][0] != null) {
      Car car = intersectionGrid[1][0];
      updateSWSlot(car);
    } 

    if (intersectionGrid[1][1] != null) {
      Car car = intersectionGrid[1][1];
      updateSESlot(car);
    } 
  } // end of updateIntersectionGrid ()


  private void resetCarDirIfTurningLeft(Car car) {
    if (car.getTurningDirection() == -1) {
      car.setDirection(calculateNextDirection(car.getDirection(),
        car.getTurningDirection()));
      car.setTurningDirection(0);
    } // end of if (car.getTurningDirection() == -1)
  } // end of resetCarDirIfTurningLeft()


  private void updateNWSlot(Car car) { // NW abbreviates NorthWestWard
    intersectionGrid[0][0] = null;
    switch(car.getDirection()) {
      case 0:
        intersectionGrid[1][0] = car;
        break;
      case 1:
        intersectionGrid[0][1] = car;
        break;
      default:
        Intersection nextIntersection = nextIntersection[car.getDirection()];
        car.moveToNextIntersection(nextIntersection);
        break;
    } // end of switch(car.getDirection())
    resetCarDirIfTurningLeft(car);
  } // end of updateNWSlot()


  private void updateSWSlot(Car car) { // SW abbreviates SouthWestWard
    intersectionGrid[1][0] = null;
    switch(car.getDirection()) {
      case 1:
        intersectionGrid[1][1] = car;
        break;
      case 2:
        intersectionGrid[0][0] = car;
        break;
      default:
        Intersection nextIntersection = nextIntersection[car.getDirection()];
        car.moveToNextIntersection(nextIntersection);
        break;
    } // end of switch(car.getDirection())
    resetCarDirIfTurningLeft(car);
  } // end of updateSWSlot()


  private void updateNESlot(Car car) { // NE abbreviates NorthEastWard
    intersectionGrid[0][1] = null;
    switch(car.getDirection()) {
      case 0:
        intersectionGrid[1][1] = car;
        break;
      case 3:
        intersectionGrid[0][0] = car;
        break;
      default:
        Intersection nextIntersection = nextIntersection[car.getDirection()];
        car.moveToNextIntersection(nextIntersection);
        break;
    } // end of switch(car.getDirection())
    resetCarDirIfTurningLeft(car);
  } // end of updateNESlot()


  private void updateSESlot(Car car) { // SE abbreviates SouthEastWard
    intersectionGrid[1][1] = null;
    switch(car.getDirection()) {
      case 2:
        intersectionGrid[0][1] = car;
        break;
      case 3:
        intersectionGrid[1][0] = car;
        break;
      default:
        Intersection nextIntersection = nextIntersection[car.getDirection()];
        car.moveToNextIntersection(nextIntersection);
        break;
    } // end of switch(car.getDirection())
    resetCarDirIfTurningLeft(car);
  } // end of updateSESlot()


  public void addCarToQueue(Car car) {
    incomingSegments.get(car.getDirection()).add(car);
  } // end of addCarToQueue ()

  // Returns summary of the intersection queues and cars
  public String toString() {
    String summary = "At the intersection located at col " + col + " and row " 
                     + row + "\n";

    for (int i = 0; i < incomingSegments.size(); i++) {
      String direction = "";

      switch(i) {
        case 0:
          direction = "SOUTHWARD";
          break;
        case 1:
          direction = "EASTWARD";
          break;
        case 2:
          direction = "NORTHWARD";
          break;
        case 3:
          direction = "WESTWARD";
          break;
      } // end of switch(i)
      if (incomingSegments.get(i).size() == 0) {
        summary += ("  incoming lane having direction " + direction + 
          " is empty\n");
      } else {
        summary += ("  incoming lane having direction " + direction + 
          " is nonempty\n");
      }

      for (Car car : incomingSegments.get(i)) {
        summary += (car.toString());
      }
    }
    summary +="\n";

    return summary;
  } // end of toString()

}
