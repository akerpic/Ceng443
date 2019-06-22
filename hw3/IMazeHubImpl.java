import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class IMazeHubImpl extends UnicastRemoteObject implements IMazeHub{
    private List<IMaze> IMazeList;
    public IMazeHubImpl() throws RemoteException {
        IMazeList = new ArrayList<>();
    }

    @Override
    public void createMaze(int width, int height) throws RemoteException {
        IMazeImpl maze = new IMazeImpl(width, height);
        IMazeList.add(maze);
    }

    @Override
    public IMaze getMaze(int index) {
        if(index<0 || index >= IMazeList.size())
            return null;
        return IMazeList.get(index);
    }

    @Override
    public boolean removeMaze(int index) {
        if(IMazeList.get(index) != null)
        {
            IMazeList.remove(index);
            return true;
        }
        return false;
    }
}


