import javafx.scene.layout.Border;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class helperPanel extends JPanel {
    JTextArea area = new JTextArea();
    boolean fixed=false;

    Image image;
    void loadImages() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/vd6.jpg"));
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
        setSize(180,120);
        setLocation(300,300);
        setVisible(false);
        loadImages();

        area.setBounds(0,0,getWidth()/2,getHeight());
        area.setOpaque(false);
        add(area);

        setTransferHandler(new workerTransferHandler(this));
        //setTransferHandler(new workerTransferHandler(area));


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ((parentPanel)getParent()).video.offCam();
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
        for (int i = 0; i < data.length ; i++) area.append(data[i]+"\n");
        getParent().repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fixed) {
            area.setForeground(Color.WHITE);
            //g.drawRect(0,0,getWidth()-1,getHeight()-1);
            g.drawImage(image,0,0,this);
        }
        else area.setForeground(Color.BLACK);


    }
}
