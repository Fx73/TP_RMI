import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ChatRoom implements Room, Serializable {
    ChatLog _chatlog;
    String _name;
    ArrayList<String> users = new ArrayList<>();
    ArrayList<Timer> timers = new ArrayList<>();

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
        Timer timer = new Timer(name);
        timers.add(timer);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                AutoUnregister_User(name);
            }
        }, 10000, 10000);
    }

    public void Unregister_User(String name){
        users.remove(name);
    }
    public String[] Get_Users(){
        return users.toArray(new String[0]);
    }

    private TimerTask AutoUnregister_User(String name){
        timers.remove(users.indexOf(name));
        users.remove(name);
        return null;
    }
}

class User{
    String _name;
    Timer _timer;
    public User(String name) {
        _name = name;
        _timer = new Timer();
    }


}