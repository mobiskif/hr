import javax.swing.*;
import java.awt.*;

public class VDNH extends mComponent {
    mTableModel model;

    public VDNH() {
        super(0, 0);
        conf.put("salary", "Вакансии");
        conf.put("transparent", true);
        conf.put("showLed", false);
        conf.put("bigImgName", "res/map2.png");
        conf.put("smallImgName", "res/map2.png");
        loadImages();

        refresh("SQL");
    }

    public void refresh(String text) {
        removeAll();
        model = new mTableModel(text);
        for (int i = 0; i < model.adata.size(); i++) {
            Company company = new Company(Integer.valueOf(model.adata.get(i)[2]), Integer.valueOf(model.adata.get(i)[3]));
            company.conf.put("title", model.adata.get(i)[1]);
            company.conf.put("salary", model.adata.get(i)[4]+".."+model.adata.get(i)[5]);
            company.conf.put("employer", model.adata.get(i)[6]);
            add(company);
        }
        Worker worker = new Worker(50, getHeight() - 200);
        worker.conf.put("title", text);
        add(worker);
        repaint();
    }

}
