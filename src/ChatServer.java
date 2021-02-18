
import java.rmi.*; 
import java.rmi.server.*; 
import java.rmi.registry.*;

public class ChatServer {
		static String _chatlog = "";



  public static void  main(String [] args) {

		try {
		  // Create a ChatRoom remote object
	    ChatRoom h = new ChatRoom (_chatlog);
	    Chat h_stub = (Chat) UnicastRemoteObject.exportObject(h, 0);

	    // Register the remote object in RMI registry with a given identifier
	    Registry registry= LocateRegistry.getRegistry(); 
	    registry.bind("ChatService", h_stub);

	    System.out.println ("Server ready");

	  } catch (Exception e) {
		  System.err.println("Error on server :" + e) ;
		  e.printStackTrace();
	  }
  }
}
