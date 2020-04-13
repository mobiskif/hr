import javax.json.*;
import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class mTableModel extends AbstractTableModel {
    ArrayList<String[]> adata = new ArrayList<String[]>();
    String query = "https://api.hh.ru/vacancies?text=IT&area=2&metro=15";

    public mTableModel(String text) {
        this.refresh(text);
    }

    void refresh(String str) {
        try {
            str = URLEncoder.encode(str, "UTF-8");
            query="https://api.hh.ru/vacancies?area=2&metro=15&text="+str;
            //URL url = new URL("https://api.hh.ru/employers?area=2&clusters=true&enable_snippets=true&no_magic=true&text=Директор&showClusters=false");
            URL url = new URL(query);
            InputStream is = url.openStream();
            JsonReader rdr = Json.createReader(is);
            JsonObject obj = rdr.readObject();
            JsonArray results = obj.getJsonArray("items");
            for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                String[] row;
                row = new String[7];
                row[0]=result.getJsonString("id").toString();
                row[1]=result.getJsonString("name").toString();
                row[2]= result.getJsonObject("address").get("lat").toString();
                row[3]= result.getJsonObject("address").get("lng").toString();
                if (row[2].length()>5) {
                    row[2] = String.valueOf((int) (-599000 +Double.valueOf(row[2])*10000));
                    row[3] = String.valueOf((int) (-303000 + Double.valueOf(row[3])*10000));
                    adata.add(row);
                }
                if (!result.get("salary").toString().contains("null")) {
                    row[4]= result.getJsonObject("salary").get("from").toString();
                    row[5]= result.getJsonObject("salary").get("to").toString();
                }
                else {row[4]= ""; row[5]= "";}
                row[6]= result.getJsonObject("employer").get("name").toString();
            }
        }
        catch (IOException e) {e.printStackTrace();}
        System.out.println("Ready");
    }

    @Override
    public int getRowCount() {
        return adata.size();
    }

    @Override
    public int getColumnCount() {
        return adata.get(0).length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return (adata.get(rowIndex))[columnIndex];
    }

}
