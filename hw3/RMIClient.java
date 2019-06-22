import java.util.Scanner;
import java.rmi.* ;


public class RMIClient  {
    public static void listAgents(IMaze maze) throws RemoteException {
        Agent[] agents = maze.getAgents();
        for(int i = 0; i < maze.getAliveAgents(); i++)
        {
            Agent agent = agents[i];
            System.out.print("Agent" + agent.getId() + " at " + agent.getPosition() +
                    ". Gold collected: " + agent.getCollectedGold() + ".\n");
        }
    }

    public static void main(String[] args) throws Exception{

        Scanner scanner = new Scanner(System.in);
        ParsedInput parsedInput = null;
        String input;

        IMazeHub client =
                (IMazeHub) Naming.lookup(
                        "MyServer") ;
        boolean control = true;
        IMaze maze = null;

        while( control ) {
            input = scanner.nextLine();
            try {
                parsedInput = ParsedInput.parse(input);
            }
            catch (Exception ex) {
                parsedInput = null;
            }
            if ( parsedInput == null ) {
                System.out.println("Wrong input format. Try again.");
                continue;
            }
            Object[] parameters = parsedInput.getArgs();
            switch(parsedInput.getType()) {
                case CREATE_MAZE:
                    client.createMaze((Integer) parameters[0],(Integer) parameters[1]);
                    break;
                case DELETE_MAZE:
                    if(client.removeMaze((Integer) parameters[0]))
                        System.out.print("Operation Success.\n");
                    else
                        System.out.print("Operation Failed.\n");
                    break;
                case SELECT_MAZE:
                    maze = client.getMaze((Integer) parameters[0]);
                    if(maze == null)
                    {
                        System.out.print("Operation Failed.\n");
                    }
                    else
                        System.out.print("Operation Success.\n");
                    break;
                case PRINT_MAZE:
                    if (maze != null) {
                        System.out.print(maze.print());
                    }
                    break;
                case CREATE_OBJECT:
                    if (maze != null) {
                        if(maze.createObject(new Position((Integer) parameters[0], (Integer) parameters[1]),(MazeObjectType) parameters[2]))
                            System.out.print("Operation Success.\n");
                        else
                            System.out.print("Operation Failed.\n");

                    }
                    break;
                case DELETE_OBJECT:
                    if (maze != null) {
                        if(maze.deleteObject(new Position((Integer) parameters[0], (Integer) parameters[1])))
                            System.out.print("Operation Success.\n");
                        else
                            System.out.print("Operation Failed.\n");
                    }
                    break;
                case LIST_AGENTS:
                    if (maze != null) {
                        listAgents(maze);
                    }
                    break;
                case MOVE_AGENT:
                    if(maze.moveAgent((Integer) parameters[0],new Position((Integer) parameters[1], (Integer) parameters[2])))
                        System.out.print("Operation Success.\n");
                    else
                        System.out.print("Operation Failed.\n");
                    break;
                case QUIT:
                    control = false;
                    break;
            }

        }
    }
}
