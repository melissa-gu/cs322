// Authors: SEALS (Jessica, Melissa, Tracy, Will)

// *****************************************************************************
// *****************************************************************************
// **** Intersection
// *****************************************************************************
// *****************************************************************************


public class Intersection {
  // id identifies which intersection this Intersection instance is.
  private int id;

  // Queues representing the four ingoing segments. The int values of the
  // direction code for S, E, N, W - (0,1,2,3) respectively - correspond to the
  // indices of this array of Queue.
  private Queue[] ingoing_segments;

  // Four next intersections of this intersection. The int values of the
  // direction code for S, E, N, W - (0,1,2,3) respectively - correspond to the
  // indices of this array of Intersection instances.
  private Intersection[] nextIntersections;

  // Setters and getters
  public int getId() {
    return id;
  }

  public setNextIntersections(Intersection[] nextIntersections) {
    this.nextIntersections = nextIntersections;
  }

  // Constructor
  public Intersection(int id) {
    this.id = id;
    
    for (Queue segment : ingoing_segments) {
      segment = new PriorityQueue();
    } // end of for (segment : ingoing_segments)
  }

}

// static final int SOUTHWARD = 0;

// static final int EASTWARD = 1;

// static final int NORTHWARD = 2;

// static final int WESTWARD = 3;
// The class name: Intersection
// Responsibility:
// 1. Receive message from Grid instance with integer ID to construct instance of
//    Intersection class
// 2. Initialize 2D integer array of size 4 representing the four slots within an
//    intersection that could contain integer car IDs
// 3. Initialize four integer queues corresponding to four directions that could
//    contain integer car IDs. Each queue represents list of Car instances
//    occupying the segment that aims towards the Intersection instance
// 4. Receive message from Car instances about direction the Car will proceed
//    through the Intersection instance
// 5. Receive message from IntersectionController instance at each time-step to
//    update the state of the Intersection. This message could contain information
//    about which Car instance(s) to dequeue (if such a Car instance exists) and
//    how to update Car instances already in the 2D array.
// 6. Update the state of the Intersection in the following ways:
//    6.1 Dequeue cars that can move into the Intersection instance according
//        to the IntersectionController message
//    6.2 Update the 2D array to reflect the movement of Car instances in the
//        Intersection
//    6.3 Send message to Car instances that have left the Intersection instance.
//        This message should tell the car which Intersection instance the
//        car is entering next, and also which direction the Car is traveling. If
//        the car is no longer traveling towards an intersection, but is instead
//        exiting the instance of Grid, the intersection ID for the next
//        Intersection instance will be -1
// 7. Receive messages from Car instances about which queue to enqueue the Car
//    into, and enqueue that Car instance
// 8. Sends message to Intersection Controller if an instance of Car can enter the
//    queue for the next Intersection based on the direction of the Car's turn and
//    the Car's current direction.
// 9. Receive message from Car instance at each time-step containing a String 
//    summary of that car's status at that time-step (e.g. "car#4 is removed
//    from SOUTHWARD queue of intersection [1,2] and placed into SOUTHWARD 
//    queue of intersection [1,1]").
// 10. Send message to Grid instance at each time-step containing a String summary 
//     of the segments and the cars occupying the segments of this Intersection

intersection.isEmpty() {
    // Check slots of the 2x2 intersection grid (represented as a 2D int array).
    // if any slots contains a carID (>= 0) instead of -1 return false.
    // else if all 4 slots' values are -1 (indicating vacancy), return true.
}

intersection.getApproachingCars() {
    // Check the 4 queues that represent the ingoing segments, and only add
    // cars that are the first to be in the queue (FIFO) AND has finished
    // traversing the segments (timeLeftInSegment = 0).
    ArrayList<Car> approachingCars;
    for (queue : ingoing_segments) {
        Car potentialCar = queue.peek();
        if (potentialCar.timeLeftInSegment <= 0) {
            approachingCars.add(potentialCar);
        }
    }
    return approachingCars;
}

intersection.nextSegmentIsFull (car) {
    // Calculate the car's next direction (N/W/E/S) when entering the next
    // intersection based on the car's current direction (N/W/E/S) and turning
    // direction (straight/left/right).
    nextDirection = calculateNextDirection(car.dir, car.turning_dir);
    // Get the next intersection based on the car's next direction.
    nextIntersection = this.getNextIntersection(nextDirection);
    return nextIntersection.checkIfQueueIsFull(nextDirection);
}

intersection.checkIfQueueIsFull(nextDirection) {
    switch (nextDirection) {
        case 0: // Southwards
            return southQueue.size() >= MAX_SEGMENT_CAPACITY;
        /// ....
    }
}

intersection.moveCarIntoIntersection(car) {
    queue = get correct queue among the 4 queues, with same direction as car
    // Dequeue the approaching car. Because moveCar() is only invoked when the
    // conditions of the approaching car is satisfied, and the car's direction
    // should be unique among all the approaching cars, we always dequeue the
    // intended car correctly.
    car = queue.dequeue();
    // choose the 2x2 grid slot to move car into based on the car direction
    // (etc: East queue and car going East corresponds to the SE slot of the 2x2
    //  intersection grid) in set2x2SlotOccupied(car), set the slot to contains
    // the integer id of the car instead of -1.
    intersection.set2x2SlotOccupied(car)
    if (car.turningCode == RIGHT) {
        car.dir = calculateNextDirection(car.dir, car.turning_dir);
    }
}

intersection.update2x2Grid() {
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

// Invoked in the function moveToNextIntersection below.
intersection.addCarToQueue(car) {
    switch(car.dir()) {
        case 0: // Car moving South
            southQueue.enqueue(car);
            break;
        // ...
    }
}

// Returns summary of the intersection queues and cars
intersection.toString() {
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
