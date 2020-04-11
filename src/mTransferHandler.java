import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;
import java.util.HashMap;

public class mTransferHandler extends TransferHandler implements Serializable {
    mComponent component;

    public mTransferHandler(mComponent comp) {
        super();
        this.component = comp;
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
                component.conf.put("components", component.getComponents());
                try {
                    FileOutputStream fos = null;
                    fos = new FileOutputStream("temp.out");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(component.conf);
                    oos.flush();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return component.conf;
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
            HashMap data = (HashMap) support.getTransferable().getTransferData(DataFlavor.stringFlavor);

            mComponent new_comp = null;
            if (data.get("simpleName").toString().contains("Athom")) new_comp = new Athom(point.x, point.y);
            else if (data.get("simpleName").toString().contains("mComponent")) new_comp = new mComponent(point.x, point.y, "res/vd.jpg");
            else if (data.get("simpleName").toString().contains("VDNH")) new_comp = new mComponent(point.x, point.y, "res/vdnh.jpg");

            Component[] comp = (Component[]) data.get("components");
            System.out.println(comp.length);
            if (comp!=null) for (Component c : comp) {
                HashMap cnf = ((mComponent) c).conf;
                mComponent nc = new mComponent(point.x, point.y, "res/vd.jpg");
                nc.conf=cnf;
                new_comp.add(nc);
            }

            new_comp.conf = data;
            component.add(new_comp);
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
