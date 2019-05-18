package e209917;

/**
 * e209917.Commando class is derived from e209917.Soldier. It's specific information is fixed at Constant such as collisionRange,
 * shootingRange,bulletSpeed.
 */
public class Commando extends Soldier{

    /**
     * Constructor of e209917.Commando with following parameters
     * @param name of commando
     * @param position of commando
     * CollisionRange, shootingRange and bulletSpeed are set depending on e209917.Commando by using e209917.Constants
     */
    public Commando(String name, Position position) {
        super(name, position, Constants.commandoSpeed); // DO NOT CHANGE PARAMETERS
        collisionRange = Constants.commandoCollisionRange;
        shootingRange = Constants.commandoShootingRange;
        bulletSpeed = Constants.CommandoBulletSpeed;
    }

    /**
     * This method overrides searchingState method of e209917.Soldier class and it is used when the soldier state is SEARCHING.
     * First, the method calls controlState method with parameters controller and SHOOTING in an if block.
     * If controlState returns false, it means that the closest e209917.Zombie is not in the shooting range and current state is
     * still SEARCHING. Then update the position and again call the control state with following parameters: contoller
     * and e209917.SoldierState.SHOOTING
     * @param controller which is the current controller is used to find closest enemy(e209917.Zombie) and update position.
     */
    @Override
    public void searchingState(SimulationController controller)
    {
        if(!controlState(controller, SoldierState.SHOOTING))
        {
            updatePosition(controller);
            controlState(controller, SoldierState.SHOOTING);
        }
    }

    /**
     * There is currently no aiming state for commando.
     * @param controller
     */
    public void aimingState(SimulationController controller)
    {}

    /**
     * This method overrides shootingState method of e209917.Soldier class and it is used when the soldier state is SHOOTING.
     * First, the method calls createBullet method.Then the method calls controlState method with parameters controller
     * and SHOOTING in an if block.
     *
     * If controlState returns false, it means that the closest e209917.Zombie is not in the shooting range and current state is
     * going to be SEARCHING. Then set a random position.
     *
     * If controlState returns true then state is remain at SHOOTING and the direction will change with respect to
     * the closest zombie.
     * @param controller which is the current controller is used to find closest enemy(e209917.Zombie) and update position.
     */
    @Override
    public void shootingState(SimulationController controller)
    {
        createBullet(controller);
        if(!controlState(controller, SoldierState.SHOOTING))
        {
            setRandomDirection();
        }

    }
}
