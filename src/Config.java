import java.io.Serializable;
import java.util.Hashtable;

public class Config implements Serializable {
    public boolean transparent = false;
    public boolean showled = true;
    String title, simpleName;
    Hashtable hashtable = new Hashtable();
}
