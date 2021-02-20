import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Room extends Remote {
    void Say(String name, String s) throws RemoteException;
    String Get_chatlog() throws RemoteException;
    public String GetRoomName() throws RemoteException;

    }
