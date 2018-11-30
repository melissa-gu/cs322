
// Authors: SEALS (Jessica, Melissa, Tracy, Will)
// *****************************************************************************
// *****************************************************************************
// **** Car
// *****************************************************************************
// *****************************************************************************

import java.util.*;

public class Car {
  private int id;
  private Intersection intersectionReference;
  private int direction;
  private int timeLeftInSegment;
  private int timeToTraverseSegment;
  private int turningDirection;
  private int numBlocksBeforeTurning;
  private String summary;
  private int exitTime;

  // Car Constructor
  public Car(int id, int direction, int turningDirection, 
             int timeToTraverseSegment, int numBlocksBeforeTurning, 
             Intersection intersection, boolean outsideGrid) {
    this.id = id;
    this.intersectionReference = intersection;
    this.timeToTraverseSegment = timeToTraverseSegment;
    if (outsideGrid) {
      this.timeLeftInSegment = 0;
    } else {
      this.timeLeftInSegment = timeToTraverseSegment;
    }
    this.direction = direction;
    this.summary = "";
    this.turningDirection = turningDirection;
    this.numBlocksBeforeTurning = numBlocksBeforeTurning;
    intersectionReference.addCarToQueue(this);
  } // end of Car() constructor


  public int getTimeLeftInSegment() {
    return timeLeftInSegment;
  } // end of getTimeLeftInSegment()

  public int getDirection() {
    return direction;
  } // end of getDirection()


  public void setDirection (int direction) {
    this.direction = direction;
  } // end of setDirection()


  public int getTurningDirection() {
    return turningDirection;
  } // end of getTurningDirection()


  public void setTurningDirection(int direction) {
    turningDirection = direction;
  } // end of setTurningDirection()


  public double getExitTime() {
    return exitTime;
  } // end of getExitTime()

  public void setExitTime(int timeStep) {
    exitTime = timeStep;
  } // end of setExitTime()

  public int getId() {
    return id;
  } // end of getId()


  public int getNumBlocksBeforeTurning() {
    return numBlocksBeforeTurning;
  } // end of getNumBlocksBeforeTurning

  // Decrements the time remaining in a segment before Car can move 
  // into Intersection
  public void update() {
    // the current subset does not handle time remaining in segment
    timeLeftInSegment--;
  } // end of update()


  // Moves car to another Intersection instance
  public void moveToNextIntersection(Intersection nextIntersection) {
    numBlocksBeforeTurning--;
    intersectionReference = nextIntersection;
    if (intersectionReference != null) {
      intersectionReference.addCarToQueue(this);
      // Because car.update() decrements timeLeftInSegment at every
      // time step, this ensures that each car waits the full
      // timeToTraverseSegment.
      timeLeftInSegment = timeToTraverseSegment;
    }
    if (numBlocksBeforeTurning < 0) {
      turningDirection = TrafficTesterView.NEVER_TURN;
    }
  } // end of moveToNextIntersection()


  public boolean hasLeftGrid() {
    return (intersectionReference == null);
  } // end of hasLeftGrid()
  

  // Returns String summary of Car
  public String toString() {
    String temp = this.summary;
    this.summary = "";
    return temp;
  } // end of toString()


}
