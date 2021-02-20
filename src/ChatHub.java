import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ChatHub implements Hub, Serializable {

    final ArrayList<String> namelist = new ArrayList<>();
    final ArrayList<ChatRoom> chatlist = new ArrayList<>();


    @Override
    public int GetChatRoomNumber() throws RemoteException {
        return chatlist.size();
    }

    @Override
    public String[] GetChatRoomNameList() throws RemoteException {
        return namelist.toArray(new String[0]);
    }

    @Override
    public String GetChatRoomURI(String name) throws RemoteException, NotBoundException {
        if(!namelist.contains(name))
            throw new NotBoundException("There is no room with this name : " + name);
        return "Room_"+name+"_Service";
    }

    @Override
    public String NewChatRoom(String name) throws RemoteException, AlreadyBoundException, NotBoundException {
        if(namelist.contains(name))
            throw new AlreadyBoundException("A room already exists with name : " + name);

        ChatRoom newchat = new ChatRoom(name, "");
        chatlist.add(newchat);
        namelist.add(name);

        Room room_stub = (Room) UnicastRemoteObject.exportObject(newchat, 0);
        Registry registry= LocateRegistry.getRegistry();
        registry.bind(GetChatRoomURI(name), room_stub);

        return GetChatRoomURI(name);
    }

    @Override
    public void RemoveChatRoom(String name) throws RemoteException, NotBoundException {
        Registry registry= LocateRegistry.getRegistry();
        registry.unbind(GetChatRoomURI(name));
        UnicastRemoteObject.unexportObject(chatlist.get(namelist.indexOf(name)), true);
        chatlist.remove(namelist.indexOf(name));
        namelist.remove(name);
    }

}