// Authors: SEALS (Jessica, Melissa, Tracy, Will)

// *****************************************************************************
// *****************************************************************************
// **** Intersection
// *****************************************************************************
// *****************************************************************************


public class Intersection {
  // id identifies which intersection this Intersection instance is.
  private int id;

  // row and column of this intersection instance in the grid.
  private int row;
  private int col;

  // the maximum number of Car instances each segment can accomodate.
  private int maxSegmentCapacity;

  // Queues representing the four ingoing segments. The int values of the
  // direction code for S, E, N, W - (0,1,2,3) respectively - correspond to the
  // indices of this array of Queue.
  private Queue<Car>[] ingoingSegments;

  // Four next intersections of this intersection. The int values of the
  // direction code for S, E, N, W - (0,1,2,3) respectively - correspond to the
  // indices of this array of Intersection instances.
  private Intersection[] nextIntersections;

  // 2x2 intersection grids of the NW, NE, SE, SW in clockwise order.
  private Car[] intersectionGrid;

  // Setters and getters
  public int getId() {
    return id;
  } // end of getId()


  public setNextIntersections(Intersection[] nextIntersections) {
    this.nextIntersections = nextIntersections;
  } // end of setNextIntersections()


  // Constructor
  public Intersection(int id, int row, int col, int maxSegmentCapacity) {
    this.id = id;
    this.row = row;
    this.col = col;
    this.maxSegmentCapacity = maxSegmentCapacity;

    for (Queue segment : ingoingSegments) {
      segment = new PriorityQueue<Car>();
    } // end of for (segment : ingoingSegments)

    intersectionGrid = new Car[2][2];
    for (int row = 0; row < intersectionGrid.length; row ++) {
      for (int col = 0; col < intersectionGrid[0].length; col++) {
        intersectionGrid[row][col] = null;
      }
    } // end of for (row, col in intersectionGrid)
  } // end of Intersection() constructor


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
    // Check the 4 queues that represent the ingoing segments, and only add
    // cars that are the first to be in the queue (FIFO) AND has finished
    // traversing the segments (timeLeftInSegment = 0).
    ArrayList<Car> approachingCars = new ArrayList<Car>();
    for (Queue<Car> segment : ingoingSegments) {
      Car potentialCar = segment.peek();
      if (potentialCar == null) continue;
      if (potentialCar.getTimeLeftInSegment() <= 0) {
        approachingCars.add(potentialCar);
      }
    } // end of for (segment in ingoingSegments)
    return approachingCars;
  } // end of getApproachingCars()


  public boolean nextSegmentIsFull(Car car) {
    int nextDirection = calculateNextDirection(car.getDirection(),
      car.getTurningDirection());
    Intersection nextIntersection = nextIntersections[nextDirection];
    return nextIntersection.checkIfQueueIsFull(nextDirection);
  } // end of nextSegmentIsFull()


  private boolean checkIfQueueIsFull(int nextDirection) {
    return ingoingSegments[nextDirection].size() > maxSegmentCapacity;
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
    }
  } // end of calculateNextDirection()


  public void moveCarIntoIntersection(Car car) {
    Queue segment = ingoingSegments[car.getCarDirection()];
    Car carToDequeue = segment.dequeue();
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
    }
     
    if (car.getTurningDirection() == 1) {
      car.setDirection(calculateNextDirection(car.getDirection(),
        car.getTurningDirection()));
    }
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


    for (int row = 0; row < intersectionGrid.length; i++) {
      for (int col = 0; col < intersectionGrid[0].length; )
    }
      for (each slot in 2x2grid) {
          if (slot occupied by car) {
              moveCarAlong(car.dir);
              if (car.turningCode == LEFT) {
                  car.dir = calculateNextDirection(car.dir, car.turning_dir);
                  car.turningCode == STRAIGHT;
              }
              if (car.isExitingIntersection()) {
                  // Based on the car direction, retrieves the "next" intersection
                  // of this current intersection and set the car's intersection
                  // reference to that intersection.
                  nextIntersection = this.getNextIntersection(car.dir)
                  car.moveToNextIntersection(nextIntersection);
              }
          }
      }
  }

  private void updateNWSlot(Car car) {

  } // end of updateNWSlot()


  private void updateSWSlot(Car car) {

  } // end of updateSWSlot()


  private void updateNESlot(Car car) {

  } // end of updateNESlot()


  private void updateSESlot(Car car) {

  } // end of updateSESlot()


  public void addCarToQueue(Car car) {
    ingoingSegments[car.getDirection()].add(car);
  } // end of addCarToQueue ()

  // Returns summary of the intersection queues and cars
  public String toString() {
    String summary;
    if southQueue.isEmpty() {
      summary.append("incoming lane having direction SOUTHWARD is empty");
    }
    else {
      summary.append("incoming lane having direction SOUTHWARD is nonempty");
    }
    for car in southQueue {
      summary.append(car.toString());
    }
    // .. repeat for all queues
    return summary;
  }

}
