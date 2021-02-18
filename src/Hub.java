import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hub extends Remote {
    int GetChatRoomNumber() throws RemoteException;
    Room GetChatRoom(int id) throws RemoteException;
    Room NewChatRoom() throws RemoteException;
    void RemoveChatRoom(Room c) throws RemoteException;

}
