import Intersection;
public class Car {
  private int id;
  private int intersectionId;
  private Intersection intersectionReference;
  private int timeLeftInSegment;
  private int timeToTraverseSegment;
  private int direction;
  private int turningDirection;
  private int numBlocksBeforeTurning;
  private String summary;

  // Constructor
  public Car(int id, int intersectionId, int timeToTraverseSegment, int direction,
    int turningDirection, int numBlocksBeforeTurning, Intersection intersection) {
    this.id = id;
    this.intersectionId = intersectionId;
    this.intersectionReference = intersection;
    this.timeToTraverseSegment = timeToTraverseSegment;
    this.timeLeftInSegment = timeToTraverseSegment;
    this.direction = direction;
    this.summary = "";
    this.turningDirection = turningDirection;
    this.numBlocksBeforeTurning = numBlocksBeforeTurning;
  }

  public int getTimeLeftInSegment() {
    return timeLeftInSegment;
  }


  public int getDirection() {
    return direction;
  }

  public int getTurningDirection() {
    return turningDirection;
  }


  // Update method
  public void update() {
    timeLeftInSegment--;   
  }


  // Method that moves car to another intersection
  public void moveToNextIntersection(int nextIntersectionId) {
    appendToSummary("car#" + id + " is removed from " + direction + 
    	      " queue of intersection " + car.intersectionReference + 
            " and placed into ");
    intersectionId = nextIntersectionId;
    // HOW DO WE GET THE ACTUAL INTERSECTION
    intersectionReference.addCarToQueue(id);
    timeLeftInSegment = timeToTraverseSegment;
    appendToSummary(direction + "queue of intersection " + intersectionId);
  } 


  public boolean hasLeftGrid() {
    return (intersectionId == -1 && timeLeftInSegment == 0);
  }


  public void appendToSummary(String summary) {
    this.summary.append(summary);
  }


  // Returns 
  public String toString() {
    String temp = this.summary;
    this.summary = "";
    return temp;
  }
}