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

        add(new Worker(30, 300));
        add(new Company(560, 370));
        add(new Company(350, 250));
        add(new Company(200, 200));
        add(new Company(680, 230));
        add(new Company(300, 500));
    }
}
