// Authors: SEALS (Jessica, Melissa, Tracy, Will)

// *****************************************************************************
// *****************************************************************************
// **** Simulation
// *****************************************************************************
// *****************************************************************************

public class Simulation {
  private int numIntersectionsInOneDirection;
  private int numberOfCars;
  private double sumOfAllCarTimesToExit;
  private int numCarsExited;
  
  public Simulation(int numIntersectionsInOneDirection, int numberOfCars) {
    this.numIntersectionsInOneDirection = numIntersectionsInOneDirection;
    this.numberOfCars = numberOfCars;
  }


  public void update() {
      grid.update();
      for all car in activeCars:
          car.update();
          // If car is exiting the grid
          if (car.hasLeftGrid()) {
              car.appendToSummary("car#" + car.ID + " has left the grid");
              activeCars.remove(car);
              exitedCars.add(car);
              numCarsExited++;
              timeToExit = car.getExitTime() - car.getEntryTime();
              sumOfAllCarTimesToExit += timeToExit;
          }
    }
  } // end of update ()


  public void insertCar(int carID, int col, int row, int segmentDirectionCode, 
                        int numBlocksBeforeTurning, int turnDirectionCode) {
    Intersection intersectionReference = grid.getIntersection(row, col);
    Car car = new Car(carID, segmentDirectionCode, turnDirectionCode,
      numBlocksBeforeTurning, intersectionReference);
  } // end of insertCar()


  public String toString() {
    return grid.toString();
  } // end of toString()



// prints average time cars took to exit the grid
  public double getAverage() {
    String result;
    result += "The average time cars took to exit the grid was ";
    result += sumOfAllCarTimesToExit/numCarsExited;
    result += " simulated time-steps";
    return result;
  }
