import java.rmi.Remote;

public interface Chat extends Remote {
    void Say(String name, String s);
    String Get_chatlog();


    }
