import java.rmi.* ;
import java.rmi.server.* ;
import java.util.ArrayList;

public class IMazeImpl extends UnicastRemoteObject implements IMaze{
    private int height;
    private int width;
    private int nextAgentId = 0;
    private int aliveAgents = 0;
    ArrayList<MazeObject> maze;

    public IMazeImpl(int width, int height) throws RemoteException {
        this.width = width;
        this.height = height;
        create(height,width);
    }

    @Override
    public void create(int height, int width) throws RemoteException {
        this.maze = new ArrayList<>();
    }

    @Override
    public MazeObject getObject(Position position) throws RemoteException {
        for (MazeObject obj : maze) {
            if (obj.getPosition().distance(position) == 0) {
                return obj;
            }
        }
        return null;
    }

    @Override
    public boolean createObject(Position position, MazeObjectType type) throws RemoteException {
        if(getObject(position) == null)
        {
            if(!isInside(position))
                return false;

            if(type == MazeObjectType.AGENT)
            {
                maze.add(new Agent(position,nextAgentId));
                nextAgentId += 1;
                aliveAgents += 1;
            }
            else
            {
                maze.add(new MazeObject(position,type));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteObject(Position position) throws RemoteException {
        MazeObject obj = getObject(position);
        if(obj != null)
        {
            if(obj.getType() == MazeObjectType.AGENT)
                aliveAgents -= 1;
            return maze.remove(obj);
        }
        return false;
    }

    @Override
    public Agent[] getAgents() throws RemoteException {
        Agent[] agents = new Agent[aliveAgents+1];
        int index = 0;
        for (MazeObject obj : maze) {
            if (obj.getType() == MazeObjectType.AGENT) {
                agents[index] = (Agent) obj;
                index += 1;
            }
        }
        return agents;
    }

    private boolean isInside(Position position)
    {
        int y = position.getY();
        int x = position.getX();
        return y >= 0 && y < height && x >= 0 && x < width;
    }

    private Agent getAgent(int id) throws RemoteException {
        Agent[] agents = getAgents();
        for(int i=0; i < aliveAgents; i++)
        {
            if(agents[i].getId() == id)
                return agents[i];
        }
        return null;
    }

    @Override
    public boolean moveAgent(int id, Position position) throws RemoteException {

        if (isInside(position))
        {
            Agent agent = getAgent(id);
            if(agent != null)
            {
                int mDistance = agent.getPosition().distance(position);
                if(mDistance == 1)
                {
                    MazeObject obj = getObject(position);
                    if(obj == null)
                    {
                        agent.setPosition(position);
                    }
                    else
                    {
                        MazeObjectType type = obj.getType();
                        if(type == MazeObjectType.HOLE)
                        {
                            deleteObject(agent.getPosition());
                        }
                        else if(type == MazeObjectType.WALL || type == MazeObjectType.AGENT)
                        {
                            return false;
                        }
                        else if(type == MazeObjectType.GOLD)
                        {
                            deleteObject(position);
                            agent.setPosition(position);
                            agent.setCollectedGold(agent.getCollectedGold()+1);

                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getAliveAgents() {
        return aliveAgents;
    }

    @Override
    public String print() throws RemoteException {
        StringBuilder str = new StringBuilder();

        str.append("+");
        for(int i = 0; i < width; i++)
        {
            str.append("-");
        }
        str.append("+\n");

        for(int i = 0; i < height; i++)
        {
            str.append("|");

            for (int j = 0; j < width; j++)
            {
                MazeObject obj = getObject(new Position(j,i));
                if(obj == null)
                {
                    str.append(" ");
                }
                else
                {
                    switch(obj.getType()) {
                        case WALL:
                            str.append("X");
                            break;
                        case HOLE:
                            str.append("O");
                            break;
                        case AGENT:
                            str.append("A");
                            break;
                        case GOLD:
                            str.append("G");
                            break;
                    }
                }
            }
            str.append("|\n");
        }
        str.append("+");
        for(int i=0;i<width; i++)
        {
            str.append("-");
        }
        str.append("+\n");

        return str.toString();
    }


}
