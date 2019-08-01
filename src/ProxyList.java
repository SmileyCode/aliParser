import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProxyList {

    private List<String> proxyList;

    public ProxyList(String proxyPath) {
        this.proxyList = readFromFile(proxyPath);
        Collections.shuffle(this.proxyList);
    }

    public List<String> readFromFile(String fileName){
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            List<String> list = new ArrayList<>();
            String line = "";
            while((line = reader.readLine()) != null){
                list.add(line);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getHost(){
        return this.proxyList.get(0).split(":")[0];
    }

    public int getPort(){
        return Integer.parseInt(this.proxyList.get(0).split(":")[1]);
    }

    public void next(){
        Collections.rotate(this.proxyList,1);
    }
}
