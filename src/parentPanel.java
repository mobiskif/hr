import javax.swing.*;
import java.awt.*;

public class parentPanel extends JPanel {
    Object[][] data = {
            {"Kathy", "Smith", "Snowboarding", "5", "yes"},
            {"John", "Doe", "Rowing", "2", "no"},
    };
    mTableModel model;

    public parentPanel() {
        setPreferredSize(new Dimension(500, 400)); //нужен для майнформ
        setLayout(new FlowLayout());
        JPanel pan1 = new childPanel(data[0]);
        pan1.setLocation(18, 10);
        add(pan1);
        JPanel pan2 = new childPanel(data[1]);
        pan2.setLocation(100, 40);
        add(pan2);
        setLayout(null);

        model = new mTableModel("IT");
        JPanel pan3 = new childPanel(model.adata.get(0));
        pan3.setLocation(70, 20);
        add(pan3);

/* */
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawRect(1, 1, getWidth()-3, getHeight()-3);
    }
}
