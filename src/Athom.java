import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.InputStream;

public class Athom extends Component implements Runnable, MouseMotionListener {
    private Image image, image2;
    private boolean isStarted=false;
    int x0,y0;

    public Athom(int x, int y) {
        super();
        setLocation(x, y);
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("res/puzyr.png");
            image = ImageIO.read(is);
            int diameter = 60;
            image = image.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);
            setPreferredSize(new Dimension(diameter, diameter));
            setSize(diameter, diameter);

            is = getClass().getClassLoader().getResourceAsStream("res/athom.png");
            image2 = ImageIO.read(is);
            diameter = 15;
            image2 = image2.getScaledInstance(diameter, diameter, Image.SCALE_SMOOTH);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //new Thread(this).start();
        //addActionListener(actionEvent -> System.out.println("---" + actionEvent));

        addMouseMotionListener(this);

        Athom a=this;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                isStarted=!isStarted;
                if (isStarted) new Thread(a).start();
            }
        });


    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, 0, 0, this);
        g.drawString("" + getX() + "," + getY(),  8, getHeight() / 2+6);
        g.drawImage(image2, getWidth()-20, getHeight()-20, this);
        g.drawImage(image2, getWidth()-20, getHeight()-37, this);

    }

    @Override
    public void run() {
        while (isStarted) {
            //Dimension dXY = model.calculateDeltaXY(this);
            //setLocation(dXY.width, dXY.height);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getParent().repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x1,y1,dx,dy;
        x1 = e.getX(); y1 = e.getY();
        dx = x1 - x0; dy = y1 - y0;
        Athom n = (Athom) e.getSource();
        n.setBounds(n.getX() + dx, n.getY() + dy, n.getWidth(), n.getHeight());
        n.getParent().repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x0 = e.getX();
        y0 = e.getY();
    }
}
