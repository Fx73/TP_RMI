import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hub extends Remote {
    int GetChatRoomNumber() throws RemoteException;
    ChatRoom GetChatRoom(int id) throws RemoteException;
    ChatRoom NewChatRoom() throws RemoteException;
    void RemoveChatRoom(Room c) throws RemoteException;

}
