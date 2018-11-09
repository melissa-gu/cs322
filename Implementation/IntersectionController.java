// Authors: SEALS (Jessica, Melissa, Tracy, Will)

// *****************************************************************************
// *****************************************************************************
// **** IntersectionController
// *****************************************************************************
// *****************************************************************************

import java.util.*;

public class IntersectionController {

  private Intersection myIntersection;
  private ArrayList<Car> cars;

	// Constructor
	public IntersectionController(Intersection intersection) {
    myIntersection = intersection;
	}

	public void update() {
    // Check whether no car is traveling within the 2x2 grid of the intersection
    boolean empty = myIntersection.intersectionIsEmpty();
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
          !myIntersection.nextSegmentIsFull(car) ) {
            myIntersection.moveCarIntoIntersection(car);
        }
      } // end of (for Car car : cars)
    } else {
      myIntersection.updateIntersectionGrid();
    }
  } // end of update()
  

  private boolean canMoveBasedOnRequirementsDoc(Car curCar) {
    return false;
  } // end of canMoveBasedOnRequirementsDoc()

}

