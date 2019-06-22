import java.rmi.* ;
public class RMIServer {
    public static void main(String[] args) throws Exception{
        IMazeHub server = new IMazeHubImpl() ;
        Naming.rebind("MyServer", server) ;
    }
}
