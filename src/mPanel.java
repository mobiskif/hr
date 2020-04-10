import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.InputStream;

public class mPanel extends JPanel implements MouseMotionListener {
    Image image, image2;
    int x0,y0;
    //int w,h;

    public mPanel(int x, int y, String bgrName) {
        super();
        setLayout(null);
        loadImages(bgrName);
        setLocation(x, y);
        addMouseMotionListener(this);

        add(new Athom(10,20));
        add(new Athom(50,75));

        setTransferHandler(new mTransferHandler(this));
        addMouseListener(new DragMouseAdapter());
    }


    void loadImages(String bgrName) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(bgrName);
            image = ImageIO.read(is);
            int w,h;
            //image = image.getScaledInstance(120, 160, Image.SCALE_SMOOTH);
            if (image!=null) {
                w = image.getWidth(this);
                h = image.getHeight(this);
            }
            else {w=120; h=160;}
            setPreferredSize(new Dimension(w, h));
            setSize(w,h);
        }
        catch (IOException e) { e.printStackTrace(); }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
        g.drawRect(1,1,getWidth()-2,getHeight()-2);
        g.drawString(getWidth()+","+getHeight(),16,16);
        g.drawString(getWidth()+","+getHeight(),16,32);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
        g2d.setColor(getBackground());
        g2d.fillRect( 0, 0, getWidth(), getHeight() );
        g2d.dispose();

        g.drawString(getWidth()+","+getHeight(),16,16);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x1,y1,dx,dy;
        x1 = e.getX(); y1 = e.getY();
        dx = x1 - x0; dy = y1 - y0;
        Component n = (Component) e.getSource();
        n.setLocation(n.getX() + dx, n.getY() + dy);
        n.getParent().repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x0 = e.getX();
        y0 = e.getY();
    }

    protected class DragMouseAdapter extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if (e.getModifiers()==18 || e.getModifiers()==17) {
                JComponent c = (JComponent) e.getSource();
                TransferHandler handler = c.getTransferHandler();
                if (handler != null) handler.exportAsDrag(c, e, TransferHandler.COPY);
                else System.out.println("null handler "+getDropTarget());
            }
            else super.mousePressed(e);
        }

    }

}
