import java.util.Random;
import java.util.stream.DoubleStream;

public abstract class Agent implements Runnable {
    public HW2Logger hw2Logger;

    public Agent(HW2Logger hw2Logger) {
        this.hw2Logger = hw2Logger;
    }

    public void sleepIt(int interval)
    {
        try {
            Random random = new Random(System.currentTimeMillis());
            DoubleStream stream;
            stream = random.doubles(1, interval-interval*0.01, interval+interval*0.02);
            Thread.sleep((long) stream.findFirst().getAsDouble());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
