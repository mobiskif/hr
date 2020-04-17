import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class parentPanel extends JPanel {
    mTableModel model;
    Image image;
    helperPanel helper = new helperPanel();
    videoPanel video = new videoPanel();
    workerPanel worker = new workerPanel();

    public parentPanel() {
        Dimension dimension = new Dimension(500, 400);
        setPreferredSize(dimension); //нужен для майнформ
        model = new mTableModel(dimension);
        //setLayout(new FlowLayout());
        setLayout(null);
        loadImages();
        add(helper);
        add(video);
        video.setLocation(getWidth()-video.getWidth(),0);

        String[] testdata = {"123", "Рабочий", "59.91", "30.32", "50000", "60000", "Газпром"};
        add(new childPanel(testdata, dimension, helper));

        //workerPanel worker = new workerPanel();
        add(worker);
        setComponentZOrder(worker,0);

        setTransferHandler(new workerTransferHandler(this));

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
        add(video);
        add(helper);
        setComponentZOrder(helper,1);

        add(worker);
        setComponentZOrder(worker,0);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,0,0,this);
        g.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
        g.drawString(getWidth()+","+getHeight(), getWidth()-60, getHeight()-14);
    }

}
