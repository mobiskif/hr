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
        f.setPreferredSize(new Dimension(580,600));
        f.add(new MainForm().$$$getRootComponent$$$());
        f.pack();
        f.setVisible(true);
    }
}
