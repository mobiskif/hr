import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Worker extends mComponent {
    public Worker(int x, int y) {
        super(x,y);
        conf.put("showLed", true);
        conf.put("bigImgName","res/small3.png");
        conf.put("smallImgName","res/small3.png");
        conf.put("salary","SQL");
        loadImages();
    }
}
