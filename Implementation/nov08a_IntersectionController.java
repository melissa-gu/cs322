// Authors: SEALS (Jessica, Melissa, Tracy, Will)

// *****************************************************************************
// *****************************************************************************
// **** IntersectionController
// *****************************************************************************
// *****************************************************************************

public class IntersectionController {

  private Intersection myIntersection;

	// Constructor
	public IntersectionController (Intersection intersection) {
    myIntersection = intersection;
	}

	public void update() {
    // Check whether no car is traveling within the 2x2 grid of the intersection
    bool empty = my_intersection.intersectionIsEmpty();
    ArrayList<Car> cars = my_intersection.getApproachingCars();

    if (empty) {
    	for (Car car : cars) {
    		int carDir = car.getDirection()

        // Decide which car can move into intersection based on:
        // 1. priority of the car instance's turning direction, specified in
        //  Requirements Doc
        // 2. whether the car's timeLeftInSegment has reached 0
        //   (already checked in intersection.getApproachingCars())
        // 3. Whether the outgoing segment is full.
        if (car.canMoveBasedOnRequirementsDoc() &&
          !my_intersection.nextSegmentIsFull(car)) {
            my_intersection.moveCarIntoIntersection(car);
          }
        
        } else {
          my_intersection.update2x2Grid();
        }
      }
    }
  }
}

