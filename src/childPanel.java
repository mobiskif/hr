import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

public class childPanel extends JPanel {
    int x0, y0;
    String[] data;
    Image image;
    Dimension dimension;
    helperPanel helper;
    boolean fixed = false;

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

    void loadImages() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/sphere.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image != null) {
            int W = image.getWidth(this);
            int H = image.getHeight(this);
            setPreferredSize(new Dimension(W, H));
            setSize(W, H);
        }
        repaint();

    }

    double minLat = 59.98;
    double maxLat = 59.88;
    double minLng = 30.21;
    double maxLng = 30.53;

    public int calcY(String lat) {
        return (int) ((Double.valueOf(lat) - minLat) * dimension.height / (maxLat - minLat));
    }

    public int calcX(String lng) {
        return (int) ((Double.valueOf(lng) - minLng) * dimension.width / (maxLng - minLng));
    }

    public childPanel(String[] data, Dimension dimension, helperPanel helper) {
        super();
        this.data = data;
        this.dimension = dimension;
        this.helper = helper;
        setOpaque(false);//фолс - прозрачный
        //setPreferredSize(new Dimension(150, 150));//нужен для фловлейаут
        //setSize(new Dimension(50, 50)); //нужен для пайнт

        setTransferHandler(new workerTransferHandler(this));

        int y = calcY(data[2]);
        int x = calcX(data[3]);
        setLocation(new Point(x, y));

        //JPanel pan = this;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                fixed = !fixed;
                helper.fixed = fixed;
                getParent().repaint();
                ((parentPanel) getParent()).video.setLocation(helper.getX() + helper.getWidth(), getY());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (!fixed) {
                    helper.setData(data);
                    helper.setVisible(true);
                    helper.setLocation(getX() + getWidth(), getY());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (!fixed) helper.setVisible(false);
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

        loadImages();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

}
