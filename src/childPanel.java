import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class childPanel extends JPanel {
    int x0, y0;

    Object[][] data = {
            {"Kathy", "Smith",
                    "Snowboarding", new Integer(5), new Boolean(false)},
            {"John", "Doe",
                    "Rowing", new Integer(3), new Boolean(true)},
            {"Sue", "Black",
                    "Knitting", new Integer(2), new Boolean(false)},
            {"Jane", "White",
                    "Speed reading", new Integer(20), new Boolean(true)},
            {"Joe", "Brown",
                    "Pool", new Integer(10), new Boolean(false)}
    };
    String[] columnNames = {"First Name",
            "Last Name",
            "Sport",
            "# of Years",
            "Vegetarian"};

    void prepareTable(JComponent pan) {
        setLayout(new FlowLayout());
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(
                new Dimension(
                        table.getPreferredScrollableViewportSize().width,
                        table.getRowCount() * table.getRowHeight()
                ));
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setEnabled(false);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                pan.removeAll();
                setSize(new Dimension(50, 50)); //нужен для пайнт
            }
        });
        add(scrollPane);
        setSize(new Dimension(scrollPane.getPreferredSize().width + 6, scrollPane.getPreferredSize().height + 20));
    }

    public childPanel() {
        super();
        setOpaque(false);

        setPreferredSize(new Dimension(150,150));//нужен для фловлейаут
        setSize(new Dimension(50, 50)); //нужен для пайнт
        JPanel pan = this;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (getComponents().length < 1) prepareTable(pan);
                getParent().setVisible(false);
                getParent().setVisible(true);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x1, y1, dx, dy;
                x1 = e.getX();
                y1 = e.getY();
                dx = x1 - x0;
                dy = y1 - y0;
                Component n = (Component) e.getSource();
                n.setLocation(n.getX() + dx, n.getY() + dy);
                n.getParent().repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                x0 = e.getX();
                y0 = e.getY();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //g.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
        if(getComponents().length==0)g.drawOval(0,0,getWidth(),getHeight());
    }

}
