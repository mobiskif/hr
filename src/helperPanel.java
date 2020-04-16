import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

public class helperPanel extends JPanel {
    JTextArea area = new JTextArea();

    public helperPanel() {
        super();
        setLayout(new BorderLayout());
        setSize(180,120);

        add(area);
        area.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                setVisible(false);
            }
        });

    }

    public void setData(String[] data) {
        area.setText("");
        for (int i = 0; i < data.length ; i++) area.append(data[i]+"\n");
        getParent().repaint();
    }
}
