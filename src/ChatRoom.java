import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ChatRoom implements Room, Serializable {
    ChatLog _chatlog;
    String _name;
    transient final ArrayList<String> users = new ArrayList<>();
    transient final ArrayList<Timer> timers = new ArrayList<>();

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
        Timer timer = new Timer(name);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                AutoUnregister_User(name);
            }
        }, 10000, 20000);

        if(users.contains(name)){
            timers.set(users.indexOf(name),timer);
        }
        else{
            users.add(name);
            timers.add(timer);
        }
    }

    public void Unregister_User(String name){
        timers.remove(users.indexOf(name));
        users.remove(name);
    }
    public String[] Get_Users(){
        return users.toArray(new String[0]);
    }

    private TimerTask AutoUnregister_User(String name){
        Unregister_User(name);
        return null;
    }
}
