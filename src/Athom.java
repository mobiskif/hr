import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Athom extends mComponent {

    public Athom(int x, int y) {
        super(x,y,"res/small2.png");
        config.transparent=false;

        Athom a=this;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //if (isStarted) new Thread(a).start();
            }
        });
    }
}
