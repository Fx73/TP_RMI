import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ChatHub implements Hub {

    private final ArrayList<String> namelist = new ArrayList<>();
    private final ArrayList<ChatRoom> chatlist = new ArrayList<>();


    @Override
    public int GetChatRoomNumber() throws RemoteException {
        return chatlist.size();
    }

    @Override
    public String[] GetChatRoomNameList() throws RemoteException {
        return namelist.toArray(new String[0]);
    }

    @Override
    public String GetChatRoomURL(String name) throws RemoteException {
        return "Room_"+name+"_Service";
    }

    @Override
    public String NewChatRoom(String name) throws RemoteException, AlreadyBoundException {
        ChatRoom newchat = new ChatRoom(name, "");
        chatlist.add(newchat);
        namelist.add(name);

        Room room_stub = (Room) UnicastRemoteObject.exportObject(newchat, 0);
        Registry registry= LocateRegistry.getRegistry();
        registry.bind(GetChatRoomURL(name), room_stub);

        return GetChatRoomURL(name);
    }

    @Override
    public void RemoveChatRoom(String name) throws RemoteException {
        chatlist.remove(namelist.indexOf(name));
    }

}
