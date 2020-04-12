import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;
import java.util.HashMap;

public class mTransferHandler extends TransferHandler implements Serializable {
    mComponent reciver;

    public mTransferHandler(mComponent comp) {
        super();
        this.reciver = comp;
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
                reciver.conf.put("components", reciver.getComponents());
                try {
                    FileOutputStream fos = null;
                    fos = new FileOutputStream("temp.out");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(reciver.conf);
                    oos.flush();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return reciver.conf;
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
        Point point;
        try {
            mComponent migrant = null;
            HashMap migrant_conf = (HashMap) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
            point = support.getDropLocation().getDropPoint();
            switch (migrant_conf.get("simpleName").toString()) {
                case "Athom":
                    migrant = new Athom(point.x, point.y);
                    break;
                case "mComponent":
                    migrant = new mComponent(point.x, point.y, "res/vd2.jpg");
                    break;
                case "VDNH":
                    migrant = new mComponent(point.x, point.y, "res/vdnh.jpg");
                    break;
            }

            Component[] migrant_childs = (Component[]) migrant_conf.get("components");
            if (migrant_childs != null) for (Component c : migrant_childs) {
                mComponent child = (mComponent) c;
                HashMap child_conf = child.conf;
                point = child.getLocation();
                switch (child.getClass().getSimpleName()) {
                    case "Athom":
                        child = new Athom(point.x, point.y);
                        break;
                    case "mComponent":
                        child = new mComponent(point.x, point.y, "res/vd2.jpg");
                        break;
                    case "VDNH":
                        child = new mComponent(point.x, point.y, "res/vdnh.jpg");
                        break;
                }
                child.conf = child_conf;
                migrant.add(child);
            }

            migrant.conf = migrant_conf;
            reciver.add(migrant);

            reciver.repaint();
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
