import javax.media.format.VideoFormat;
import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.util.Vector;
import javax.media.*;

public class Main extends Applet {
    private static Player player;

    public Main() throws HeadlessException {
        super();
        setLayout( new BorderLayout());
        add(new MainForm().$$$getRootComponent$$$());
        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("ВДНХ");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setPreferredSize(new Dimension(580,600));
        f.add(new MainForm().$$$getRootComponent$$$());
        f.pack();
        f.setVisible(true);

        JPanel panel = (JPanel) f.getContentPane();
        Vector devicesList = CaptureDeviceManager.getDeviceList(new VideoFormat("YUV"));
        System.out.println(devicesList);
        //String mediaFile = "vfw:Microsoft WDM Image Capture (Win32):0";
        String mediaFile = "vfl://:0";
        try {
            MediaLocator mlr = new MediaLocator(mediaFile);
            player = Manager.createRealizedPlayer(mlr);
            player.setRate(100);
            if (player.getVisualComponent() != null) {
                panel.add("Center", player.getVisualComponent());
            }
            if (player.getControlPanelComponent() != null) {
                panel.add("South", player.getControlPanelComponent());
            }
        } catch (Exception e) {
            System.err.println("Got exception " + e);
        }
    }
}
