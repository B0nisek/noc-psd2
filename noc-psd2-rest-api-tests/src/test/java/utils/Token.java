package utils;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import static com.jayway.restassured.RestAssured.given;

public class Token {
    private Response response;
    private RequestSpecification request;
    private String value;
    private static Token token;

    public static Token getTokenInstance() {
        if(token == null) {
            token = new Token();
        }
        return token;
    }

    private Token() {
        adjustToken();
    }

    private void adjustToken() {
        request =  given().body(Util.getBasicUserForAutorizationAsJson());
        response = request.when().contentType(ContentType.JSON).post(Util.getAutorizationUrl());
        value = response.path("token");
    }

    public String getValue() {
        return value;
    }
}
