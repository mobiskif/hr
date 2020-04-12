import java.awt.*;
import java.util.ArrayList;

public class VDNH extends mComponent{
    public VDNH() {
        super(0, 0, "res/map.png");
        conf.put("title","Санкт-Петербург");
        conf.put("transparent",false);
        conf.put("showLed",false);

        mComponent panel;

        panel = new mComponent(30, 50, "res/vd6.jpg");
        panel.conf.put("title","Газпром");
        //add(panel);

        panel = new mComponent(100, 150, "res/vd7.jpg");
        panel.conf.put("title","РЖД");
        //add(panel);

        panel = new mComponent(20, 200, "res/vd8.jpg");
        panel.conf.put("title","Сбербанк");
        //add(panel);

        add(new Athom(30, 300));
        add(new Sphere(560, 370));
        add(new Sphere(350, 250));
        add(new Sphere(200, 200));
        add(new Sphere(680, 230));
        add(new Sphere(300, 500));
    }
}
