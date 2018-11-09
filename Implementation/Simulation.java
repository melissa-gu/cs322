// Authors: SEALS (Jessica, Melissa, Tracy, Will)

// *****************************************************************************
// *****************************************************************************
// **** Simulation
// *****************************************************************************
// *****************************************************************************

import java.util.*;

public class Simulation {
  private int numIntersectionsInOneDirection;
  private double sumOfAllCarTimesToExit;
  private int numCarsExited;
  private Grid grid;
  private ArrayList<Car> cars;
  private ArrayList<Car> exitedCars;
  
  public Simulation(int numIntersectionsInOneDirection) {
    System.out.println("Num Intersection: " + numIntersectionsInOneDirection);
    this.numIntersectionsInOneDirection = numIntersectionsInOneDirection;
    grid = new Grid(numIntersectionsInOneDirection);
    cars = new ArrayList<Car>();
    exitedCars = new ArrayList<Car>();
  } // End of constructor Simulation()


  public void update() {
      grid.update();
      for (Car car : cars) {
        car.update();
        // If car is exiting the grid
        if (car.hasLeftGrid()) {
          car.appendToSummary("car#" + car.getId() + " has left the grid");
          cars.remove(car);
          exitedCars.add(car);
          numCarsExited++;
          double timeToExit = car.getExitTime() - car.getEntryTime();
          sumOfAllCarTimesToExit += timeToExit;
      }
    }
  } // end of update ()


  public void insertCar(int carID, int col, int row, int segmentDirectionCode, 
                        int numBlocksBeforeTurning, int turnDirectionCode) {
    Intersection intersectionReference = grid.getIntersection(row, col);
    Car car = new Car(carID, segmentDirectionCode, turnDirectionCode,
      numBlocksBeforeTurning, intersectionReference);
    cars.add(car);
  } // end of insertCar()


  public String toString() {
    return grid.toString();
  } // end of toString()


  // prints average time cars took to exit the grid
  public String getAverage() {
    String result = "";
    result += "The average time cars took to exit the grid was ";
    result += sumOfAllCarTimesToExit/numCarsExited;
    result += " simulated time-steps";
    return result;
  }
}