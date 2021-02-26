import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ChatRoom implements Room, Serializable {
    ChatLog _chatlog;
    String _name;
    ArrayList<String> users = new ArrayList<>();

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

    public void Register_User(String name){
        users.add(name);
    }

    public void Unregister_User(String name){
        users.remove(name);
    }
    public String[] Get_Users(){
        return users.toArray(new String[0]);
    }

}
