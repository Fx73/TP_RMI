public class ChatRoom implements Chat{
    String _chatlog;
    ChatRoom(String chatlog){
        _chatlog = chatlog;
    }

    public void Say(String name, String s){
        _chatlog += "\n <" + name + ">\n" + s;
    }

    public String Get_chatlog(){
        return _chatlog;
    }
}
