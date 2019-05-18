package e209917;

/**
 * e209917.RegularZombie class is derived from e209917.Zombie. It's specific information is fixed at Constant such as collisionRange,
 * detectionRange, speed.
 * It encapsulates the following information:
 * <ul>
 * <li> Following count which is used to count consequent FOLLOWING states
 * </ul>
 */
public class RegularZombie extends Zombie {

    private int followingCount;

    /**
     * Constructor of e209917.RegularZombie with following parameters
     * @param name of e209917.RegularZombie
     * @param position of e209917.RegularZombie
     * CollisionRange, detectionRange are set depending on e209917.RegularZombie by using e209917.Constants
     * following count is set 0 by default.
     */
    RegularZombie(String name, Position position) {
        super(name, position, Constants.regularZombieSpeed); // DO NOT CHANGE PARAMETERS
        collisionRange = Constants.regularZombieCollisionRange;
        detectionRange = Constants.regularZombieDetectionRange;
        this.followingCount = 0;
    }

    /**
     * @return the followingCount of the e209917.RegularZombie as a integer.
     */
    public int getFollowingCount() {
        return followingCount;
    }

    /**
     * @param followingCount is used to determine consequent following state count
     */
    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    /**
     * @return the collision range of the e209917.RegularZombie as a double.
     */
    public double getCollisionRange() {
        return RegularZombie.collisionRange;
    }

    /**
     * @return the detection range of the e209917.RegularZombie as a double.
     */
    public double getDetectionRange(){
        return RegularZombie.detectionRange;
    }


    /**
     * This method overrides wanderingState method of e209917.Zombie class and it is used when the zombie state is WANDERING.
     *
     * First the method updates the position of the e209917.RegularZombie.
     *
     * After that, the method calls controlState with the following parameters: controller, e209917.ZombieState.FOLLOWING, false.
     * If the closest enemy(e209917.Soldier) is in detection range of the e209917.RegularZombie,zombie state is updated to FOLLOWING.
     *
     * If the closest enemy(e209917.Soldier) is in detection range of the e209917.RegularZombie
     *
     * Finally, following count set to 0.
     * @param controller which is the current controller is used to find closest enemy(e209917.Soldier) and update position.
     */
    @Override
    public void wanderingState(SimulationController controller)
    {
        updatePosition(controller);
        controlState(controller,ZombieState.FOLLOWING,false);
        setFollowingCount(0);
    }

    /**
     * This method overrides followingState method of e209917.Zombie class and it is used when the zombie state is WANDERING.
     *
     * First the method updates the position of the e209917.RegularZombie.
     * After that, followingCount is increased by 1.
     * Then, if the followingCount is equal to 4 set e209917.RegularZombie to WANDERING
     * @param controller which is the current controller is used to find closest enemy(e209917.Soldier) and update position.
     */
    @Override
    public void followingState(SimulationController controller)
    {
        updatePosition(controller);
        this.setFollowingCount(this.getFollowingCount() + 1);
        if(getFollowingCount()==4)
        {
            this.updateZombieState(ZombieState.WANDERING);
        }
    }

}
