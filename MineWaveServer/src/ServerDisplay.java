import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class ServerDisplay extends JPanel {

    /*
    Pretty much copy pasted from a previous project I did, no reason not to tbh.
     */

    public static JFrame frame;
    public static ServerDisplay mainPanel;
    public static ServerThread myServer;

    private JTextArea loggingDisplay;


    public ServerDisplay(LayoutManager layout) {
        super(layout);
    }

    private void addComponents() {
        GridBagConstraints c = new GridBagConstraints();

        loggingDisplay = new JTextArea(25,30);
        c.insets = new Insets(10, 10, 10, 10);
        loggingDisplay.setLineWrap(false);
        loggingDisplay.setWrapStyleWord(true);
        loggingDisplay.setFont(new Font(loggingDisplay.getFont().getName(), loggingDisplay.getFont().getStyle(), (int)(loggingDisplay.getFont().getSize()*0.6)));
        JScrollPane areaScrollPane = new JScrollPane(loggingDisplay);
        loggingDisplay.setEditable(false);
        areaScrollPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        c.gridwidth = 2;
        add(areaScrollPane, c);
    }

    public void log(String toLog) {
        loggingDisplay.append(toLog + "\n");
        //Write to file.
    }

    public static ServerDisplay init(ServerThread myServer) {
        ServerDisplay.myServer = myServer;
        frame = new JFrame("ServerDisplay");
        mainPanel = new ServerDisplay(new GridBagLayout());
        mainPanel.addComponents();
        frame.add(mainPanel);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
        return mainPanel;
    }

}