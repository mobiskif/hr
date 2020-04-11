import java.awt.*;
import java.util.ArrayList;

public class VDNH extends mComponent{
    public VDNH() {
        super(0, 0, "res/vdnh.jpg");
        conf.put("title","HR LIVE");
        conf.put("transparent",true);
        conf.put("showLed",false);

        mComponent panel;

        panel = new mComponent(30, 50, "res/vd.jpg");
        panel.conf.put("title","Газпром");
        add(panel);

        panel = new mComponent(100, 150, "res/vd.jpg");
        panel.conf.put("title","РЖД");
        add(panel);

        panel = new mComponent(20, 200, "res/vd.jpg");
        panel.conf.put("title","Магнит");
        add(panel);

        add(new Athom(200, 20));
    }
}
