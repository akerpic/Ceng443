package e209917;

/**
 * e209917.Soldier class is derived from e209917.SimulationObject. It holds the following information's
 * <ul>
 * <li> Current state of soldier object
 * <li> Shooting range of soldier object
 * <li> Collision range of zombie object
 * <li> BulletSpeed of soldier object
 *</ul>
 */
public abstract class Soldier extends SimulationObject{
    private SoldierState soldierState;
    static double shootingRange;
    static double collisionRange;
    static double bulletSpeed;

    /**
     * Constructor of e209917.Soldier with following parameters
     * @param name of the soldier
     * @param position of the soldier
     * @param speed of the soldier
     * e209917.SoldierState is set SEARCHING by default
     */
    public Soldier(String name, Position position, double speed) {
        super(name, position, speed);
        setSoldierState(SoldierState.SEARCHING);
    }

    /**
     * This method overrides step method of Simulation Object class and it simulates of soldier objects in the given
     * controller.
     * If the step of the object is first step and its direction probably will be null.
     * For this reason, the method first checks the direction and if it is null then assign a random direction.
     * After that, the method calls one of the following methods depending on the current state of the object:
     * searchingState, aimingState, shootingState.
     * @param controller which is the current controller is used to find closest enemy(e209917.Zombie) and update position.
     */
    @Override
    public void step(SimulationController controller)
    {
        if(this.getDirection() == null)
            setRandomDirection();
        if(soldierState == SoldierState.SEARCHING)
            searchingState(controller);

        else if(soldierState == SoldierState.AIMING)
            aimingState(controller);

        else
            shootingState(controller);
    }

    /**
     * This method is an abstract method that is overridden by e209917.Sniper, e209917.RegularSoldier and e209917.Commando classes.
     * It is used if current state of the object is SEARCHING.
     * @param controller which is the current controller is used to find closest enemy(e209917.Zombie) and update position.
     */
    public abstract void searchingState(SimulationController controller);

    /**
     * This method is an abstract method that is overridden by e209917.Sniper, e209917.RegularSoldier and e209917.Commando classes.
     * It is used if current state of the object is AIMING.
     * @param controller which is the current controller is used to find closest enemy(e209917.Zombie) and update position.
     */
    public abstract void aimingState(SimulationController controller);

    /**
     * This method is an abstract method that is overridden by e209917.Sniper, e209917.RegularSoldier and e209917.Commando classes.
     * It is used if current state of the object is SHOOTING.
     * @param controller which is the current controller is used to find closest enemy(e209917.Zombie) and update position.
     */
    public abstract void shootingState(SimulationController controller);

    /**
     * @return soldierState that is current state of soldier.
     */
    public SoldierState getSoldierState() {
        return soldierState;
    }


    /**
     * This method is used to update and print new state of soldier.
     * If current soldier state is equal to given parameter then it does not update.
     * @param soldierState will be set to the state of soldier.
     */
        public void updateSoldierState(SoldierState soldierState)
    {
        if(this.getSoldierState() != soldierState)
        {
            setSoldierState(soldierState);
            PrintObject.stateChange(this);
        }
    }

    /**
     * This method is used to set and update current state of soldier which is hold by e209917.SimulationObject.
     * @param soldierState will be set to the state of soldier.
     */
    public void setSoldierState(SoldierState soldierState) {
        this.soldierState = soldierState;
        this.setCurrentState(soldierState.toString());
    }


    /**
     * This method is used to obtain of shooting range of the soldier depending on soldier type.
     * @return shootingRange as a double
     */
    public static double getShootingRange() {
        return shootingRange;
    }


    /**
     * This method is used to obtain of collision range of the soldier depending on soldier type.
     * @return collisionRange as a double
     */
    public static double getCollisionRange() {
        return collisionRange;
    }


    /**
     * This method is used to obtain of bullet speed of the soldier depending on soldier type.
     * @return bulletSpeed as a double
     */
    public static double getBulletSpeed() {
        return bulletSpeed;
    }


    @Override
    public String toString() {
        return "e209917.Soldier{" +
                "soldierState=" + soldierState + " soldierPosition " + this.getPosition().toString() +
                '}';
    }

    /**
     * This method is an used to manage state of the soldier. This method is used by e209917.Sniper, e209917.RegularSoldier and
     * e209917.Commando classes.
     * Firstly, the method find closest e209917.Zombie with using reflection. If the e209917.Zombie is in shooting range
     * then, soldier state is updated to nextState which is given as a parameter. If next state is SHOOTING, then
     * direction is changed with respect to enemy position.
     * If the closest e209917.Zombie is not in shooting range the soldier state remains or updated to SEARCHING.
     * @param controller which is the current controller that is used to find closest enemy(e209917.Zombie).
     * @param nextState which will set to state of soldier if closest enemy is in shooting range of the soldier.
     * @return boolean if closest e209917.Zombie is in shooting range returns true else returns false.
     */
    boolean controlState(SimulationController controller, SoldierState nextState)
    {
        SimulationObject closest = findClosest(controller.getAllObjects(),"e209917.Zombie");

        if(getMinDistance() <= this.getShootingRange())
        {
            if(this.getSoldierState() != nextState)
                this.updateSoldierState(nextState);
            if(nextState==SoldierState.SHOOTING)
            {
                changeDirectionToClosest(closest.getPosition());
            }
            return true;
        }
        else {
            if(this.getSoldierState() != SoldierState.SEARCHING)
                this.updateSoldierState(SoldierState.SEARCHING);
            return false;
        }

    }

    /**
     * This method is an used to create a bullet and added to bullet list of controller.
     * This method is used by e209917.Sniper, e209917.RegularSoldier and e209917.Commando classes.
     * The bullet speed is arranged with respect to soldier type.
     * The direction and position of the bullet are the same as the soldier.
     * The bullet count is increased by 1.
     * Prints the soldier fired the bullet.
     * @param controller the current controller that is used for obtaining bullet array list
     */
    void createBullet(SimulationController controller)
    {
        String bulletName = "bullet" + controller.getBulletCount();
        Bullet bullet = new Bullet(bulletName,this.getPosition(), this.getBulletSpeed(), this.getDirection());
        controller.getBulletArrayList().add(bullet);
        controller.setBulletCount(controller.getBulletCount() + 1);
        PrintObject.SoldierFiredBullet(this,bullet);
    }


}
