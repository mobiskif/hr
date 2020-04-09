import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class mTransferHandler extends TransferHandler {
    JComponent parent;

    public mTransferHandler(JComponent parent) {
        super();
        this.parent = parent;
    }

    @Override
    public int getSourceActions(JComponent c) {
        //return super.getSourceActions(c);
        return TransferHandler.COPY_OR_MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        //return super.createTransferable(c);
        JComponent tc = c;
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
                //return null;
                return tc.toString();
            }
        };
        return t;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        //super.exportDone(source, data, action);
        System.out.println("export source: "+source);
        try {
            System.out.println("export data: "+data.getTransferData(DataFlavor.stringFlavor));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (action == TransferHandler.MOVE) {
            System.out.println("MOVE");
            Container parent = source.getParent();
            parent.remove(source);
            parent.repaint();
        }
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
        //return super.importData(comp, t);
        Point point = support.getDropLocation().getDropPoint();
        //System.out.println("import location: " + support.getDropLocation());
        try {
            System.out.println("import data: "+support.getTransferable().getTransferData(DataFlavor.stringFlavor));
        } catch (Exception e) {
            e.printStackTrace();
        }
        parent.add(new Athom(point.x, point.y));
        parent.repaint();
        return true;
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        //return super.canImport(comp, transferFlavors);
        //System.out.println("canImport: " + support.getSourceDropActions());
        return true;
    }

}
