import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class mComponent extends JComponent implements Serializable {
    transient Image image, image2;
    Config config = new Config();
    int x0, y0;

    public mComponent(int x, int y, String bgrName) {
        super();
        setLocation(x, y);
        loadImages(bgrName);
        config.simpleName = getClass().getSimpleName();
        setTransferHandler(new mTransferHandler(this, config));

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                //super.mouseDragged(e);
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
                //super.mouseMoved(e);
                x0 = e.getX();
                y0 = e.getY();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //super.mousePressed(e);
                if (e.getModifiers() == 18 || e.getModifiers() == 17) {
                    JComponent c = (JComponent) e.getSource();
                    TransferHandler handler = c.getTransferHandler();
                    if (handler != null) handler.exportAsDrag(c, e, TransferHandler.COPY);
                    else System.out.println("null handler " + getDropTarget());
                } else super.mousePressed(e);
            }
        });
    }

    void loadImages(String bgrName) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(bgrName);
            image = ImageIO.read(is);
            int diameter = 60;
            image = image.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);
            setPreferredSize(new Dimension(diameter, diameter));
            setSize(diameter, diameter);

            is = getClass().getClassLoader().getResourceAsStream("res/puzyr.png");
            image2 = ImageIO.read(is);
            diameter = 15;
            image2 = image2.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, this);
        g.drawString("" + getX() + "," + getY(), 8, getHeight() / 2 + 6);
        g.drawImage(image2, getWidth() - 20, getHeight() - 15, this);
        g.drawImage(image2, getWidth() - 20, getHeight() - 32, this);
        g.drawImage(image2, getWidth() - 20, getHeight() - 49, this);
    }


}
