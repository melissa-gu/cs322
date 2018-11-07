public class Car {
	private int ID;
	private int intersectionID;
	private int timeLeftInSegment;
	private int direction;
	private String summary;

	// Constructor
	public Car(int ID, int intersectionID, int timeToTraverseSegment, int direction) {
		this.ID = ID;
		this.intersectionID = intersectionID;
		this.timeLeftInSegment = timeToTraverseSegment;
		this.direction = direction;
		this.summary = "";
	}
}