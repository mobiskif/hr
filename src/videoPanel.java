import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class videoPanel extends JPanel {
    JTextArea area = new JTextArea();
    Webcam webcam = null;
    WebcamPanel webcamPanel = null;

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

    public videoPanel() {
        super();
        setLayout(new BorderLayout());
        setSize(180,120);

        add(area);
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

    }
}
