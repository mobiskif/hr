import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class parentPanel extends JPanel {
    mTableModel model;
    Image image;
    helperPanel helper;

    public parentPanel() {
        Dimension initdim = new Dimension(500, 400);
        setPreferredSize(initdim); //нужен для майнформ
        model = new mTableModel(initdim);
        //setLayout(new FlowLayout());
        setLayout(null);
        loadImages();
        helper=new helperPanel();
        //helper.setComponentZOrder(this,1);
        helper.setVisible(false);
    }

    void loadImages() {
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/map2.png"));
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

    public void queryAPI(String text) {
        Dimension dimension = new Dimension(getWidth(), getHeight());
        model.queryAPI(text);
        removeAll();
        for (String[] row : model.adata) add(new childPanel(row, dimension, helper));
        add(helper);
        setComponentZOrder(helper,0);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,0,0,this);
        g.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
    }

}
