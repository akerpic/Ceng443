package e209917;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Bullet extends SimulationObject{

    /**
     * Constructor of e209917.Bullet with following parameters
     * @param name
     * @param position
     * @param speed
     */
    public Bullet(String name, Position position, double speed) {
        super(name, position, speed);
    }


    /**
     * Constructor of e209917.Bullet with following parameters
     * @param name
     * @param position
     * @param speed
     * @param direction
     */
    public Bullet(String name, Position position, double speed, Position direction) {
        super(name, position, speed);
        this.setDirection(direction);
    }

    /**
     * This method overrides step method of Simulation Object class and it simulates of bullet objects in the given
     * controller.
     * First, it normalizes the direction of the object.Then iterates over every position(inside of the conroller grid)
     * of the object. If the bullet hit a zombie, the zombie is removed from controller. If the bullet is not hit a
     * zombie, it will drop or it will go outside.
     * @param controller
     */
    @Override
    public void step(SimulationController controller) {
        getDirection().normalize();
        for(int i=0;i<getSpeed();i++)
        {
            this.updatePosition(controller);
            if(!isInside(this.getPosition(),controller))
            {
                this.setActive(false);
                PrintObject.bulletMovedOut(this);
                return;
            }
            SimulationObject closest = findClosest(controller.getAllObjects(),"e209917.Zombie");

            try {
                if(isCollide(controller,closest))
                {
                    PrintObject.bulletHitZombie(this,closest);
                    controller.removeSimulationObject(closest);
                    this.setActive(false);
                    return;
                }

            } catch (NoSuchMethodException |
                    InvocationTargetException |
                    IllegalAccessException |
                    NullPointerException e)
            {
                e.printStackTrace();
            }
        }
        PrintObject.bulletDrop(this);
    }

    /**
     * This method overrides updatePosition method of e209917.SimulationObject class and it updates the position of the bullet.
     * @param controller
     */
    @Override
    public void updatePosition(SimulationController controller)
    {
        double x = getPosition().getX()+getDirection().getX();
        double y = getPosition().getY()+getDirection().getY();
        Position position = new Position(x,y);
        setPosition(position);
    }

    /**
     * This method is used to check whether there exists a collision between a bullet and zombie or not.
     * If there is not a collision, then the bullet position will be updated.
     * While obtaining collision range of the zombie, reflection is used.
     * @param controller
     * @param obj
     * return boolean if there is a collision returns true ,else false.
     */
    boolean isCollide(SimulationController controller, SimulationObject obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method getCollisionRange = obj.getClass().getSuperclass().getDeclaredMethod("getCollisionRange");
        getCollisionRange.setAccessible(true);
        double collisionRange = ((Number)getCollisionRange.invoke(obj)).doubleValue();

        if(this.getMinDistance() > collisionRange)
        {
            updatePosition(controller);
            return false;
        }
        return true;
    }


}
