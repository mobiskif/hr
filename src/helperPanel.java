import javafx.scene.layout.Border;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class helperPanel extends JPanel {
    JTextArea area = new JTextArea();
    boolean fixed = false;

    Image image, ledImage;

    void loadImages() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/vd6.jpg"));
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
        repaint();

    }

    public helperPanel() {
        super();
        //setLayout(new BorderLayout());
        setLayout(null);
        setSize(180, 120);
        setLocation(300, 300);
        setVisible(false);
        loadImages();

        area.setBounds(14, 10, getWidth() / 3, getHeight() - 14);
        area.setOpaque(false);

        add(area);

        setTransferHandler(new workerTransferHandler(this));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ((parentPanel) getParent()).video.offCam();
                /*
                fixed=!fixed;
                System.out.println(fixed);
                if (fixed) {
                    //area.setBorder(BorderFactory.createEtchedBorder());
                    area.setOpaque(false);
                    area.setForeground(Color.WHITE);
                    //((parentPanel)getParent()).video.showCam();
                }
                else {
                    ((parentPanel)getParent()).video.offCam();
                    //area.setBorder(BorderFactory.createEmptyBorder());
                    area.setOpaque(true);
                    area.setForeground(Color.BLACK);
                }

                 */
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                //if(!fixed) setVisible(false);
            }
        });

    }

    public void setData(String[] data) {
        area.setText("");
        for (int i = 0; i < data.length; i++) area.append(data[i] + "\n");
        getParent().repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
        g.drawImage(ledImage, getWidth() - 20, 5, this);
        area.setFont(new Font("Sans Serif", Font.PLAIN, 13));
        area.setForeground(Color.BLACK);
        if (!fixed) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.65f));
            g2d.setColor(getBackground());
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        } else {
            area.setForeground(Color.WHITE);
            area.setFont(new Font("Sans Serif", Font.BOLD, 13));
            g.drawImage(ledImage, getWidth() - 20, 5, this);
            g.drawImage(ledImage, getWidth() - 20, 22, this);
            g.drawImage(ledImage, getWidth() - 20, 39, this);
        }


    }
}
