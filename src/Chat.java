import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Chat extends Remote {
    void Say(String name, String s) throws RemoteException;
    String Get_chatlog() throws RemoteException;


    }
