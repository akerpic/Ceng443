import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Simulator
{

    public static void main(String args[])
    {
        SmelterType sTypes[] = SmelterType.values();
        ConstructorType cTypes[] = ConstructorType.values();

        List<Smelter> smelterList = new ArrayList<>();
        List<Constructor> constructorList = new ArrayList<>();
        List<Transporter> transporterList = new ArrayList<>();

        Scanner in = new Scanner(System.in);

        HW2Logger hw2Logger = new HW2Logger();
        hw2Logger.InitWriteOutput();

        int sCount = in.nextInt();
        for(int i=0; i<sCount; i++)
        {
            int sInterval = in.nextInt();
            int sCapacity = in.nextInt();
            int sType = in.nextInt();
            int sTotal = in.nextInt();
            try {
                Class cls = Class.forName(sTypes[sType].toString());
                java.lang.reflect.Constructor constructors[] = cls.getConstructors();
                Object object = constructors[0].newInstance(i+1, sInterval, sCapacity, sType, sTotal, hw2Logger);
                smelterList.add((Smelter) object);
            } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        int cCount = in.nextInt();
        for(int i=0; i<cCount; i++)
        {
            int cInterval = in.nextInt();
            int cCapacity = in.nextInt();
            int cType = in.nextInt();
            try {
                Class cls = Class.forName(cTypes[cType].toString());
                java.lang.reflect.Constructor constructors[] = cls.getConstructors();
                Object object = constructors[0].newInstance(i+1, cInterval, cCapacity, cType, hw2Logger);
                constructorList.add((Constructor) object);
            } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        int tCount = in.nextInt();
        for(int i=0; i<tCount; i++)
        {
            int loadTime = in.nextInt();
            int sId = in.nextInt();
            int cId = in.nextInt();

            Smelter s = null;
            Constructor c = null;

            for(int j=0;j<sCount;j++)
            {
                if(smelterList.get(j).getId()==sId)
                {
                    s = smelterList.get(j);
                    s.transporterCount += 1;
                    break;
                }
            }
            for(int k=0;k<cCount;k++)
            {
                if(constructorList.get(k).getId() == cId)
                {
                    c = constructorList.get(k);
                    break;
                }
            }
            transporterList.add(new Transporter(i+1, loadTime, s, c, hw2Logger));
        }

        ExecutorService taskList = Executors.newFixedThreadPool(sCount+cCount+tCount);
        for (Smelter smelter : smelterList) {
            taskList.execute(smelter);
        }
        for (Transporter transporter : transporterList) {
            taskList.execute(transporter);
        }
        for (Constructor constructor: constructorList) {
            taskList.execute(constructor);
        }

        taskList.shutdown();

    }
} 