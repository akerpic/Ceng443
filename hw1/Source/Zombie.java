package e209917;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * e209917.Zombie class is derived from e209917.SimulationObject. It holds the following information's
 * <ul>
 * <li> Current state of zombie object
 * <li> Shooting range of zombie object
 * <li> Collision range of zombie object
 * <li> Detection range of zombie object
 *</ul>
 */
public abstract class Zombie extends SimulationObject{

    private ZombieState zombieState ;
    static double collisionRange;
    static double detectionRange;

    /**
     * Constructor of e209917.Zombie with following parameters
     * @param name of the e209917.Zombie
     * @param position of the e209917.Zombie
     * @param speed of the e209917.Zombie
     * e209917.SoldierState is set WANDERING by default
     */
    public Zombie(String name, Position position, double speed) {
        super(name, position, speed);
        this.setZombieState(ZombieState.WANDERING);
    }


    /**
     * @return the current state of the zombie as an instance of e209917.ZombieState.
     */
    public ZombieState getZombieState() {
        return zombieState;
    }

    /**
     * This method is used while updating state of the objects.It also update currentState.
     * @param zombieState which will be set to state of the zombie
     */
    public void setZombieState(ZombieState zombieState) {
        this.zombieState = zombieState;
        this.setCurrentState(zombieState.toString());
    }

    /**
     * This method is used to update and print new state of zombie.
     * If current zombie state is equal to given parameter then it does not update.
     * @param zombieState will be set to the state of zombie.
     */
    public void updateZombieState(ZombieState zombieState)
    {
        if(this.getZombieState() != zombieState)
        {
            setZombieState(zombieState);
            PrintObject.stateChange(this);
        }
    }

    /**
     * @return the collision range of the zombie as a double.
     */
    public double getCollisionRange() {
        return this.collisionRange;
    }

    /**
     * @return the detection range of the zombie as a double.
     */
    public  double getDetectionRange() {
        return detectionRange;
    }


    /**
     * This method overrides step method of Simulation Object class and it simulates of zombie objects in the given
     * controller.
     * If the step of the object is first step and its direction probably will be null.
     * For this reason, the method first checks the direction and if it is null then assign a random direction.
     * After that, the method calls one of the following methods depending on the current state of the object:
     * wanderingState, followingState.
     * @param controller the current controller
     */
    @Override
    public void step(SimulationController controller)
    {
        if(this.getDirection()==null)
            this.setRandomDirection();

        SimulationObject closest = findClosest(controller.getAllObjects(),"e209917.Soldier");

        try {
            if(!isKilledSoldier(closest,controller))
            {
                if(zombieState == ZombieState.WANDERING)
                    wanderingState(controller);

                else
                    followingState(controller);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NullPointerException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is an used to manage state of the zombie. This method is used by e209917.RegularZombie, e209917.FastZombie and
     * e209917.SlowZombie classes.
     * Firstly, the method find closest e209917.Soldier with using reflection. If the e209917.Soldier is in detection range
     * then, zombie state is updated(direction is optionally updated depending situation) to nextState which is given
     * as a parameter.Then, returns true.
     * If the closest e209917.Soldier is not in detection range the zombie state remains and return false.
     * @param controller which is the current controller is used to find closest enemy(e209917.Soldier).
     * @param zombieState which will set to state of zombie if closest enemy is in detection range of the zombie.
     * @param isDirectionChange which determines the direction change if closest enemy is in detection range of the zombie
     * @return boolean if closest e209917.Zombie is in shooting range returns true else returns false
     */
    boolean controlState(SimulationController controller, ZombieState zombieState, boolean isDirectionChange)
    {
        SimulationObject closest = findClosest(controller.getAllObjects(),"e209917.Soldier");

        if(getMinDistance() <= this.getDetectionRange())
        {
            if(isDirectionChange)
            {
                changeDirectionToClosest(closest.getPosition());
            }
            this.updateZombieState(zombieState);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * This method is an used to determine whether the closest soldier will be killed or not. This method is used by
     * e209917.RegularZombie, e209917.FastZombie and e209917.SlowZombie classes.
     * Firstly, the method obtain collision range of the closest enemy(soldier) by using reflection.
     * After getting the collision range, the method checks whether collision happens or not.
     * If collision is happening, then the closest enemy will be removed ,and the method returns true
     * Else, the closest will not be removed ,and the method returns false
     * @param closest Enemy(e209917.Soldier)
     * @return boolean if closest soldier is killed returns true else returns false
     */
    private boolean isKilledSoldier(SimulationObject closest, SimulationController controller) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method getCollisionRange = closest.getClass().getSuperclass().getDeclaredMethod("getCollisionRange");
        getCollisionRange.setAccessible(true);
        double closestCollisionRange = ((Number)getCollisionRange.invoke(closest)).doubleValue();

        if(getMinDistance() <= this.getCollisionRange()+closestCollisionRange)
        {
            controller.removeSimulationObject(closest);
            PrintObject.zombieKilledSoldier(this,closest);
            return true;
        }
        return false;
    }

    /**
     * This method is an abstract method that is overridden by e209917.RegularZombie, e209917.FastZombie and e209917.SlowZombie classes.
     * It is used if current state of the object is WANDERING.
     * @param controller which is the current controller is used to find closest enemy(e209917.Soldier) and update position.
     */
    public abstract void wanderingState(SimulationController controller);

    /**
     * This method is an abstract method that is overridden by e209917.RegularZombie, e209917.FastZombie and e209917.SlowZombie classes.
     * It is used if current state of the object is FOLLOWING.
     * @param controller which is the current controller is used to find closest enemy(e209917.Soldier) and update position.
     */
    public abstract void followingState(SimulationController controller);
}
