import java.rmi.RemoteException;

public class ChatRoom implements Room {
    String _chatlog;
    String _name;
    ChatRoom(String name, String chatlog){
        _name = name;
        _chatlog = chatlog;
    }

    public String GetRoomName() throws RemoteException{
        return _name;
    }

    public void Say(String name, String s) throws RemoteException {
        _chatlog += "\n <" + name + ">\n" + s;
    }

    public String Get_chatlog() throws RemoteException{
        return _chatlog;
    }
}
