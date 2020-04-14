import javax.swing.*;
import java.awt.*;

public class VDNH extends mComponent {
    mTableModel model;

    public VDNH() {
        super(0, 0);
        setLayout(null);
        conf.put("salary", "");
        conf.put("transparent", true);
        conf.put("showLed", false);
        conf.put("bigImgName", "res/map2.png");
        conf.put("smallImgName", "res/map2.png");
        conf.put("employer", "Санкт-Петербург");
        conf.put("title", "Вакансии");
        loadImages();

        //refresh("SQL");
        prepareWorker("IT");
    }

    double minLat = 59.84;
    double maxLat = 60.1;
    double minLng = 30.31;
    double maxLng = 30.35;


    public int calcX(String lat) {
        return (int) (getWidth() * (Double.valueOf(lat) - minLat) / (maxLat - minLat));
    }

    public int calcY(String lng) {
        return (int) (getHeight() * (Double.valueOf(lng) - minLng) / (maxLng - minLng));
    }


    public void refresh(String text) {
        removeAll();
        model = new mTableModel(text);
        if (model!=null) {
            for (int i = 0; i < model.adata.size(); i++) {
                int x = calcX(model.adata.get(i)[2]);
                int y = calcY(model.adata.get(i)[3]);
                Company company = new Company(x, y);
                company.conf.put("title", model.adata.get(i)[1]);
                company.conf.put("salary", model.adata.get(i)[4] + ".." + model.adata.get(i)[5]);
                company.conf.put("employer", model.adata.get(i)[6]);
                add(company);
            }
        }

        prepareWorker(text);
    }

    void prepareWorker(String text){
        Worker worker = new Worker(50, getHeight() - 200);
        worker.conf.put("salary", text);
        worker.conf.put("title", "магистр");
        worker.conf.put("employer", "Закиль Малкин");
        add(worker);
        repaint();
    }

}
