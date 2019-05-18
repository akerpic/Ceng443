package e209917;

/**
 * e209917.RegularSoldier class is derived from e209917.Soldier. It's specific information is fixed at Constant such as collisionRange,
 * shootingRange,bulletSpeed.
 */
public class RegularSoldier extends Soldier{

    /**
     * Constructor of e209917.RegularSoldier with following parameters
     * @param name of the e209917.RegularSoldier
     * @param position of the e209917.RegularSoldier
     * CollisionRange, shootingRange and bulletSpeed are set depending on e209917.RegularSoldier by using e209917.Constants
     */
    RegularSoldier(String name, Position position) {
        super(name,position,Constants.regularSoldierSpeed); // DO NOT CHANGE PARAMETERS
        collisionRange = Constants.regularSoldierCollisionRange;
        shootingRange = Constants.regularSoldierShootingRange;
        bulletSpeed = Constants.regularSoldierBulletSpeed;
    }

    /**
     * This method overrides searchingState method of e209917.Soldier class and it is used when the soldier state is SEARCHING.
     * First, the method calls updatePosition method with a parameter controller. If the new position is out of bounds,
     * then updatePosition method changes direction to random value if the new position is not out of bounds, then
     * updatePosition method change direction to a random value.
     *
     * Then, the method  calls the controlState method with parameters controller and AIMING. If closest zombie is in
     * shooting range of the regular e209917.Soldier then the state of the soldier changes to AIMING and the direction will
     * changes to position of the closest e209917.Zombie.
     * If the it is not in the shooting range then the state of the soldier remains at SEARCHING.
     * @param controller which is the current controller is used to find closest enemy(e209917.Zombie) and update position.
     */
    @Override
    public void searchingState(SimulationController controller)
    {
        updatePosition(controller);
        controlState(controller, SoldierState.AIMING);
    }

    /**
     * The method  calls the controlState method with parameters controller and SHOOTING. If closest zombie is in
     * shooting range of the regular e209917.Soldier then the state of the soldier changes to SHOOTING and the direction will
     * changes to position of the closest e209917.Zombie.
     * If the it is not in the shooting range then the state of the soldier remains at SEARCHING.
     * @param controller which is the current controller is used to find closest enemy(e209917.Zombie) and update position.
     */
    @Override
    public void aimingState(SimulationController controller)
    {
        controlState(controller, SoldierState.SHOOTING);
    }

    /**
     * This method overrides shootingState method of e209917.Soldier class and it is used when the soldier state is SHOOTING.
     * First, the method calls createBullet method with a parameter controller.Then the method calls controlState
     * method with parameters controller and AIMING in an if block.
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
