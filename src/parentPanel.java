import javax.swing.*;
import java.awt.*;

public class parentPanel extends JPanel {
    //JPanel pan2 = new childPanel();

    public parentPanel() {
        setPreferredSize(new Dimension(500,400)); //нужен для майнформ
        setLayout(new FlowLayout());
        JPanel pan1 = new childPanel();
        pan1.setLocation(18,27);
        add(pan1);
        JPanel pan2 = new childPanel();
        pan2.setLocation(100,40);
        add(pan2);
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //g.drawRect(1,1,getWidth()-3,getHeight()-3);
    }
}
