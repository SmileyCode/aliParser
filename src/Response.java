import java.util.ArrayList;
import java.util.List;

public class Response {

    private String[] parsingParams;
    //private String[] rawResponse;
    private List<List<String>> response;

    public Response(String rawResponse) {
        this(rawResponse,"sellerId,productImage,productDetailUrl,productDetailUrl,minPrice,maxPrice,oriMinPrice,oriMaxPrice,discount,orders,shopUrl".split(","));
    }

    public Response(String rawResponse, String[] parsingParams) {
        setParsingParams(parsingParams);
        setResponse(rawResponse);
    }

    public String[] getParsingParams() {
        return parsingParams;
    }

    public void setParsingParams(String[] parsingParams) {
        this.parsingParams = parsingParams;
    }

    public List<List<String>> getResponse() {
        return response;
    }

    public void setResponse(List<List<String>> response) {
        this.response = response;
    }

    public void addToResponse(List<String> strings) {
        this.response.add(strings);
    }

    public void setResponse(String rawResponse){
        this.setResponse(new ArrayList<>());

        String[] rawResponseArr = getRawResponseArr(rawResponse);

        for (int i = 0; i < rawResponseArr.length; i++) {
            String[] temp = rawResponseArr[i].split(":");
            if(temp[0].contains("productId")){
                List<String> item = new ArrayList<>();
                item.add(temp[1]);                      //adding productId

                i++;
                temp = rawResponseArr[i].split(":");

                while (!temp[0].contains("gps-id")){ //gps-id is last param for each item
                    if (needToParse(temp[0])){      //adding other params needed to parse
                        item.add(temp[1]);
                    }
                    i++;
                    temp = rawResponseArr[i].split(":");
                }

                addToResponse(item);
            }
            else if(temp[0].contains("lastPage")) break;
        }
    }

    public String[] getRawResponseArr(String rawResponse) {
        rawResponse = rawResponse.substring(22);//remove {"gpsProductDetails":[
        String[] arr = rawResponse.split(",");
        return arr;
    }

    public boolean needToParse(String paramName){
        for (int i = 0; i < getParsingParams().length; i++) {
            if(paramName.contains(getParsingParams()[i])){
                return true;
            }
        }
        return false;
    }
}
