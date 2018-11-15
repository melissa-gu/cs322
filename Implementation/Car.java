
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
  private int turningDirection;
  private int numBlocksBeforeTurning;
  private String summary;
  private int entryTime;
  private int exitTime;

  // Car Constructor
  public Car(int id, int direction, int turningDirection, 
             int numBlocksBeforeTurning, Intersection intersection) {
    this.id = id;
    this.intersectionReference = intersection;
    this.direction = direction;
    this.summary = "";
    this.turningDirection = turningDirection;
    this.numBlocksBeforeTurning = numBlocksBeforeTurning;
    intersectionReference.addCarToQueue(this);
  } // end of Car() constructor


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


  public double getEntryTime() {
    return entryTime;
  } // end of getEntryTime()


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
  } // end of update()


  // Moves car to another Intersection instance
  public void moveToNextIntersection(Intersection nextIntersection) {
    numBlocksBeforeTurning--;
    intersectionReference = nextIntersection;
    if (intersectionReference != null) {
      intersectionReference.addCarToQueue(this);
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
