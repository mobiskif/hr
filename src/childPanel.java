import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class childPanel extends basePanel {
    final String[] data;
    final Dimension dimension;
    final helperPanel helper;

    void prepareTable(JComponent pan) {
        setLayout(new FlowLayout());
        //JTable table = new JTable(data, columnNames);
        JTable table = new JTable();
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

    final double minLat = 59.98;
    final double maxLat = 59.88;
    final double minLng = 30.21;
    final double maxLng = 30.53;

    public int calcY(String lat) {
        return (int) ((Double.parseDouble(lat) - minLat) * dimension.height / (maxLat - minLat));
    }

    public int calcX(String lng) {
        return (int) ((Double.parseDouble(lng) - minLng) * dimension.width / (maxLng - minLng));
    }

    public childPanel(String[] data, Dimension dimension, helperPanel helper) {
        super("sphere.png", 0,0);
        fixed=true;
        this.data = data;
        this.dimension = dimension;
        this.helper = helper;

        int y = calcY(data[2]);
        int x = calcX(data[3]);
        setLocation(new Point(x, y));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                helper.fixed = !fixed;
                getParent().repaint();
                ((mapPanel) getParent()).video.setLocation(helper.getX() + helper.getWidth(), getY());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (fixed && !helper.fixed) {
                    helper.setData(data);
                    helper.setVisible(true);
                    helper.setLocation(getX() + getWidth(), getY());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (fixed && !helper.fixed) helper.setVisible(false);
           }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Sans Serif", Font.BOLD, 13));
        g.drawString(data[4],6, getWidth()/2+4);
    }
}
