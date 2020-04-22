import javax.json.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class mTableModel extends AbstractTableModel {
    final ArrayList<String[]> adata = new ArrayList<>();
    String[] headers = {"id","name", "lat", "lng", "from", "to", "employer"};
    final Dimension dimension;
    final String[][] data = {
            {"123", "Рабочий", "59.91", "30.32", "50000", "60000", "Газпром"},
            {"345", "Дворник", "59.88", "30.33", "50000", "60000", "РЖД"},
    };

    public mTableModel(Dimension d) {
        dimension = d;
        adata.add(data[0]);
        adata.add(data[1]);
        //this.queryAPI("Java");
    }

    void appendData(JsonArray res ) {
        for (JsonObject result : res.getValuesAs(JsonObject.class)) {
            String[] row;
            row = new String[9];
            row[0]=result.getJsonString("id").toString();
            row[1]=result.getJsonString("name").toString();
            //System.out.println("="+result.get("address").toString());
            if (!result.get("address").toString().startsWith("null")) {
                row[2]= result.getJsonObject("address").get("lat").toString();
                row[3]= result.getJsonObject("address").get("lng").toString();
                row[6]= result.getJsonObject("address").get("street").toString() + " " + result.getJsonObject("address").get("building").toString();
                if (!result.getJsonObject("address").get("metro").toString().contains("null")) row[7]= result.getJsonObject("address").getJsonObject("metro").get("station_name").toString();
            } else {row[2]= ""; row[3]= "";}
            if (!result.get("salary").toString().startsWith("null")) {
                row[4]= result.getJsonObject("salary").get("from").toString()+".."+result.getJsonObject("salary").get("to").toString();
            } else row[4]= "не указана";
            row[5]= result.getJsonObject("employer").get("name").toString();
            if (row[2].length()>5) adata.add(row);
        }
    }

    void queryAPI(String str) {
        adata.clear();
        try {
            str = URLEncoder.encode(str, "UTF-8");
            str="https://api.hh.ru/vacancies?area=2&only_with_salary=true&salary=100000&text="+str;
            //URL url = new URL("https://api.hh.ru/employers?area=2&clusters=true&enable_snippets=true&no_magic=true&text=Директор&showClusters=false");
            URL url = new URL(str);
            InputStream is = url.openStream();
            JsonReader rdr = Json.createReader(is);
            JsonObject obj = rdr.readObject();
            JsonNumber pages = obj.getJsonNumber("pages");
            JsonArray results = obj.getJsonArray("items");
            appendData(results);
            for (int i = 1; i < 3 ; i++) {
                url = new URL(str+"&page="+i);
                InputStream is1 = url.openStream();
                JsonReader rdr1 = Json.createReader(is1);
                JsonObject obj1 = rdr1.readObject();
                JsonNumber pages1 = obj1.getJsonNumber("pages");
                JsonArray results1 = obj1.getJsonArray("items");
                appendData(results1);
            }
        }
        catch (IOException e) {e.printStackTrace();}
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
