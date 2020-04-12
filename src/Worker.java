import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Worker extends mComponent {

    public Worker(int x, int y) {
        super(x,y,"res/small3.png");
        conf.put("title","Йехезкель");

        Worker a=this;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //if (isStarted) new Thread(a).start();
            }
        });
    }
}
