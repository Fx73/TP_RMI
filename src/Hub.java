import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hub extends Remote {
    int GetChatRoomNumber() throws RemoteException;
    String[] GetChatRoomNameList() throws RemoteException;
    String GetChatRoomURI(String name) throws RemoteException, NotBoundException;
    String NewChatRoom(String name) throws RemoteException, AlreadyBoundException, NotBoundException;
    void RemoveChatRoom(String name) throws RemoteException, NotBoundException;

}
