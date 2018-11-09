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
    cars = my_intersection.getApproachingCars();

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
    // boolean value representing curCar has priority to move
    boolean priority = true;
    int carTurn = curCar.getTurningDirection();
    int carDir = curCar.getDirection()

    for (Car car : cars) {
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
      else if (carTurn == 1){
        // Turn right
        if(otherCarTurn == 0){
          if(carDir - otherCarDir == 1){
            return false;
          }
        }
        else if(carTurn == otherCarTurn){

        }
      }
      else {
        // Turn left == -1

      }
    }
    return priority;

  }

}

