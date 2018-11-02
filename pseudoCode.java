//////////////////////////////////////////////////////////////////////////
// SIMULATION FUNCTIONS
simulation.update() {
	grid.update();
	for all car in cars:
		car.update();
}

//////////////////////////////////////////////////////////////////////////
// GRID FUNCTIONS
// GRID owns a list of Traffic Controllers and a 2D array of intersections.
// Each TrafficController is assigned a corresponding Intersection when the
// trafficController instance is initialized in Grid.
grid.update() {
	for traffic_controller : traffic_controllers:
		traffic_controller.update();
}

//////////////////////////////////////////////////////////////////////////
// TRAFFIC CONTROLLER functions
// Traffic controller owns an Intersection instance to handle called my_intersection
trafficCongroller.update() {
	// Check whether no car is traveling within the 2x2 grid of the intersection
	bool empty = my_intersection.isEmpty();
	cars = my_intersection.getApproachingCars();
	if (empty) {
		for (car : cars) {
	    	// Get car direction carDir = car.dir()
	    	// Decide which car can move into intersection based on:
	    	// 1. priority of the car instance's turning direction, specified in Requirements Doc
	    	// 2. whether the car's timeLeftInSegment has reached 0 
	    	//   (already checked in intersection.getApproachingCars())
	    	// 3. Whether the outgoing segment is full.
	    	if (car.canMoveBasedOnRequirementsDoc() && 
	    		!my_intersection.nextSegmentIsFull(car)) {
	    		my_intersection.moveCarIntoIntersection(car);
	    	}
    	}
    } else {
    	my_intersection.update2x2Grid();
    }
}


//////////////////////////////////////////////////////////////////////////
// INTERSECTION functions
// Each Intersection instance owns 4 instances of queues, representing the 4 ingoing
// segments.

intersection.isEmpty() {
	// Check 4 slots of the 2x2 intersection grid (represented as a 2D int array).
	// if any slots contains a carID (>= 0) instead of -1, return false.
	// else if all 4 slots' values are -1 (indicating vacancy), return true.
}

intersection.getApproachingCars() {
	// Check the 4 queues that represent the ingoing segments, and only add
	// cars that are the first to be in the queue (FIFO) AND has finished 
	// traversing the segments (timeLeftInSegment = 0).
	ArrayList<Car> approachingCars;
	for (queue : ingoing_segments) {
		Car potentialCar = queue.peek();
		if (potentialCar.timeLeftInSegment <= 0) {
			approachingCars.add(potentialCar);
		}
	}
	return approachingCars;
}

intersection.nextSegmentIsFull (car) {
	// Calculate the car's next direction (N/W/E/S) when entering the next intersection
	// based on the car's current directiom (N/W/E/S) and turning direction (straight/left/right).
	nextDirection = calculateNextDirection(car.dir, car.turning_dir);
	// Get the next intersection based on the car's next direction.
	nextIntersection = this.getNextIntersection(nextDirection);
	return nextIntersection.checkIfQueueIsFull(nextDirection);
}

intersection.checkIfQueueIsFull(nextDirection) {
	switch (nextDirection) {
		case 0: // Southwards
			return southQueue.size() >= MAX_SEGMENT_CAPACITY;
		/// ....
	}
}

intersection.moveCarIntoIntersection(car) {
	queue = get correct queue among the 4 queues, with same direction as car
	// Dequeue the approaching car. Because moveCar() is only invoked when the conditions of the
	// approaching car is satisfied, and the car's direction should be unique among all the approaching
	// cars, we always dequeue the intended car correctly.
	car = queue.dequeue();
	// choose the 2x2 grid slot to move car into based on the car direction
	// (etc: East queue and car going East corresponds to the SE slot of the 2x2 intersection grid)
	// in set2x2SlotOccupied(car), set the slot to contains the integer id of the car instead of -1.
	intersection.set2x2SlotOccupied(car)
}


intersection.update2x2Grid() {
	for (each slot in grid) {
		if (slot occupied by car) {
			moveCarAlong(car.dir);
			if (car.isExitingIntersection()) {
				// Based on the car direction, retrieves the "next" intersection
				// of this current intersection and set the car's intersection
				// reference to that intersection.
				nextIntersection = this.getNextIntersection(car.dir)
				car.moveToNextIntersection(nextIntersection);
			}
		}
	}
}

// Invoked in the function moveToNextIntersection below.
intersection.addCarToQueue(car) {
	switch(car.dir()) {
		case 0: // Car moving South
			southQueue.enqueue(car);
			break;
		// ...
	}
}

//////////////////////////////////////////////////////////////////////////
// CAR functions
// car contains a reference to an intersection the car is currently moving through
// or moving towards.
car.moveToNextIntersection(nextIntersection) {
	car.intersectionReference = nextIntersection;
	// Car dir has same direction as queue direction, so the intersection will
	// add the car to the corresponding queue.
	car.intersectionReference.addCarToQueue(car);
	car.timeLeftInSegment = maxSegmentCapacity;
}

// Update is called at each time step, invoked by the Simulation.update() function
// timeLeftInSegment is reset to MAX_SEGMENT_CAPACITY when the car is first spawn or 
// when the car just exits the intersection and is added to the queue of the next intersection.
car.update() {
	timeLeftInSegment--;
}


