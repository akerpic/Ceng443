import java.rmi.RemoteException;
import java.util.*;
public class MainTest {
    public static void main(String[] args)  {

        IMaze maze = null;
        try {
            maze = new IMazeImpl(3,3);
            maze.createObject(new Position(0,0),MazeObjectType.AGENT);
            maze.createObject(new Position(0,1),MazeObjectType.HOLE);
            maze.createObject(new Position(1,0),MazeObjectType.GOLD);


            ((IMazeImpl) maze).listAgents();
            boolean b = maze.moveAgent(0,new Position(0,1));

            if(b)
            {
                System.out.println(maze.print());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
} 