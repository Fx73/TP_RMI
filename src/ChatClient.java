import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class ChatClient {
	static Chat chat;

  public static void main(String [] args) {
	String host;
	SwingUtilities.invokeLater(() -> Frame.getWindow().setVisible(true));

	try {
		if (args.length < 1)
			host = "localhost";
		else
			host = args[0];


	// Get remote object reference
		Registry registry = LocateRegistry.getRegistry(host);
		chat = (Chat) registry.lookup("ChatService");

 	// Set up a timer for update
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {public void run() {Update();}}, 4000, 4000);


	} catch (Exception e)  {
		System.err.println("Error on client: " + e);
	}
  }

  static void Update(){
	  // Remote method invocation
	  try {
		  Frame.getWindow().set_chattextarea(chat.Get_chatlog());
	  } catch (RemoteException e) {
		  Frame.getWindow().set_chattextarea(Arrays.toString(e.getStackTrace()));
	  }
  }

  static void Say(String text){
	  // Remote method invocation
	  try {
		  chat.Say(System.getProperty("user.name"),text);
	  } catch (RemoteException e) {
		  Frame.getWindow().set_chattextarea(Arrays.toString(e.getStackTrace()));
	  }
  }

}

class Frame extends JFrame {
	private static Frame window = null;
	public static Frame getWindow(){
		return window == null? window = new Frame(): window;
	}

	private JTextArea _chattextarea;
	private JTextArea _messagearea;

	private Frame() {
		super("RMI Chat");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1000,600));


		//Barre de chat
		_messagearea  = new JTextArea ();
		JButton buttonsay = new JButton("Say");
		buttonsay.addActionListener(e -> {
			ChatClient.Say(_messagearea.getText());
			_messagearea.setText("");
		});
		buttonsay.setMinimumSize(new Dimension(40, 40));
		JSplitPaneWithZeroSizeDivider chatpane = new JSplitPaneWithZeroSizeDivider(JSplitPane.HORIZONTAL_SPLIT,buttonsay,_messagearea);
		chatpane.setResizeWeight(0.1);
		chatpane.setEnabled(false);

		//Zone principale
		_chattextarea = new JTextArea ();
		_chattextarea.setEditable(false);
		JSplitPaneWithZeroSizeDivider textpane = new JSplitPaneWithZeroSizeDivider(JSplitPane.VERTICAL_SPLIT,new JScrollPane(_chattextarea),chatpane);
		textpane.setResizeWeight(1);

		add(textpane);
		pack();
	}

	public void set_chattextarea(String area){
		_chattextarea.setText(area);
	}

}