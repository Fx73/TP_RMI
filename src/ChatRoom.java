import java.io.Serializable;
import java.rmi.RemoteException;

public class ChatRoom implements Room, Serializable {
    ChatLog _chatlog;
    String _name;
    ChatRoom(String name){
        _name = name;
        _chatlog = new ChatLog(100);
    }

    public String GetRoomName() throws RemoteException{
        return _name;
    }

    public void Say(String name, String s) throws RemoteException {
        _chatlog.Add_log(name,s);
    }

    public String Get_chatlog() throws RemoteException{
        return _chatlog.Get_Logs();
    }
}
