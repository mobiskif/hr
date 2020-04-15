import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

public class helperPanel extends JPanel {
    int x0, y0;
    Image image;
    JTextArea area = new JTextArea();

    void loadImages() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/office.jpg"));
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

    public helperPanel() {
        super();
        setOpaque(false);//фолс - прозрачный
        setLayout(new BorderLayout());
        //area.setOpaque(false);
        add(area);

        area.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                //setSize(new Dimension(area.getWidth(), area.getHeight())); //нужен для пайнт
                setVisible(false);
            }
        });

        //setLocation(130,130);
        //setPreferredSize(new Dimension(100,100));//нужен для фловлейаут
        setSize(180,120);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                //setSize(new Dimension(area.getWidth(), area.getHeight())); //нужен для пайнт
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

        //loadImages();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawImage(image,0,0,this);
        //g.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
    }

    public void setData(String[] data) {
        area.setText("");
        for (int i = 0; i < data.length ; i++) area.append(data[i]+"\n");
        //setSize(new Dimension(area.getPreferredSize().width, area.getPreferredSize().height));
        //setSize(new Dimension(area.getWidth(), area.getHeight())); //нужен для пайнт
        getParent().repaint();
    }
}
