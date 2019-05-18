public class Transporter extends Agent{
    private int id;
    private int loadTime;
    private Smelter smelter;
    private Constructor constructor;

    Transporter(int id, int loadTime, Smelter smelter, Constructor constructor, HW2Logger hw2Logger) {
        super(hw2Logger);
        this.id = id;
        this.loadTime = loadTime;
        this.smelter = smelter;
        this.constructor = constructor;
    }

    @Override
    public void run()
    {
        HW2Logger.WriteOutput(0, id, 0, Action.TRANSPORTER_CREATED);
        while (constructor.getIsActive())
        {
            try {
                smelter.sFull.acquire();
                if(smelter.controlTCount())
                {
                    HW2Logger.WriteOutput(smelter.getId(), id, 0, Action.TRANSPORTER_TRAVEL);
                    sleepIt(loadTime);
                    HW2Logger.WriteOutput(smelter.getId(), id, 0, Action.TRANSPORTER_TAKE_INGOT);
                    sleepIt(loadTime);
                    smelter.sEmpty.release();
                    constructor.sEmpty.acquire();
                    HW2Logger.WriteOutput(smelter.getId(), id, 0, Action.TRANSPORTER_TRAVEL);
                    sleepIt(loadTime);
                    HW2Logger.WriteOutput(0, id, constructor.getId(), Action.TRANSPORTER_DROP_INGOT);
                    sleepIt(loadTime);
                    //release operation
                    if(constructor.incrementAndCheckIngotCount())
                    {
                        constructor.sFull.release();
                    }
                }
                else {
                    // if controlTCount returns false it means
                    // number of ingots transported from smelter to constructor
                    // reach to the total ingot number.
                    break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        HW2Logger.WriteOutput(0, id, 0, Action.TRANSPORTER_STOPPED);
    }
}

