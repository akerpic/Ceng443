package e209917;

/**
 * e209917.FastZombie class is derived from e209917.Zombie. It's specific information is fixed at Constant such as collisionRange,
 * detectionRange, speed.
 */
public class FastZombie extends Zombie {

    /**
     * Constructor of e209917.FastZombie with following parameters
     * @param name of e209917.FastZombie
     * @param position of e209917.FastZombie
     * CollisionRange, detectionRange are set depending on e209917.FastZombie by using e209917.Constants
     */
    FastZombie(String name, Position position) {
        super(name, position, Constants.fastZombieSpeed); // DO NOT CHANGE PARAMETERS
        collisionRange = Constants.fastZombieCollisionRange;
        detectionRange = Constants.fastZombieDetectionRange;
    }

    /**
     * @return the collision range of the e209917.FastZombie as a double.
     */
    public double getCollisionRange() {
        return FastZombie.collisionRange;
    }

    /**
     * @return the detection range of the e209917.FastZombie as a double.
     */
    public double getDetectionRange(){
        return FastZombie.detectionRange;
    }

    /**
     * This method overrides wanderingState method of e209917.Zombie class and it is used when the zombie state is WANDERING.
     *
     * First the method calls controlState with the following parameters: controller, e209917.ZombieState.FOLLOWING, true.
     * If the closest enemy(e209917.Soldier) is in detection range of the e209917.FastZombie the followings will be done:
     * 1)e209917.Zombie state is updated to FOLLOWING
     * 2)The direction of the e209917.FastZombie is changed to position of the closest enemy(e209917.Soldier)
     * 3)returns true
     *
     * If the closest enemy(e209917.Soldier) is not in detection range of the e209917.FastZombie,then controlState method returns false
     * ,and the position of the e209917.FastZombie will be updated.
     * @param controller which is the current controller is used to find closest enemy(e209917.Soldier) and update position.
     */
    @Override
    public void wanderingState(SimulationController controller)
    {
       if(!controlState(controller, ZombieState.FOLLOWING,true))
       {
           updatePosition(controller);
       }
    }

    /**
     * This method overrides followingState method of e209917.Zombie class and it is used when the zombie state is WANDERING.
     * First the method updates the position of the e209917.FastZombie.
     * Then, state of the e209917.SlowZombie will be changed to e209917.ZombieState.WANDERING.
     * @param controller which is the current controller is used to find closest enemy(e209917.Soldier) and update position.
     */
    @Override
    public void followingState(SimulationController controller)
    {
        updatePosition(controller);
        this.updateZombieState(ZombieState.WANDERING);
    }
}
