
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

  // the maximum number of Car instances each segment can accomodate.
  private int maxSegmentCapacity;

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

  // Summary of the 4 incoming segments.
  private String[] segmentSummary = new String[4];

  public int getRow() {
    return row;
  } // end of getRow()


  public int getCol() {
    return col;
  } // end of getCol()


  public void setNextIntersection(Intersection[] nextIntersection) {
    this.nextIntersection = nextIntersection;
  } // end of setNextIntersection()


  public Intersection getNextIntersection(int directionCode) {
    return nextIntersection[directionCode];
  }

  // Constructor
  public Intersection(int row, int col, int maxSegmentCapacity) {
    this.row = row;
    this.col = col;
    this.maxSegmentCapacity = maxSegmentCapacity;

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
      if (potentialCar.getTimeLeftInSegment() <= 0) {
        approachingCars.add(potentialCar);
      } // end of if (potentialCar.getTimeLeftInSegment() <= 0)
    } // end of for (segment in incomingSegment)
    return approachingCars;
  } // end of getApproachingCars()


  public boolean nextSegmentIsFull(Car car) {
    int turnDirection;
    if (car.getNumBlocksBeforeTurning() == 0) {
      turnDirection = car.getTurningDirection();
    } else {
      turnDirection = TrafficTesterView.NEVER_TURN;
    }
    int nextDirection = calculateNextDirection(car.getDirection(),
      turnDirection);
    Intersection intersection = nextIntersection[nextDirection];
    if (intersection == null) return false;
    return intersection.checkIfQueueIsFull(nextDirection);
  } // end of nextSegmentIsFull()


  private boolean checkIfQueueIsFull(int nextDirection) {
    return incomingSegment.get(nextDirection).size() >= maxSegmentCapacity;
  } // end of checkIfQueueIsFull()


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
    int segmentDirectionCode = car.getDirection();
    Queue<Car> segment = incomingSegment.get(segmentDirectionCode);

    Car carToDequeue = segment.poll();
    if (carToDequeue != car) {
      // throw error
    }

    String summary = (("   car#" + car.getId() + " is removed from " + 
                    TrafficTesterView.convertToSegmentDirection(
                    car.getDirection()) + " queue of intersection ["
                    + col + ", " + row + "]\n"));

    int direction;
    if (car.getNumBlocksBeforeTurning() == 0) {
      direction = car.getTurningDirection();
    } else {
      direction = TrafficTesterView.NEVER_TURN;
    }
    car.setDirection(calculateNextDirection(car.getDirection(), direction));
    Intersection intersection = nextIntersection[car.getDirection()];
    if (intersection != null) {
      summary += ("   and placed into " + 
                 TrafficTesterView.convertToSegmentDirection(car.getDirection())
                 + " queue of intersection " + "[" + intersection.getCol() 
                 + ", " + intersection.getRow() + "].\n");
    } else {
      summary += ("   and placed into " + 
        TrafficTesterView.convertToSegmentDirection(car.getDirection()) + 
        " segment exiting the grid.\n");
    }
    segmentSummary[segmentDirectionCode] += summary;
    car.moveToNextIntersection(intersection);

    // resetCarDirIfTurningRight(car);
    
  } // end of moveCarIntoIntersectionGrid()


  public void updateSegmentSummary() {
    for (int segmentDirectionCode = 0; segmentDirectionCode < 4;
         segmentDirectionCode++) {
      String direction =
        TrafficTesterView.convertToSegmentDirection(segmentDirectionCode);
      if (incomingSegment.get(segmentDirectionCode).size() == 0) {
        segmentSummary[segmentDirectionCode] = 
          "  incoming segment having direction " + direction + " is empty\n";
      } else {
        segmentSummary[segmentDirectionCode] = 
          "  incoming segment having direction " + direction + " is nonempty\n";
      }
    } // end of for (segmentDirectionCode in range(0,4))
  } // end of updateSegmentSummary()

  public void addCarToQueue(Car car) {
    incomingSegment.get(car.getDirection()).add(car);
  } // end of addCarToQueue ()

  // Returns summary of the intersection queues and cars
  public String toString() {
    String summary = "At the intersection located at col " + col + " and row " 
                     + row + "\n";

    for (int segmentDirectionCode = 0;
         segmentDirectionCode < segmentSummary.length; segmentDirectionCode++) {
      summary += segmentSummary[segmentDirectionCode];
    } // end of for (segmentDirectionCode = 0; ...)

    summary += "\n";
    return summary;
  } // end of toString()


}


