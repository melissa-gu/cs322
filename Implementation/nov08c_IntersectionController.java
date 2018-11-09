// Authors: SEALS (Jessica, Melissa, Tracy, Will)

// *****************************************************************************
// *****************************************************************************
// **** IntersectionController
// *****************************************************************************
// *****************************************************************************

public class IntersectionController {

  private Intersection myIntersection;
  private ArrayList<Car> cars;
  
	// Constructor
	public IntersectionController (Intersection intersection) {
    myIntersection = intersection;
	}

	public void update() {
    // Check whether no car is traveling within the 2x2 grid of the intersection
    boolean empty = my_intersection.intersectionIsEmpty();
    cars = myIntersection.getApproachingCars();

    if (empty) {
    	for (Car car : cars) {
        // Decide which car can move into intersection based on:
        // 1. priority of the car instance's turning direction, specified in
        //  Requirements Doc
        // 2. whether the car's timeLeftInSegment has reached 0
        //   (already checked in intersection.getApproachingCars())
        // 3. Whether the outgoing segment is full.
        if (canMoveBasedOnRequirementsDoc(car) &&
          !myIntersection.nextSegmentIsFull(car)) {
            myIntersection.moveCarIntoIntersection(car);
          }
        }
    } else {
        myIntersection.updateIntersectionGrid();
      }
        
  }
  

  private boolean canMoveBasedOnRequirementsDoc(Car curCar) {
    int carTurn = curCar.getTurningDirection();
    int carDir = curCar.getDirection()

    // Loop through approaching cars to check whether the current car can move
    for (Car car : cars) {
      if (car == curCar) {
        continue;
      }
      int otherCarTurn = car.getTurningDirection();
      int otherCarDir = car.getDirection();
      if (carTurn == 0) {
        // Go straight
        if (carTurn == otherCarTurn) {
          if (carDir > otherCarDir) {
            return false;
          }
        }
      }
      else if (carTurn == 1) {
        // Turn right
        if (otherCarTurn == 0) {
          // Conflict with car going straight
          if((carDir - otherCarDir == 1) || (carDir - otherCarDir == -3)){
            return false;
          }
        }
      }
      else {
        // Turn left == -1
        if (otherCarTurn == 0) {
          return false;
        }
        else if (otherCarTurn == 1){
          // No conflict if the other car turning right in this specified
          // direction, otherwise conflict
          if((carDir - otherCarDir != 1) || (carDir - otherCarDir != -3)){
            return false;
          }
        } else {
            if (carDir > otherCarDir) {
              return false;
            }
        }

      }

    }
    return true;
  }

}

