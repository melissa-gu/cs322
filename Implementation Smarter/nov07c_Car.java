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


  public int setTurningDirection(int direction) {
    turningDirection = direction;
  }


  public int getId() {
    return id;
  }


  // Update method
  public void update() {
    timeLeftInSegment--;   
  }


  // Method that moves car to another intersection
  public void moveToNextIntersection(Intersection nextIntersection) {
    appendToSummary("car#" + id + " is removed from " + direction + 
    	      " queue of intersection " + car.intersectionReference + 
            " and placed into ");
    intersectionReference = nextIntersection;
    intersectionReference.addCarToQueue(this);
    timeLeftInSegment = timeToTraverseSegment;
    appendToSummary(direction + "queue of intersection " + 
        intersectionReference.getId());
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