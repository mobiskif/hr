import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class videoPanel extends JPanel {
    int x0, y0;
    JTextArea area = new JTextArea();
    Webcam webcam = null;
    WebcamPanel webcamPanel = null;

    public void showCam() {
        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setFPSDisplayed(true);
        //panel.setDisplayDebugInfo(true);
        webcamPanel.setImageSizeDisplayed(true);
        webcamPanel.setMirrored(true);
        webcamPanel.setVisible(true);
        webcamPanel.setBounds(0, 0, getWidth(), getHeight());
        add(webcamPanel);
        /*
        webcamPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                webcamPanel.setSize(320, 240);
                super.mouseClicked(e);
            }
        });

         */

        setVisible(true);
    }

    public void offCam() {
        setVisible(false);
        if (webcam != null) {
            webcam.close();
            webcam = null;
            getParent().remove(webcamPanel);
            getParent().repaint();
        }
    }

    public videoPanel() {
        super();
        setLayout(new BorderLayout());
        setSize(236, 157);
        setVisible(false);
        //add(area);
        area.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        });
        //showCam();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (webcam!=null) {
                    setSize(320,240);
                    webcamPanel.setSize(320, 240);
                }
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
}
