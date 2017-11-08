package utils;
import com.jayway.restassured.response.Header;
import org.json.simple.JSONObject;

public class Util {

    public static JSONObject getBasicUserForAutorizationAsJson() {
        JSONObject person = new JSONObject();
        person.put("username", "johndoe");
        person.put("password", "12345");
        return  person;
    }

    public static String getAllTransactionUrl() {
        return Util.getLocalBaseUri() + "psd/acc";
    }

    public static String getAutorizationUrl() {
        return Util.getLocalBaseUri() + "psd/login";
    }

    public static String getLocalBaseUri(){
        return "http://localhost:8080/";
    }

    public static Header getAutorizationHeaderBasicUserHeader() {
        return new Header("auth-token", Token.getTokenInstance().getValue());
    }

}
