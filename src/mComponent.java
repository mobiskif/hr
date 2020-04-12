import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;

public class mComponent extends JPanel implements Serializable {
    transient Image image, image2;
    HashMap conf = new HashMap();
    int x0, y0, W, H;
    Webcam webcam = null;
    WebcamPanel webcamPanel = null;

    public mComponent(int x, int y, String bgrName) {
        super();
        setLayout(null);
        setLocation(x, y);
        loadImages(bgrName);
        setOpaque(false);

        conf.put("simpleName", getClass().getSimpleName());
        conf.put("showLed", true);
        conf.put("transparent", false);

        setTransferHandler(new mTransferHandler(this));

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
            public void mouseClicked(MouseEvent e) {
                //super.mouseClicked(e);
                //if ((boolean) conf.get("showLed")) showCam();
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
            //int diameter = 60;
            //image = image.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);
            setPreferredSize(new Dimension(w, h));
            setSize(w, h);
            W = w;
            H = h;

            is = getClass().getClassLoader().getResourceAsStream("res/puzyr2.png");
            image2 = ImageIO.read(is);
            int diameter = 15;
            image2 = image2.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font oldf = g.getFont();

        g.drawImage(image, 0, 0, this);
        g.drawString(getX() + "," + getY() + " " + getWidth() + "," + getHeight(), 4, 14);

        if ((boolean) conf.get("transparent")) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
            g2d.setColor(getBackground());
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }

        g.setFont(new Font("Serif", Font.BOLD, 18));
        //g.drawString(getComponents().length + "", 16, 20);
        g.drawString("" + conf.get("title"), 16, getHeight() - 20);
        //g.drawString("" + conf.get("simpleName"), 16, getHeight() - 38);
        if ((boolean) conf.get("showLed")) {
            g.drawImage(image2, getWidth() - 20, 5, this);
            g.drawImage(image2, getWidth() - 20, 22, this);
            g.drawImage(image2, getWidth() - 20, 39, this);
        }
        g.setFont(oldf);
    }

    public void showCam() {
        if (webcam != null) {
            webcam.close();
            webcam=null;
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
            webcamPanel.setBounds(getX() + getWidth()+4, getY(), 320, 240);
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
