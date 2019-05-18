package e209917;

import java.util.ArrayList;
import java.util.Random;

/**
 * Simulation Object is the abstract class for all objects that are in the simulation.It encapsulates the object information.
 * This state information includes:
 * <ul>
 * <li> Name of the object
 * <li> e209917.Position of the object
 * <li> Speed of the object
 * <li> Activity information of the object
 * <li> Current state of the object
 * <li> Minimum Distance to closest enemy objects
 *</ul>
 */
public abstract class SimulationObject {
    private final String name;
    private Position position;
    private Position direction;
    private final double speed;
    private boolean active;
    private String currentState;
    private double minDistance;

    /**
     *Constructor of Simulation Object
     * <p>
     * It sets the direction to null and active to true by default
     * @param  name
     * @param  position
     * @param  speed
     */
    public SimulationObject(String name, Position position, double speed) {
        this.name = name;
        this.position = position;
        this.speed = speed;
        this.direction = null;
        this.active = true;
    }

    /**
     * @return the name of the e209917.SimulationObject as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the current position of the e209917.SimulationObject as an instance of the e209917.Position class.
     */
    public Position getPosition() {
        return position;
    }


    /**
     * This method is used while updating position of the objects.
     * @param position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Direction of the objects is always normalized.
     * @return the current direction of the e209917.SimulationObject as an instance of the e209917.Position class.
     */
    public Position getDirection() {
        return direction;
    }


    /**
     * This method is used while updating direction of the objects.
     * @param direction must be normalized.
     */
    public void setDirection(Position direction) {
        this.direction = direction;
    }

    /**
     * Speed of the objects is final. It is given at e209917.Constants.
     * @return the speed of the e209917.SimulationObject as a double.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Activity of the objects is set as false when the object is killed.
     * @return the activity information of the e209917.SimulationObject as a boolean.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * This method is used while updating activity information of the objects usually when the object dies.
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * This method is used by e209917.PrintObject class while printing state of the object when it is changed.
     * @return the current state of the e209917.SimulationObject as a String.
     */
    public String getCurrentState() {
        return currentState;
    }

    /**
     * This method is used while updating state of the objects.
     * @param currentState
     */
    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    /**
     * This method is used when the object moves. This method is used directly by e209917.Soldier and e209917.Zombie classes.
     * e209917.Bullet class overrides this method.
     * <p>
     *  * This method first calculate the next position of the object ,and if the calculated position is inside of the
     *  grid ,then updates the position of the object and print the position change.If the calculated position is not inside then direction of the
     *  object is updated randomly.
     * @param  controller
     */
    public void updatePosition(SimulationController controller)
    {
        double x = this.getPosition().getX()+this.getDirection().getX()*this.getSpeed();
        double y = this.getPosition().getY()+this.getDirection().getY()*this.getSpeed();
        Position newPosition = new Position(x,y);

        if(isInside(newPosition,controller))
        {
            this.setPosition(newPosition);
            PrintObject.positionChange(this);
        }
        else
        {
            setRandomDirection();
        }
    }

    /**
     * This method is used to calculate new position of the object is inside or not of the controller's grid.
     * @param  position gives the x and y coordinates which is used while calculation
     * @param  controller gives the grid information
     * @return boolean if it is inside return true ,else false.
     */
    static boolean isInside(Position position, SimulationController controller)
    {
        return !(position.getX() < 0) && !(position.getX() > controller.getWidth())
                && !(position.getY() < 0) && !(position.getY() > controller.getHeight());
    }

    /**
     * This method is used to set a random direction to the object and prints it.
     * It uses generateRandomDirection in order to create random direction.
     */
    void setRandomDirection()
    {
        setDirection(generateRandomDirection());
        PrintObject.directionChange(this);
    }

    /**
     * This method is used to create a random direction.
     * After the direction created, it is normalized.
     */
    Position generateRandomDirection()
    {
        double start = -100;
        double end = 100;

        double x = start + (new Random().nextDouble()*(end-start));
        double y = start + (new Random().nextDouble()*(end-start));

        Position newDirection = new Position(x,y);
        newDirection.normalize();

        return newDirection;
    }

    /**
     * This method is used to calculate new position of the object is inside or not of the controller's grid.
     * @param  pos gives the x and y coordinates which is used while calculating distance of position of this object and given pos
     * @return distance is always greater o equal to 0 because of sqrt.
     */
    private double calcDistance(Position pos)
    {
        return Math.sqrt( Math.pow(this.getPosition().getX() - pos.getX(),2) +  Math.pow(this.getPosition().getY() - pos.getY(),2));
    }

    /**
     * This method is used to find closest object whose class name is taken as a parameter.
     * While iterating all objects of the simulation, reflection is used in order to find instanes of desired class.
     * Furthermore, while iterating it checks whether the object is active or not.
     * @param  simulationObjects gives all objects of the simulation.
     * @return e209917.SimulationObject closest simulation object
     */
    SimulationObject findClosest(ArrayList<SimulationObject> simulationObjects, String className)
    {
        SimulationObject closest = null;
        minDistance = Double.MAX_VALUE;
        double distance;

        for (SimulationObject simulationObject : simulationObjects)
        {
            if(simulationObject.isActive() && simulationObject.getClass().getSuperclass().getName().equals(className))
            {
                distance = calcDistance(simulationObject.getPosition());
                if (minDistance > distance)
                {
                    minDistance = distance;
                    closest = simulationObject;
                }
            }
        }

        return closest;
    }

    /**
     * This method is used to change direction of the object to given position.
     * While calculating direction it is normalized.
     * After calculation process, this method prints the direction change.
     * @param position
     */
    void changeDirectionToClosest(Position position)
    {
        double x = position.getX() - this.getPosition().getX();
        double y = position.getY() - this.getPosition().getY();
        Position newDirection = new Position(x,y);
        newDirection.normalize();
        this.setDirection(newDirection);
        PrintObject.directionChange(this);
    }

    /**
     * This method is used to get min distance to closest enemy object.
     * @return min distance to closest enemy objects
     */
    double getMinDistance() {
        return minDistance;
    }

    /**
     * This method is used simulate SimulationObjects at every steps.
     * This method is overriding by e209917.Bullet, e209917.Soldier and e209917.Zombie classes.
     * @param controller
     */
    public abstract void step(SimulationController controller);

}
