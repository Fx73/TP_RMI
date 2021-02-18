import java.rmi.RemoteException;
import java.util.ArrayList;

public class ChatHub implements Hub {

    private final ArrayList<Room> chatlist = new ArrayList<>();


    @Override
    public int GetChatRoomNumber() throws RemoteException {
        return chatlist.size();
    }

    @Override
    public Room GetChatRoom(int id) throws RemoteException {
        return chatlist.get(id);
    }

    @Override
    public Room NewChatRoom() throws RemoteException {
        Room newchat = new ChatRoom("");
        chatlist.add(newchat);
        return newchat;
    }

    @Override
    public void RemoveChatRoom(Room c) throws RemoteException {
        chatlist.remove(c);
    }
}
