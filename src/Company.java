import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;

public class Company extends mComponent {
    public Company(int x, int y) {
        super(x, y);
        conf.put("title", "");
        conf.put("showLed", false);
        conf.put("bigImgName","res/office.png");
        loadImages();
        W=320;
    }
}
