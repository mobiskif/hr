import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class workerTransferHandler extends TransferHandler implements Serializable {
    final JComponent reciver;

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
        return new Transferable() {
            @Override
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[0];
            }

            @Override
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return false;
            }

            @Override
            public Object getTransferData(DataFlavor flavor) {
                try {
                    FileOutputStream fos;
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
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        if (action == TransferHandler.MOVE) {
            System.out.println("MOVE");
            Container parent = source.getParent();
            parent.remove(source);
            parent.repaint();
            if (parent.getClass().getSimpleName().contains("mapPanel")) ((mapPanel) parent).video.showCam();
            if (parent.getClass().getSimpleName().contains("helperPanel")) {
                ((mapPanel) parent.getParent()).video.offCam();
                ((helperPanel) parent).fixed=false;
            }
        }
    }

    @Override
    public boolean importData(TransferSupport support) {
        Point point;
        try {
            point = support.getDropLocation().getDropPoint();
            //Object transferedObj = support.getTransferable().getTransferData(DataFlavor.stringFlavor);
            //System.out.println(point);
            basePanel migrant = new basePanel("worker.png", point.x, point.y);
            migrant.fixed=true;
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
