import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.Serializable;

public class basePanel extends JPanel implements Serializable {
    int x0, y0;
    transient Image image, ledImage;
    boolean fixed = false;

    public basePanel(String f, int x, int y) {
        super();
        setOpaque(false);//фолс - прозрачный
        setLayout(null);
        setLocation(new Point(x, y));
        setTransferHandler(new workerTransferHandler(this));
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/" + f));
            ledImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/puzyr2.png"));
            ledImage = ledImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image != null) {
            int W = image.getWidth(this);
            int H = image.getHeight(this);
            setPreferredSize(new Dimension(W, H));
            setSize(W, H);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                fixed = !fixed;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getModifiers() == (InputEvent.CTRL_MASK | InputEvent.BUTTON1_MASK) || e.getModifiers() == (InputEvent.SHIFT_MASK | InputEvent.BUTTON1_MASK)) {
                    JComponent c = (JComponent) e.getSource();
                    TransferHandler handler = c.getTransferHandler();
                    if (handler != null) handler.exportAsDrag(c, e, TransferHandler.COPY);
                    else System.out.println("null handler " + getDropTarget());
                } else super.mousePressed(e);
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
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
        if (!fixed) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.65f));
            g2d.setColor(getBackground());
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }
        if(!getClass().getSimpleName().startsWith("childPanel")) {
            g.drawImage(ledImage, getWidth() - 20, 5, this);
            g.drawImage(ledImage, getWidth() - 20, 22, this);
            g.drawImage(ledImage, getWidth() - 20, 39, this);
        }
    }

}
