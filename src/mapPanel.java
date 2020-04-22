import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class mapPanel extends basePanel {
    final mTableModel model;
    final helperPanel helper = new helperPanel();
    final videoPanel video = new videoPanel();
    final basePanel worker = new basePanel("worker.png",30,300);

    public mapPanel() {
        super("map.png",0,0);
        model = new mTableModel(new Dimension(getWidth(),getHeight()));
        fixed=true;
        add(helper);
        add(video);

        String[] testdata = {"123", "Рабочий", "59.91", "30.32", "50000", "60000", "Газпром"};
        add(new childPanel(testdata, new Dimension(getWidth(),getHeight()), helper));

        worker.fixed=true;
        add(worker);
        setComponentZOrder(worker,0);
    }

    public void queryAPI(String text) {
        Dimension dimension = new Dimension(getWidth(), getHeight());
        model.queryAPI(text);
        removeAll();
        for (String[] row : model.adata) add(new childPanel(row, dimension, helper));
        add(video);

        add(worker);
        setComponentZOrder(worker,0);

        add(helper);
        setComponentZOrder(helper,1);

        repaint();
    }
}
