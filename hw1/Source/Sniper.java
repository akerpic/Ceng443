package e209917;

/**
 * e209917.Sniper class is derived from e209917.Soldier. It's specific information is fixed at Constant such as collisionRange,
 * shootingRange,bulletSpeed.
 */
public class Sniper extends Soldier{

    /**
     * Constructor of e209917.Sniper with following parameters
     * @param name of e209917.Sniper
     * @param position of e209917.Sniper
     * CollisionRange, shootingRange and bulletSpeed are set depending on e209917.Sniper by using e209917.Constants
     */
    public Sniper(String name, Position position) {
        super(name, position, Constants.sniperSpeed); // DO NOT CHANGE PARAMETERS
        collisionRange = Constants.sniperCollisionRange;
        shootingRange = Constants.sniperShootingRange;
        bulletSpeed = Constants.SniperBulletSpeed;
    }

    /**
     * This method overrides searchingState method of e209917.Soldier class and it is used when the soldier state is SEARCHING.
     * First, the method calls updatePosition method with a parameter controller. If the new position is out of bounds,
     * then updatePosition method changes direction to random value if the new position is not out of bounds, then
     * updatePosition method change direction to a random value.
     * Then, the method calls updateSoldierState with a  parameter e209917.SoldierState.AIMING.
     * updateSoldierState method will changed the state as AIMING and print it.
     * @param controller which is the current controller is used to find closest enemy(e209917.Zombie) and update position.
     */
    @Override
    public void searchingState(SimulationController controller)
    {
        updatePosition(controller);
        this.updateSoldierState(SoldierState.AIMING);
    }

    /**
     * This method overrides searchingState method of e209917.Soldier class and it is used when the soldier state is AIMING.
     * First, the method calls controlState method with a parameter controller and e209917.SoldierState.SHOOTING.
     * If closest zombie is in shooting range of the sniper, controlState method changes the direction of sniper to
     * position of the closest zombie and state of the object to SHOOTING
     * It closest zombie is not in shooting range of the sniper, then state of the sniper is changed to SEARCHING.
     * @param controller which is the current controller is used to find closest enemy(e209917.Zombie) and update position.
     */
    @Override
    public void aimingState(SimulationController controller)
    {
        controlState(controller, SoldierState.SHOOTING);
    }

    /**
     * This method overrides shootingState method of e209917.Soldier class and it is used when the soldier state is SHOOTING.
     * First, the method calls createBullet method with a parameter controller.Then the method calls controlState method
     * with parameters controller and AIMING in an if block.
     * If closest zombie is in shooting range of the sniper, controlState method changes the direction of sniper to
     * position of the closest zombie and state of the object to AIMING
     * It closest zombie is not in shooting range of the sniper, then state of the sniper is changed to SEARCHING.
     * @param controller which is the current controller is used to find closest enemy(e209917.Zombie) and update position.
     */
    @Override
    public void shootingState(SimulationController controller)
    {
        createBullet(controller);
        if(!controlState(controller, SoldierState.AIMING))
        {
            setRandomDirection();
        }
    }

}
