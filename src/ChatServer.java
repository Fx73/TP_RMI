
import java.io.*;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.Timer;
import java.util.TimerTask;

public class ChatServer {
	static ChatHub hub;



	public static void  main(String [] args) {
		Load();

		try {
		  // Create a ChatRoom remote object
	    	Hub hub_stub = (Hub) UnicastRemoteObject.exportObject(hub, 0);

	    	// Register the remote object in RMI registry with a given identifier
	    	Registry registry= LocateRegistry.getRegistry();
	    	registry.bind("ChatService", hub_stub);

	    	System.out.println ("Server ready");

	  } catch (Exception e) {
		  System.err.println("Error on server :" + e) ;
		  e.printStackTrace();
	  }

		// Set up a timer for save
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				Save();
			}
		}, 10000, 10000);


		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				Registry registry= LocateRegistry.getRegistry();
				registry.unbind("ChatService");
				UnicastRemoteObject.unexportObject(hub, true);
				Save();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}));
  }

  static void Load(){
	  try {
		  FileInputStream fi = new FileInputStream("hubsave.txt");
		  ObjectInputStream oi = new ObjectInputStream(fi);

		  hub = (ChatHub) oi.readObject();

		  oi.close();
		  fi.close();
		  System.out.println("Save File found");
	  } catch (IOException | ClassNotFoundException e) {
		  hub = new ChatHub();
		  System.out.println("No save found");
	  }
	  int i=0;
	  try {
	  for (i = 0; i < hub.namelist.size(); i++) {
			Registry registry = LocateRegistry.getRegistry();
			Room room_stub = (Room) UnicastRemoteObject.exportObject(hub.chatlist.get(i), 0);
			registry.bind(hub.GetChatRoomURI(hub.namelist.get(i)), room_stub);
	  }
		  System.out.println("Successfully bound " + hub.namelist.size() + " saved rooms");
	  }catch (RemoteException | NotBoundException | AlreadyBoundException e) {
		  System.out.println("Error bounding saved room : " + hub.namelist.get(i));

	  }

  }

  static void Save(){
	  try {
		  FileOutputStream f = new FileOutputStream("hubsave.txt");
		  ObjectOutputStream o = new ObjectOutputStream(f);

		  o.writeObject(hub);

		  o.close();
		  f.close();
	  } catch (IOException e) {
		  System.out.println("Save log fail : "+e);
	  }
  }

}
