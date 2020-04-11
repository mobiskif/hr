import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.*;

public class mPanel extends JPanel implements Serializable {
    transient Image image, image2;
    Config config = new Config();
    int x0, y0;

    public mPanel(int x, int y, String bgrName) {
        super();
        setLocation(x, y);
        loadImages(bgrName);
        config.simpleName = getClass().getSimpleName();
        setTransferHandler(new mTransferHandler(this,config));

        setLayout(null);
        //add(new Athom(10, 20));
        //add(new Athom(50, 75));

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
            int w, h;
            //image = image.getScaledInstance(120, 160, Image.SCALE_SMOOTH);
            if (image != null) {
                w = image.getWidth(this);
                h = image.getHeight(this);
            } else {
                w = 120;
                h = 160;
            }
            setPreferredSize(new Dimension(w, h));
            setSize(w, h);

            is = getClass().getClassLoader().getResourceAsStream("res/puzyr2.png");
            image2 = ImageIO.read(is);
            image2 = image2.getScaledInstance(15, 15, Image.SCALE_SMOOTH);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
        g.drawRect(1, 1, getWidth() - 2, getHeight() - 2);
        //g.drawString(getWidth() + "," + getHeight(), 16, 32);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();

        Font oldf = g.getFont();
        g.setFont(new Font("Serif", Font.BOLD, 18));
        g.drawString(getComponents().length + "", 16, 20);
        g.drawString("" + config.title, 16, getHeight() - 20);
        g.setFont(oldf);
        g.drawString("" + config.simpleName, 16, getHeight() - 38);

        g.drawImage(image2, getWidth() - 20, 15, this);
        g.drawImage(image2, getWidth() - 20, 32, this);
        g.drawImage(image2, getWidth() - 20, 49, this);
    }

}
