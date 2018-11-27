
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
  private String carsRemoved;

  public Simulation(int numIntersectionsInOneDirection) {
    this.numIntersectionsInOneDirection = numIntersectionsInOneDirection;
    grid = new Grid(numIntersectionsInOneDirection);
    cars = new ArrayList<Car>();
  } // End of constructor Simulation()


  public void update() {
    carsRemoved = "";
    grid.update();
    ArrayList<Car> exitedCars = new ArrayList<Car>();
    for (Car car : cars) {
      car.update();
      // If car is exiting the grid
      if (car.hasLeftGrid()) {
        carsRemoved += "car#" + car.getId() + " has left the grid\n";
        exitedCars.add(car);
        numCarsExited++;
        double timeToExit = car.getExitTime() - car.getEntryTime();
        sumOfAllCarTimesToExit += timeToExit;
      } // end for (Car car: cars)
    }

    cars.removeAll(exitedCars);
  } // end of update ()
  

  public void insertCar(int carID, int col, int row, int segmentDirectionCode,
                        int numBlocksBeforeTurning, int turnDirectionCode) {
    Intersection intersectionReference = grid.getIntersection(row, col);
    Car car = new Car(carID, segmentDirectionCode, turnDirectionCode,
      numBlocksBeforeTurning, intersectionReference);
    cars.add(car);
  } // end of insertCar()


  public String toString() {
    // prints summary of the grid and summary of cars that have left grid
    return grid.toString() + carsRemoved;
  } // end of toString()


  // prints average time cars took to exit the grid
  public String getAverage() {
    String result = "";
    if (numCarsExited > 0) {
      result += "The average time in the grid for cars that left the grid is ";
      result += sumOfAllCarTimesToExit/numCarsExited;
    } // end of if (numCarsExited > 0)
    else {
      result += "No cars left the grid";
    }
    
    return result;
  } // end of getAverage()


}
