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
    //String[] columnNames = {"First Name", "Last Name", "Sport", "# of Years", "Vegetarian"};

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

    void prepareArea(JComponent pan) {
        JTextArea area = new JTextArea();
        //for (int i = 0; i < data.length ; i++) area.append(columnNames[i]+": "+data[i]+"\n");
        for (int i = 0; i < data.length ; i++) area.append(data[i]+"\n");
        add(area);
        setSize(new Dimension(area.getPreferredSize().width, area.getPreferredSize().height));

        area.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                pan.removeAll();
                setSize(new Dimension(50, 50)); //нужен для пайнт
            }
        });
    }

    double minLat = 59.84;
    double maxLat = 60.1;
    double minLng = 30.31;
    double maxLng = 30.35;

    public int calcX(String lat) {
        return (int) (dimension.width * (Double.valueOf(lat) - minLat) / (maxLat - minLat));
    }

    public int calcY(String lng) {
        return (int) (dimension.height * (Double.valueOf(lng) - minLng) / (maxLng - minLng));
    }

    public childPanel(String[] data, Dimension dimension, helperPanel helper) {
        super();
        this.data= data;
        this.dimension = dimension;
        this.helper = helper;
        setOpaque(false);//фолс - прозрачный
        //setPreferredSize(new Dimension(150, 150));//нужен для фловлейаут
        //setSize(new Dimension(50, 50)); //нужен для пайнт

        int x = calcX(data[2]);
        int y = calcY(data[3]);
        setLocation(new Point(x,y));

        JPanel pan = this;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (getComponents().length < 1) prepareArea(pan);
                getParent().setVisible(false);
                getParent().setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                //if (getComponents().length < 1) prepareArea(pan);
                //getParent().setVisible(false);
                //getParent().setVisible(true);
                helper.setData(data);
                helper.setVisible(true);
                helper.setLocation(getX(),getY());
                //setSize(helper.area.getWidth(),helper.area.getHeight());
                //getParent().repaint();
                //getParent().setVisible(false);
                //getParent().setVisible(true);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                //helper.setVisible(false);
                //pan.removeAll();
                //setSize(new Dimension(50, 50)); //нужен для пайнт
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
    public void paint(Graphics g) {
        super.paint(g);
        //if (getComponents().length == 0) g.drawOval(0, 0, getWidth(), getHeight());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,0,0,this);
    }

}
