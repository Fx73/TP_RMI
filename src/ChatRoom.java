import java.rmi.RemoteException;

public class ChatRoom implements Chat{
    String _chatlog;
    ChatRoom(String chatlog){
        _chatlog = chatlog;
    }

    public void Say(String name, String s) throws RemoteException {
        _chatlog += "\n <" + name + ">\n" + s;
    }

    public String Get_chatlog() throws RemoteException{
        return _chatlog;
    }
}
