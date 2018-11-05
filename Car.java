public class Car {
	private int ID;
	private int intersectionID;
	private Intersection intersectionReference;
	private int timeLeftInSegment;
	private int timeToTraverseSegment;
	private int direction;
	private String summary;

	// Constructor
	public Car(int ID, int intersectionID, int timeToTraverseSegment, int direction) {
		this.ID = ID;
		this.intersectionID = intersectionID;
		this.intersectionReference = ??;
		this.timeToTraverseSegment = timeToTraverseSegment;
		this.timeLeftInSegment = timeToTraverseSegment;
		this.direction = direction;
		this.summary = "";
	}

	// Update method
	public void update() {
		timeLeftInSegment--;
	}

	// Method that moves car to another intersection
	public void moveToNextIntersection(int nextIntersectionID) {
		appendToSummary("car#" + ID + " is removed from " + direction + 
			            " queue of intersection " + car.intersectionReference + 
                        " and placed into ");
		intersectionID = nextIntersectionID;
		// HOW DO WE GET THE ACTUAL INTERSECTION
		addCarToQueue(ID);
		timeLeftInSegment = timeToTraverseSegment;
		appendToSummary(direction + "queue of intersection " + intersectionID);

	} 

	public boolean hasLeftGrid() {
		return (interSectionID == -1 && timeLeftInSegment == 0);
	}

	public void appendToSummary(String summary) {
		this.summary.append(summary);
	}

	public String toString() {
		String temp = this.summary;
		this.summary = "";
		return temp;
	}
}