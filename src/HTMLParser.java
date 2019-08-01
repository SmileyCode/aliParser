import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class HTMLParser {

    private String USER_AGENT;

    public HTMLParser() {
        setUSER_AGENT("Mozilla/5.0");
    }


    public HTMLParser(String USER_AGENT) {
        setUSER_AGENT(USER_AGENT);
    }

    public String getUSER_AGENT() {
        return USER_AGENT;
    }

    public void setUSER_AGENT(String USER_AGENT) {
        this.USER_AGENT = USER_AGENT;
    }

    // HTTP GET request
    public String sendGet(int countItems, int phase, ProxyList proxyList) throws Exception {
        if(countItems > 45) countItems = 45;

        java.net.URL obj = new URL("https://gpsfront.aliexpress.com/queryGpsProductAjax.do?widget_id=5547572&platform=pc&limit=" + countItems + "&offset=0&phase=" + phase + "&productIds2Top=&postback=&_=1564562976188");

        Proxy proxy;
        HttpURLConnection con;
        StringBuffer response = new StringBuffer();

        while (!response.toString().contains("sellerId")){
            try {
                proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxyList.getHost(), proxyList.getPort()));
                con = (HttpURLConnection) obj.openConnection(proxy);

                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", getUSER_AGENT());

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;


                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                proxyList.next();
            }
            catch (Exception e){
                proxyList.next();
            }
        }

        return response.toString();

    }

}
