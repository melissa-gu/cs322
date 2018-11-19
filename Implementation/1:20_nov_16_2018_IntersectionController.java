
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
      if (carTurn == TrafficTesterView.NEVER_TURN) {
        // Car is going straight
        if (carTurn == otherCarTurn) {
          // Both cars are going straight
          // Only allow car to move if car direction and otherCar's 
          // direction are Northward and Southward or Eastward and Westward,
          // or the same pairs of directions but flipped.
          // The integer representation of the directions in these valid pairs
          // are different by a value of 2, thus the absolute value of carDir -
          // otherCarDir needs to be 2 for both cars to move
          return Math.abs(carDir - otherCarDir) == 2;
        }
      }
      else if (carTurn == TrafficTesterView.TURN_RIGHTWARD) {
        // Turning right
        if (otherCarTurn == TrafficTesterView.NEVER_TURN) {
          // Conflict with car going straight
          if ((carDir - otherCarDir == 1) || (carDir - otherCarDir == -3)) {
            return false;
          }
        }
      }
      else { 
        // Turning left
        if (otherCarTurn == TrafficTesterView.NEVER_TURN) {
          return false;
        }
        else if (otherCarTurn == TrafficTesterView.TURN_RIGHTWARD){
          // No conflict if the other car turning right in this specified
          // direction, otherwise conflict
          if ((carDir - otherCarDir != 1) || (carDir - otherCarDir != -3)) {
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
  } // end of canMoveBasedOnRequirementsDoc()
  

}

