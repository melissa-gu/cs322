
// Authors: SEALS (Jessica, Melissa, Tracy, Will)
// *****************************************************************************
// *****************************************************************************
// **** IntersectionController
// *****************************************************************************
// *****************************************************************************

import java.util.*;

public class IntersectionController {

  private Intersection myIntersection;
  private ArrayList<Car> approachingCar;

	// Constructor
	public IntersectionController(Intersection intersection) {
    myIntersection = intersection;
	}

	public void update() {
    myIntersection.updateSegmentSummary();
    // Check whether no car is traveling within the 2x2 grid of the intersection
    boolean empty = myIntersection.intersectionIsEmpty();
    approachingCar = myIntersection.getApproachingCars();
    if (true) {
    	for (Car car : approachingCar) {
        // Decide which car can move into intersection based on:
        // 1. priority of the car instance's turning direction, specified in
        //  Requirements Doc
        // 2. whether the car's timeLeftInSegment has reached 0
        //   (already checked in intersection.getApproachingCars())
        // 3. Whether the outgoing segment is full.
        if (canMoveBasedOnRequirementsDoc(car) &&
          !myIntersection.nextSegmentIsFull(car) ) {
            myIntersection.moveCarIntoIntersectionGrid(car);
            break;
        }
      } // end of (for Car car : cars)
    } else {
      // myIntersection.updateIntersectionGrid();
    }
  } // end of update()
  

  private boolean canMoveBasedOnRequirementsDoc(Car car) {
    int carTurn = car.getTurningDirection();
    int carDir = car.getDirection();

    // Loop through approaching cars to check whether the current car can move
    for (Car otherCar : approachingCar) {
      if (otherCar == car) {
        continue;
      }

      int otherCarTurn = otherCar.getTurningDirection();
      int otherCarDir = otherCar.getDirection();
      // If current car and other car have the same turning direction, then
      // we prioritize cars based on the direction they aim.
      if (carTurn == otherCarTurn) {
        if (carDir > otherCarDir) {
          return false;
        } // end of if (carDir > otherCarDir)
      } // end of if (carTurn == otherCarTurn)

      // If the other car is going straight and the current car
      // is turning right or turning left, then the other car has priority.
      if (otherCarTurn == TrafficTesterView.NEVER_TURN) {
        if (carTurn != TrafficTesterView.NEVER_TURN) {
          return false;
        } // end of if (carTurn != NEVER_TURN)
      } // end of if (otherCarTurn == NEVER_TURN)

      // If the other car is turning right and the current car is turning left,
      // then the other car has priority.
      if (otherCarTurn == TrafficTesterView.TURN_RIGHTWARD) {
        if (carTurn == TrafficTesterView.TURN_LEFTWARD) {
          return false;
        } // end of if (carTurn == TURN_LEFTWARD)
      } // end of if (otherCarTurn == TURN_RIGHTWARD)
    }

    // In all other cases, the current car has priority over the other cars.
    return true;
  } // end of canMoveBasedOnRequirementsDoc()
  

}

