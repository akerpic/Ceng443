package e209917;

import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 *
 */
public class SimulationRunner {

    public static void main(String[] args) throws NoSuchMethodException {
        SimulationController simulation = new SimulationController(50, 50);

        Soldier soldier1 = new Commando("Soldier1", new Position(20, 20));

        simulation.addSimulationObject(soldier1);


        Zombie regular1 = new SlowZombie("Zombie1", new Position(30, 30));

        simulation.addSimulationObject(regular1);


        while (!simulation.isFinished()) {
            simulation.stepAll();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SimulationRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
