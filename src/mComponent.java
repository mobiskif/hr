import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.Set;

public class mComponent extends JPanel implements Serializable {
    transient Image image, smallImage, bigImage, ledImage;
    HashMap conf = new HashMap();
    int x0, y0;
    int W = 100;
    int H = 100;
    Webcam webcam = null;
    WebcamPanel webcamPanel = null;

    public mComponent(int x, int y) {
        super();
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setLocation(x, y);
        setOpaque(false);

        conf.put("simpleName", getClass().getSimpleName());
        conf.put("showLed", true);
        conf.put("transparent", false);
        conf.put("smallImgName", "res/sphere.png");
        conf.put("bigImgName", "res/vd6.jpg");
        conf.put("ledImgName", "res/puzyr2.png");


        setTransferHandler(new mTransferHandler(this));

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

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (image == bigImage) initSmall();
                else initBig();
                repaint();
                offCam();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getModifiers() == 18 || e.getModifiers() == 17) {
                    JComponent c = (JComponent) e.getSource();
                    TransferHandler handler = c.getTransferHandler();
                    if (handler != null) handler.exportAsDrag(c, e, TransferHandler.COPY);
                    else System.out.println("null handler " + getDropTarget());
                } else super.mousePressed(e);
            }
        });

        loadImages();
    }

    void loadImages() {
        try {
            smallImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream(conf.get("smallImgName").toString()));
            bigImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream(conf.get("bigImgName").toString()));
            ledImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream(conf.get("ledImgName").toString()));
            ledImage = ledImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
            initSmall();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void initSmall() {
        image = smallImage;
        if (image != null) {
            W = image.getWidth(this);
            H = image.getHeight(this);
            setPreferredSize(new Dimension(W, H));
            setSize(W, H);
        }
        repaint();
    }

    void initBig() {
        //txt.setVisible(true);
        image = bigImage;
        if (image != null) {
            W = image.getWidth(this);
            H = image.getHeight(this);
            setPreferredSize(new Dimension(W, H));
            setSize(W, H);
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //setSize(W,H);
        g.drawImage(image, 0, 0, this);
        //g.drawString(getX() + "," + getY() + " " + getWidth() + "," + getHeight(), 4, 14);

        if ((boolean) conf.get("transparent")) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.35f));
            g2d.setColor(getBackground());
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }

        Font oldf = g.getFont();
        g.setFont(new Font("Serif", Font.BOLD, 18));
        g.drawString("" + conf.get("salary"), 10, getHeight() / 2 + 4);
        //if ((boolean) conf.get("showLed")) {
        if (image == bigImage) {
            g.drawImage(ledImage, getWidth() - 20, 5, this);
            g.drawImage(ledImage, getWidth() - 20, 22, this);
            g.drawImage(ledImage, getWidth() - 20, 39, this);
            g.drawString("" + conf.get("title"), 10, getHeight() - 15);
            g.drawString("" + conf.get("employer"), 10, getHeight() - 35);
        }
        g.setFont(oldf);

        //g.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
    }

    public void showCam() {
        if (webcam != null) {
            webcam.close();
            webcam = null;
            getParent().remove(webcamPanel);
            getParent().repaint();
        } else {
            webcam = Webcam.getDefault();
            webcam.setViewSize(WebcamResolution.VGA.getSize());
            webcamPanel = new WebcamPanel(webcam);
            webcamPanel.setFPSDisplayed(true);
            //panel.setDisplayDebugInfo(true);
            webcamPanel.setImageSizeDisplayed(true);
            webcamPanel.setMirrored(true);
            webcamPanel.setVisible(true);
            webcamPanel.setBounds(getX() + getWidth() + 4, getY(), 320, 240);
            getParent().add(webcamPanel);
        }
    }

    public void offCam() {
        if (webcam != null) {
            webcam.close();
            webcam = null;
            getParent().remove(webcamPanel);
            getParent().repaint();
        }
    }
}
