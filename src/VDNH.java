public class VDNH extends mComponent{
    public VDNH() {
        super(0, 0, "res/vdnh.jpg");
        config.title = "HR LIVE";
        config.transparent=true;
        config.showled=false;

        mComponent panel;

        panel = new mComponent(30, 50, "res/vd.jpg");
        panel.config.title = "Газпром";
        panel.config.transparent = false;
        add(panel);

        panel = new mComponent(100, 150, "res/vd.jpg");
        panel.config.title = "РЖД";
        add(panel);

        panel = new mComponent(20, 200, "res/vd.jpg");
        panel.config.title = "Магнитф";
        add(panel);

        add(new Athom(200, 20));
    }
}
