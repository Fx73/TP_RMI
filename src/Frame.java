import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class Frame extends JFrame {
    private static Frame window = null;

    public static Frame getWindow() {
        return window == null ? window = new Frame("") : window;
    }

    public static Frame setWindow(String name) {
        return window = new Frame(name);
    }

    private final JTextArea _chattextarea = new JTextArea();
    private final JTextArea _messagearea = new JTextArea();
    private final JPanel roombuttoncontainer = new JPanel();
    public JTextField nom;


    private Frame(String target) {
        super("RMI Chat : " + target);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 600));


        //Barre de chat
        final JButton buttonsay = new JButton("Say");
        buttonsay.addActionListener(e -> {
            ChatClient.Say(_messagearea.getText());
            _messagearea.setText("");
        });
        buttonsay.setMinimumSize(new Dimension(40, 40));
        final JSplitPaneWithZeroSizeDivider chatpane = new JSplitPaneWithZeroSizeDivider(JSplitPane.HORIZONTAL_SPLIT, buttonsay, _messagearea);
        chatpane.setResizeWeight(0.1);
        chatpane.setEnabled(false);

        //Zone de dialogue
        _chattextarea.setEditable(false);
        _chattextarea.setLineWrap(true);
        final JSplitPaneWithZeroSizeDivider textpane = new JSplitPaneWithZeroSizeDivider(JSplitPane.VERTICAL_SPLIT, new JScrollPane(_chattextarea), chatpane);
        textpane.setResizeWeight(1);


        //Pannel de gauche
        nom = new JTextField();
        nom.setText(System.getProperty("user.name"));
        nom.setHorizontalAlignment(JTextField.CENTER);
        nom.setEnabled(false);
        nom.addMouseListener(nommouselistener);
        roombuttoncontainer.setLayout(new BoxLayout(roombuttoncontainer, BoxLayout.Y_AXIS));
        roombuttoncontainer.setBorder(new EmptyBorder(new Insets(3, 3, 2000, 3)));
        final JSplitPaneWithZeroSizeDivider roompane = new JSplitPaneWithZeroSizeDivider(JSplitPane.VERTICAL_SPLIT, nom, roombuttoncontainer);


        //Panel + et -
        final JButton buttonplus = new JButton("+");
        buttonplus.addActionListener(e -> ChatClient.Create_Room());
        final JButton buttonmoins = new JButton("-");
        buttonmoins.addActionListener(e -> ChatClient.Delete_Room());
        final JSplitPaneWithZeroSizeDivider toolpane = new JSplitPaneWithZeroSizeDivider(JSplitPane.HORIZONTAL_SPLIT, buttonplus, buttonmoins);
        toolpane.setResizeWeight(0.5);

        final JSplitPaneWithZeroSizeDivider leftpane = new JSplitPaneWithZeroSizeDivider(JSplitPane.VERTICAL_SPLIT, roompane, toolpane);
        leftpane.setResizeWeight(1);
        final JSplitPaneWithZeroSizeDivider mainpane = new JSplitPaneWithZeroSizeDivider(JSplitPane.HORIZONTAL_SPLIT, leftpane, textpane);
        mainpane.setResizeWeight(0.05);
        mainpane.setEnabled(false);


        add(mainpane);
        pack();
    }

    public void set_chattextarea(String area) {
        _chattextarea.setText(area);
        Frame.getWindow().revalidate();
    }

    public String[] buttonlist = new String[0];

    public void UpdateButtons(String[] newbuttonlist) {
        if (Arrays.equals(buttonlist, newbuttonlist)) return;
        roombuttoncontainer.removeAll();

        for (int i = 0; i < newbuttonlist.length; i++) {
            JPanel p = new JPanel();
            p.setLayout(new BorderLayout());
            p.setBorder(new EmptyBorder(new Insets(3, 0, 0, 0)));

            JButton b = new JButton(newbuttonlist[i]);
            b.addActionListener(e -> ChatClient.Select_Room(((JButton) e.getSource()).getName()));

            p.add(b);
            roombuttoncontainer.add(p);
        }
        buttonlist = newbuttonlist;
        pack();
    }

    MouseListener nommouselistener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            String result = (String) JOptionPane.showInputDialog(
                    Frame.getWindow(),
                    "Change your name",
                    "Name",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    Frame.getWindow().nom.getText()
            );

            if (result != null)
                Frame.getWindow().nom.setText(result);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    };
}
