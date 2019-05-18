package e209917;

class PrintObject {

    static void stateChange(SimulationObject simulationObject)
    {

        System.out.print(simulationObject.getName() + " changed state to " + simulationObject.getCurrentState() + ".\n");
    }

    static void positionChange(SimulationObject simulationObject)
    {
        System.out.print(simulationObject.getName() + " moved to " + simulationObject.getPosition() + ".\n");
    }

    static void directionChange(SimulationObject simulationObject)
    {
        System.out.print(simulationObject.getName() + " changed direction to " + simulationObject.getDirection() + ".\n");
    }

    static void SoldierFiredBullet(SimulationObject soldier, SimulationObject bullet)
    {
        System.out.print(soldier.getName() + " fired " + bullet.getName() + " to direction " + bullet.getDirection() + ".\n");
    }

    static void zombieKilledSoldier(SimulationObject zombie, SimulationObject soldier)
    {
        System.out.print(zombie.getName() + " killed " + soldier.getName() + ".\n");
    }

    static void bulletHitZombie(SimulationObject bullet, SimulationObject zombie)
    {
        System.out.print(bullet.getName() + " hit " + zombie.getName() + ".\n");
    }

    static void bulletMovedOut(SimulationObject bullet)
    {
        System.out.print(bullet.getName() + " moved out of bounds.\n");
    }

    static void bulletDrop(SimulationObject bullet)
    {
        System.out.print(bullet.getName() + " dropped to the ground at " + bullet.getPosition() + ".\n");
    }

}
