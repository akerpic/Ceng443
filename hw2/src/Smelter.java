import java.util.concurrent.Semaphore;

/**
 * pCount refers to number of produced ingot
 * tCount refers to number of transported ingot
 * */
public abstract class Smelter extends Agent {
    private int id;
    private int ingotType;
    private int interval;
    private int pCount;
    private int tCount;
    public int transporterCount = 0;

    private boolean isActive = true;
    private final int totalIngot;
    public final Semaphore sEmpty;
    public final Semaphore sFull;

    Smelter(int id, int interval, int capacity, int ingotType, int totalIngot, HW2Logger hw2Logger) {
        super(hw2Logger);
        this.id = id;
        this.ingotType = ingotType;
        this.interval = interval;
        this.totalIngot = totalIngot;
        this.sFull = new Semaphore(0);
        this.sEmpty = new Semaphore(capacity);
        this.pCount = 0;
        this.tCount = 0;
    }

    synchronized boolean controlTCount()
    {
        if(this.tCount < this.totalIngot)
        {
            tCount += 1;
            return true;
        }
        return false;
    }

    public int getId() {
        return id;
    }

    private void WaitCanProduce(){
        try {
            sEmpty.acquire();
            pCount++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void IngotProduced(){
        sFull.release();
    }


    private void isStop()
    {
        if(totalIngot == pCount)
            isActive = false;
    }


    /**
     * It releases sFull as much as the number of transporter that are related to corresponding smelter
     * */
    private void SmelterStopped()
    {
        sFull.release(transporterCount);
        HW2Logger.WriteOutput(id, 0, 0, Action.SMELTER_STOPPED);
    }


    @Override
    public void run()
    {
        HW2Logger.WriteOutput(id, 0, 0, Action.SMELTER_CREATED);

        while (isActive)
        {
            WaitCanProduce();
            HW2Logger.WriteOutput(id, 0, 0, Action.SMELTER_STARTED);
            sleepIt(interval);
            IngotProduced();
            HW2Logger.WriteOutput(id, 0, 0, Action.SMELTER_FINISHED);
            sleepIt(interval);
            isStop();
        }
        SmelterStopped();
    }
}
