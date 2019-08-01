import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSV {

    private String path;
    private String separator;

    public CSV(String[] columnNames, String separator, String path) {
        setPath(path);
        setSeparator(separator);
        makeCSV(columnNames);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public void makeCSV(String[] columnNames){
        try (FileWriter writer = new FileWriter(getPath())){
            for (int i = 0; i < columnNames.length-1; i++) {
                writer.append(columnNames[i]).append(getSeparator());
            }
            writer.append(columnNames[columnNames.length - 1]).append(System.lineSeparator());

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToCSV(Response response) {

        List<List<String>> values = response.getResponse();

        try (FileWriter writer = new FileWriter(getPath(),true)){

            for (List<String> value : values) {
                for (int j = 0; j < value.size() - 1; j++) {
                    writer.append(value.get(j)).append(getSeparator());
                }
                writer.append(value.get(value.size() - 1)).append(System.lineSeparator());
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
