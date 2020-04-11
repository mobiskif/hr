public class VDNH extends mPanel {
    public VDNH() {
        super(0, 0, "res/vdnh.jpg");
        config.title = "HR LIVE";

        mPanel panel;

        panel = new mPanel(30, 50, "res/vd.jpg");
        panel.config.title = "Газпром";
        add(panel);

        panel = new mPanel(100, 150, "res/vd.jpg");
        panel.config.title = "РЖД";
        add(panel);

        panel = new mPanel(20, 200, "res/vd.jpg");
        panel.config.title = "Магнитф";
        add(panel);

        add(new Athom(200, 20));
    }
}
