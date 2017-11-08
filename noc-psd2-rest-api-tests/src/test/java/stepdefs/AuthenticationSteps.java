package stepdefs;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import utils.Util;

import static com.jayway.restassured.RestAssured.given;

public class AuthenticationSteps {

    private Response response;
    private RequestSpecification request;
    private ValidatableResponse json;

    @Given("^Authorize as regular user$")
    public void authorize_as_regular_user() throws Throwable {
        request = given().body(Util.getBasicUserForAutorizationAsJson());
    }

    @When("^a user retrieves the response$")
    public void a_user_retrieves_the_response() throws Throwable {
        response = request.when().post(Util.getAutorizationUrl());
    }

    @Then("^the status code is (\\d+)$")
    public void the_status_code_is(int statusCode) throws Throwable {
        json = response.then().contentType(ContentType.JSON).statusCode(statusCode);
    }

    @Then("^response includes the token")
    public void response_includes_the_sessionId() throws Throwable {
        Assert.assertTrue(StringUtils.isNotBlank((CharSequence) response.path("token")));
        Assert.assertThat(response.asString(), CoreMatchers.containsString("token"));
    }

}
