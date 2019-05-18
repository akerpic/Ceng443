import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public abstract class Constructor extends Agent{
    private int id;
    private int ingotType;
    private int interval;
    public final Semaphore sFull;
    public final Semaphore sEmpty;
    private int ingotCount;
    protected int requiredIngot;
    private boolean isActive;
    private final int timeout = 3;

    int getId() {
        return id;
    }

    Constructor(int id, int interval, int capacity, int ingotType, HW2Logger hw2Logger) {
        super(hw2Logger);
        this.id = id;
        this.ingotType = ingotType;
        this.interval = interval;
        this.sFull = new Semaphore(0);
        this.sEmpty = new Semaphore(capacity);
        this.ingotCount = 0;
        this.isActive = true;

    }

    public synchronized boolean getIsActive()
    {
        return isActive;
    }



    public synchronized int getIngotCount()
    {
        return ingotCount;
    }


    /**increment and check whether it is divisible by controlNumber(that is 2 or 3)
     * if it is divisible then return true else false
     * */
    public synchronized boolean incrementAndCheckIngotCount(){
        ingotCount += 1;
        return ingotCount % requiredIngot == 0 && ingotCount != 0;
    }

    @Override
    public void run()
    {
        HW2Logger.WriteOutput(0, 0, id, Action.CONSTRUCTOR_CREATED);

        while(true)
        {
            try {
                if(sFull.tryAcquire(timeout, TimeUnit.SECONDS))
                {
                    HW2Logger.WriteOutput(0, 0, id, Action.CONSTRUCTOR_STARTED);
                    sleepIt(interval);
                    sEmpty.release(requiredIngot);
                    HW2Logger.WriteOutput(0, 0, id, Action.CONSTRUCTOR_FINISHED);
                }
                else
                {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        HW2Logger.WriteOutput(0, 0, id, Action.CONSTRUCTOR_STOPPED);
    }
}
