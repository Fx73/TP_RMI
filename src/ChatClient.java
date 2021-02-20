import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class ChatClient {
	static Room room = null;
	static Hub hub;
	static Registry registry;

	public static void main(String[] args) {
		String host;
		if (args.length < 1)
			host = "localhost";
		else
			host = args[0];

		SwingUtilities.invokeLater(() -> Frame.setWindow(host).setVisible(true));

		try {
			// Get remote object reference
			registry = LocateRegistry.getRegistry(host);
			hub = (Hub) registry.lookup("ChatService");

			// Set up a timer for update
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				public void run() {
					Update();
				}
			}, 1000, 10000);


		} catch (Exception e) {
			Frame.getWindow().set_chattextarea("Error on client: " + e);
		}
	}

	static void Update() {
		// Remote method invocation
		try {
			Frame.getWindow().UpdateButtons(hub.GetChatRoomNameList());
		} catch (RemoteException e) {
			Frame.getWindow().set_chattextarea(Arrays.toString(e.getStackTrace()));
		}
		if (room != null) {
			try {
				Frame.getWindow().set_chattextarea(room.Get_chatlog());
			} catch (RemoteException e) {
				Frame.getWindow().set_chattextarea(Arrays.toString(e.getStackTrace()));
			}
		} else {
			Frame.getWindow().set_chattextarea("Select a room or start a new one");
		}
		Frame.getWindow().revalidate();
		Frame.getWindow().repaint();
	}

	static void Say(String text) {
		if (room != null){
		// Remote method invocation
		try {
			room.Say(Frame.getWindow().nom.getText(), text);
			Update();
		} catch (RemoteException e) {
			Frame.getWindow().set_chattextarea(Arrays.toString(e.getStackTrace()));
		}
		}
	}

	static void Select_Room(String name) {
		// Remote method invocation
		try {
			String roomuri = hub.GetChatRoomURL(name);
			room = null;
			room = (Room) registry.lookup(roomuri);
			Update();

		} catch (RemoteException | NotBoundException e) {
			Frame.getWindow().set_chattextarea(Arrays.toString(e.getStackTrace()));
		}
	}

	static void Create_Room() {
        String result = (String)JOptionPane.showInputDialog(
                Frame.getWindow(),
                "Select the room name",
                "New Room",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
				""
        );
        if(result == null)
            return;
		// Remote method invocation
		try {
			String roomuri = hub.NewChatRoom(result);
			room = (Room) registry.lookup(roomuri);
			Update();

		} catch (RemoteException | NotBoundException | AlreadyBoundException e) {
			Frame.getWindow().set_chattextarea(Arrays.toString(e.getStackTrace()));
		}
	}

	static void Delete_Room() {
		// Remote method invocation
		try {
			hub.RemoveChatRoom(room.GetRoomName());
			room = null;
			Update();

		} catch (RemoteException | NotBoundException e) {
			Frame.getWindow().set_chattextarea(Arrays.toString(e.getStackTrace()));
		}
	}
}

