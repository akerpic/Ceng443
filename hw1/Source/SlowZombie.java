package e209917;

/**
 * e209917.SlowZombie class is derived from e209917.Zombie. It's specific information is fixed at Constant such as collisionRange,
 * detectionRange, speed.
 */
public class SlowZombie extends Zombie{
    /**
     * Constructor of e209917.SlowZombie with following parameters
     * @param name of e209917.SlowZombie
     * @param position of e209917.SlowZombie
     * CollisionRange, detectionRange are set depending on e209917.SlowZombie by using e209917.Constants
     */
        public SlowZombie(String name, Position position) {
        super(name, position, Constants.slowZombieSpeed); // DO NOT CHANGE PARAMETERS
        collisionRange = Constants.slowZombieCollisionRange;
        detectionRange = Constants.slowZombieDetectionRange;
    }

    /**
     * @return the collision range of the SlowZombieSlowZombie as a double.
     */
    public double getCollisionRange() {
        return SlowZombie.collisionRange;
    }

    /**
     * @return the detection range of the e209917.SlowZombie as a double.
     */
    public double getDetectionRange(){
        return SlowZombie.detectionRange;
    }

    /**
     * This method overrides wanderingState method of e209917.Zombie class and it is used when the zombie state is WANDERING.
     *
     * First the method calls controlState with the following parameters: controller, e209917.ZombieState.FOLLOWING, false.
     * If the closest enemy(e209917.Soldier) is in detection range of the e209917.SlowZombie,zombie state is updated to FOLLOWING ,and
     * controlState method returns true.
     *
     * If the closest enemy(e209917.Soldier) is not in detection range of the e209917.SlowZombie, controlState method returns false,
     * then position of the e209917.SlowZombie will be updated.
     * @param controller which is the current controller is used to find closest enemy(e209917.Soldier) and update position.
     */
    @Override
    public void wanderingState(SimulationController controller)
    {
        if(!controlState(controller, ZombieState.FOLLOWING,false))
        {
            updatePosition(controller);
        }
    }

    /**
     * This method overrides followingState method of e209917.Zombie class and it is used when the zombie state is WANDERING.
     * First the method calls controlState with the following parameters: controller, e209917.ZombieState.FOLLOWING, true.
     *
     * If the closest enemy(e209917.Soldier) is in detection range of the e209917.SlowZombie,zombie state is updated to FOLLOWING ,
     * direction of the e209917.SlowZombie changed to the position of the closest enemy(e209917.Soldier) ,and controlState method
     * returns true ,so firstStep is set true.
     *
     * If the closest enemy(e209917.Soldier) is in detection range of the e209917.SlowZombie, controlState method returns false,
     * and firstStep is set false
     * After that,position of the e209917.SlowZombie will be updated.
     *
     * If firstStep is true state of the e209917.SlowZombie will be changed to e209917.ZombieState.WANDERING.
     * @param controller which is the current controller is used to find closest enemy(e209917.Soldier) and update position.
     */
    @Override
    public void followingState(SimulationController controller)
    {
        boolean firstStep = controlState(controller,ZombieState.FOLLOWING,true);
        updatePosition(controller);
        if(firstStep)
        {
            this.updateZombieState(ZombieState.WANDERING);
        }
    }
}
