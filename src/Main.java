import javax.swing.*;
import java.applet.Applet;
import java.awt.*;

public class Main extends Applet {

    public Main() throws HeadlessException {
        super();
        setLayout( new BorderLayout());
        add(new MainForm().$$$getRootComponent$$$());
        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("ВДНХ");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MainForm mf = new MainForm();
        f.add(mf.$$$getRootComponent$$$());
        f.setPreferredSize(new Dimension(mf.objectsPanel.W,mf.objectsPanel.H));
        f.pack();
        f.setVisible(true);
    }
}
