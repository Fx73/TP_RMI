import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Room extends Remote {
    void Say(String name, String s) throws RemoteException;
    String Get_chatlog() throws RemoteException;
    String GetRoomName() throws RemoteException;
    void Register_User(String name) throws RemoteException;
    void Unregister_User(String name) throws RemoteException;
    String[] Get_Users() throws RemoteException;
    }
