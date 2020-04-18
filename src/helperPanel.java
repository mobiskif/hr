import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class helperPanel extends basePanel {
    final JTextArea area = new JTextArea();
    //boolean fixed = false;

    public helperPanel() {
        super("helper.jpg",300,300);
        setVisible(false);
        area.setBounds(14, 10, getWidth()/2, getHeight() - 14);
        area.setOpaque(false);
        add(area);
    }

    public void setData(String[] data) {
        area.setText("");
        for (String datum : data) area.append(datum + "\n");
        getParent().repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fixed) {
            area.setForeground(Color.WHITE);
            area.setFont(new Font("Sans Serif", Font.BOLD, 13));
        } else {
            area.setFont(new Font("Sans Serif", Font.PLAIN, 13));
            area.setForeground(Color.BLACK);
        }

    }
}
