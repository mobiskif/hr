import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class objectsPanel extends JPanel {
    private Image image;
    int w,h;

    public objectsPanel() {
        setLayout(null);
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("res/vdnh.jpg");
            image = ImageIO.read(is);
            image = image.getScaledInstance(240, 320, Image.SCALE_SMOOTH);
            w=image.getWidth(this);
            h=image.getHeight(this);
            setPreferredSize(new Dimension(w, h));
            setSize(w,h);
        } catch (IOException e) {
            e.printStackTrace();
        }
        add(new Athom(50,50));
        add(new Athom(150,150));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
        g.drawRect(1,1,getWidth()-2,getHeight()-2);
        g.drawString(getWidth()+","+getHeight(),16,16);
        g.drawString(w+","+h,16,32);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
        g2d.setColor(getBackground());
        g2d.fillRect( 0, 0, getWidth(), getHeight() );
        g2d.dispose();

        g.drawString(getWidth()+","+getHeight(),16,16);
    }
}
