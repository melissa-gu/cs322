
simulation.update() {
	grid.update();
	for all car in cars:
		car.update();
}

car.update() {
	timeLeftInSegment--;
}

grid.update() {
	for all tf : traffic controllers:
		tf.update();
}

tf.update() {
	intersection = tf.getIntersectionReference();
	bool empty = intersection.isEmpty();
	cars = intersection.getApproachingCars();
	// If no car is in intersection right now.
	if (empty) {
    	// Get car direction carDir = car.dir()
    	// Decide which car can move into intersection.
    	if (car.canMoveBasedOnRequirementsDoc() && 
    		car.timeLeftInSegment <= 0) {
    		if (!nextSegmentIsFull(carDir))
    			moveCar(carDir);
    	}
    } else {
    	intersection.update2x2Grid();
    }
}

intersection.update2x2Grid() {
	for (each slot in grid) {
		if (slot occupied by car) {
			moveCarAlong(car.dir);
			if (car.isExitingIntersection) {
				nextIntersection = getNextIntersection(car.dir)
				car.moveToNextIntersection(nextIntersection);
			}
		}
	}
}

car.moveToNextIntersection(nextIntersection) {
	car.intersectionReference = nextIntersection;
	car.intersectionReference.addCarToQueue(carDir);
	// Car dir has same direction as queue direction
	car.timeLeftInSegment = maxSegmentCapacity;
}

intersection.moveCar(direction) {
	queue = get queue with same direction as car
	car = queue.dequeue();
	// choose slot to move car into based on the queue
	// (etc: East queue goes with SE slot)
	intersection.setSlotOccupiedBy(car)
}

