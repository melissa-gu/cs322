
// Authors: SEALS (Jessica, Melissa, Tracy, Will)
// *****************************************************************************
// *****************************************************************************
// **** Car
// *****************************************************************************
// *****************************************************************************
// The Car class is a representation of a car in the simulation. Cars are
// constructed in the Simulation class as a result of the input received from
// TrafficTesterView. Each car has a series of variables that are used to
// determine the route taken by the Car inside the Grid instance. In this design
// intersections send messages to cars to get their attributes and determine 
// where to move them in the Grid instance.
import java.util.*;

public class Car {
  private int id;
  private int intersectionId;
  private Intersection intersectionReference;
  private int direction;
  private int turningDirection;
  private int numBlocksBeforeTurning;
  private String summary;
  private int entryTime;
  private int exitTime;

  // Constructor
  public Car(int id,
             int direction, int turningDirection, int numBlocksBeforeTurning,
             Intersection intersection) {
    this.id = id;
    this.intersectionId = intersectionId;
    this.intersectionReference = intersection;
    this.direction = direction;
    this.summary = "";
    this.turningDirection = turningDirection;
    this.numBlocksBeforeTurning = numBlocksBeforeTurning;
    intersectionReference.addCarToQueue(this);
  }


  public int getDirection() {
    return direction;
  }


  public void setDirection (int direction) {
    direction = direction;
  }


  public int getTurningDirection() {
    return turningDirection;
  }


  public void setTurningDirection(int direction) {
    turningDirection = direction;
  }


  public double getExitTime() {
    return exitTime;
  }


  public double getEntryTime() {
    return entryTime;
  }


  public int getId() {
    return id;
  }


  // Update method
  public void update() {

  }


  // Method that moves car to another intersection
  public void moveToNextIntersection(Intersection nextIntersection) {
    appendToSummary("car#" + id + " is removed from " + direction +
    	            " queue of intersection " + intersectionReference +
                    " and placed into ");
    intersectionReference = nextIntersection;
    intersectionReference.addCarToQueue(this);
    appendToSummary(direction + "queue of intersection " +
                    intersectionReference.getId());
  }


  public boolean hasLeftGrid() {
    return (intersectionReference == null);
  }


  public void appendToSummary(String summary) {
    this.summary += summary;
  }


  // Returns
  public String toString() {
    String temp = this.summary;
    this.summary = "";
    return temp;
  }
}
