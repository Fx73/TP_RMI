import java.rmi.RemoteException;
import java.util.ArrayList;

public class ChatHub implements Hub {

    private final ArrayList<ChatRoom> chatlist = new ArrayList<>();


    @Override
    public int GetChatRoomNumber() throws RemoteException {
        return chatlist.size();
    }

    @Override
    public ChatRoom GetChatRoom(int id) throws RemoteException {
        return chatlist.get(id);
    }

    @Override
    public ChatRoom NewChatRoom() throws RemoteException {
        ChatRoom newchat = new ChatRoom("");
        chatlist.add(newchat);
        return newchat;
    }

    @Override
    public void RemoveChatRoom(Room c) throws RemoteException {
        chatlist.remove(c);
    }
}
