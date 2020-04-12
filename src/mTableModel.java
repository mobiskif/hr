import javax.swing.table.AbstractTableModel;


public class mTableModel extends AbstractTableModel {
    @Override
    public int getRowCount() {
        return 10;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowIndex+" "+columnIndex;
    }
}
