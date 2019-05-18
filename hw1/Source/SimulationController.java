package e209917;

import java.util.ArrayList;

/**
 * e209917.SimulationController Object is the class for all objects that are in the simulation.
 * It encapsulates information about simulation.This information includes:
 * <ul>
 * <li> Height of the grid
 * <li> Width of the grid
 * <li> e209917.Bullet Count represents available bullet number
 * <li> bulletArrayList that is used to store bullets which will be added to simulation next step
 * <li> allObjects that is used to store simulation objects
 *</ul>
 */
public class SimulationController {
    private final double height;
    private final double width;
    private long bulletCount;
    private ArrayList<SimulationObject> bulletArrayList = new ArrayList<>();

    private ArrayList<SimulationObject> allObjects = new ArrayList<>();

    /**
     *Constructor of e209917.SimulationController
     * <p>
     * It sets the bulletCount to 0 by default
     * @param  width of the grid
     * @param  height of the grid
     */
    public SimulationController(double width, double height) {
        this.width = width;
        this.height = height;
        this.bulletCount=0;
    }

    /**
     * @return height of the grid as a double.
     */
    public double getHeight() {
        return height;
    }

    /**
     * @return width of the grid as a double.
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return bulletCount that represents available bullet number
     */
    public long getBulletCount() {
        return bulletCount;
    }

    /**
     *
     * @param bulletCount bulletCount that represents available bullet number
     */
    public void setBulletCount(long bulletCount) {
        this.bulletCount = bulletCount;
    }



    /**
     *This function iterates over allObjects and call step function if objects will be removed
     * Then calls addBulletsToSimulation() method in order to add bullets to simulation.
     * After that, it calls removeNotActives which is used to remove SimulationObjects that are not active from
     * allObject ArrayList.
     */
    //Make sure to fill these methods for grading.
    public void stepAll() {

        for (SimulationObject obj: allObjects) {
            if(obj.isActive())
            {
                obj.step(this);
            }
        }
        addBulletsToSimulation();

        removeNotActives();
    }

    /** This method iterates over bullet array if there exist newly created bullets, add these bullets to allObject
     * ArrayList.
     * While doing these also remove these bullets from bulletArrayList.
     */
    private void addBulletsToSimulation()
    {
        int size = bulletArrayList.size();
        for(int i=0;i<size && size >0;i++)
        {
            if(bulletArrayList.get(i).isActive())
            {
                allObjects.add(bulletArrayList.get(i));
                bulletArrayList.remove(i);
                i--;
                size--;
            }
        }
    }

    /**
     * This method is used to add SimulationObjects to simulation.
     * @param obj
     */
    public void addSimulationObject(SimulationObject obj) {
            allObjects.add(obj);
    }

    /**
     * This method is used to remove SimulationObjects to simulation.
     * @param obj that will be removed
     */
    public void removeSimulationObject(SimulationObject obj) {
        obj.setActive(false);
    }

    /**
     * This method is used to check whether the simulation is over or not.
     * If there exists zombie and soldier objects and they are active then simulation is not over.
     * else, simulation is over.
     * returns true if it is over, else returns false
     */
    public boolean isFinished() {
        boolean zActive = false;
        boolean sActive = false;

        for (SimulationObject obj: allObjects) {
            if(obj.isActive())
            {
                if(obj.getClass().getSuperclass().getName().equals("e209917.Soldier"))
                    sActive = true;
                else if(obj.getClass().getSuperclass().getName().equals("e209917.Zombie"))
                    zActive = true;

                if(zActive && sActive)
                    return false;
            }
        }
        return true;
    }

    /**
     * @return allObjects that store simulation objects
     */
    public ArrayList<SimulationObject> getAllObjects() {
        return allObjects;
    }

    /**
     *
     * @param allObjects that is set as allObjects
     */
    public void setAllObjects(ArrayList<SimulationObject> allObjects) {
        this.allObjects = allObjects;
    }

    /**
     * this method is used to remove SimulationObjects that are not active allObject ArrayList.
     */
    private void removeNotActives()
    {
        allObjects.removeIf(obj -> !obj.isActive());
    }

    /**
     * @return bulletArrayList that store bullet objects that will be added to simulation on the next step
     */
    public ArrayList<SimulationObject> getBulletArrayList() {
        return bulletArrayList;
    }

    /**
     *
     * @param bulletArrayList that is set as bulletArrayList
     */
    public void setBulletArrayList(ArrayList<SimulationObject> bulletArrayList) {
        this.bulletArrayList = bulletArrayList;
    }

}
