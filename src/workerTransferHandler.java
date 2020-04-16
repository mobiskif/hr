import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

public class workerTransferHandler extends TransferHandler implements Serializable {
    JComponent reciver;

    public workerTransferHandler(JComponent panel) {
        super();
        this.reciver = panel;
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
                try {
                    FileOutputStream fos = null;
                    fos = new FileOutputStream("temp.out");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(reciver);
                    oos.flush();
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return reciver;
            }
        };
        return t;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        if (action == TransferHandler.MOVE) {
            System.out.println("MOVE");
            Container parent = source.getParent();
            parent.remove(source);
            parent.repaint();
            ((parentPanel) parent).video.showCam();
        }
        //((parentPanel) source.getParent()).video.showCam();
    }

    @Override
    public boolean importData(TransferSupport support) {
        Point point;
        try {
            point = support.getDropLocation().getDropPoint();
            workerPanel migrant = new workerPanel();
            //migrant = (workerPanel) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
            migrant.setLocation(point);
            reciver.add(migrant);

            reciver.repaint();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean canImport(TransferSupport support) {
        return true;
    }

}
