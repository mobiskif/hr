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
            public JComponent getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
                try {
                    FileOutputStream fos = null;
                    fos = new FileOutputStream("temp.out");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(c);
                    oos.flush();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
            Object data = support.getTransferable().getTransferData(DataFlavor.stringFlavor);
            String str = data.toString();
            if (str.contains("Athom")) component.add(new Athom(point.x, point.y));
            else if (str.contains("mPanel")) component.add(new mPanel(point.x, point.y, "res/vd.jpg"));
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
