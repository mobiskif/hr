public class VDNH extends mPanel {
    public VDNH() {
        super(0, 0, "res/vdnh.jpg");
        add(new mPanel(30, 50, "res/vd.jpg"));
        add(new mPanel(100, 150, "res/vd.jpg"));
        add(new Athom(20, 200));
    }
}
