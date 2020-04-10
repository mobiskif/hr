import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;

public class mTransferHandler extends TransferHandler implements Serializable {
    JComponent component;

    public mTransferHandler(JComponent c) {
        super();
        this.component = c;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return TransferHandler.COPY_OR_MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        Transferable t = new Transferable() {
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[0];
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return false;
            }

            @Override
            public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                FileOutputStream fos = null;
                fos = new FileOutputStream("temp.out");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(c);
                oos.flush();
                oos.close();
                return c;
            }
        };
        return t;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        if (action == TransferHandler.MOVE) {
            Container parent = source.getParent();
            parent.remove(source);
            parent.repaint();
        }
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
        Point point = support.getDropLocation().getDropPoint();
        try {
            //System.out.println("imported: " + support.getTransferable().getTransferData(DataFlavor.stringFlavor));
            FileInputStream fis = new FileInputStream("temp.out");
            ObjectInputStream oin = new ObjectInputStream(fis);
            Athom ts =  (Athom) oin.readObject();
            System.out.println("to Object: " + ts);
            //component.add(new Athom(point.x, point.y));
            component.add(ts);
            component.repaint();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        return true;
    }

}
