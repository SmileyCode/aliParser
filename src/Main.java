import jdk.nashorn.internal.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();

        int needToParse = 100;//How many items need to parse
        String[] parsingParams = "sellerId,productImage,productDetailUrl,productDetailUrl,minPrice,maxPrice,oriMinPrice,oriMaxPrice,discount,orders,shopUrl".split(",");

        ProxyList proxyList = new ProxyList("C:\\Users\\user\\Downloads\\socks.txt");//Use only Socks proxy
        CSV csv = new CSV(parsingParams,";","C:\\work\\my.csv");

        int phase = 1;//Starting to parse from the 1 page
        while (needToParse > 0){
            Response response = parse(parsingParams, phase, needToParse, proxyList);
            csv.addToCSV(response);
            needToParse-=response.getResponse().size();
            phase++;
        }

        long timeSpent = System.currentTimeMillis() - startTime;
        System.out.println("Spent " + timeSpent + " ms");

    }

    public static Response parse(String[] parsingParams, int phase, int needToParse, ProxyList proxy) throws Exception {
        HTMLParser aliexpress = new HTMLParser();
        String result = aliexpress.sendGet(needToParse, phase, proxy);
        return new Response(result,parsingParams);
    }

}