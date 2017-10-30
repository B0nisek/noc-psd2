package stepdefs;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import utils.Util;

import static com.jayway.restassured.RestAssured.given;

public class AccountSteps {
    private Response response;
    private RequestSpecification request;
    private ValidatableResponse json;

    @Given("^endpoint for user \"([^\"]*)\" from bank \"([^\"]*)\"$")
    public void existed_user_from_bank( String customer, String bank) throws Throwable {
        request = given().pathParam("bank", bank).pathParam("customer", customer);
    }

    @When("^data are received from server$")
    public void data_are_recieved_from_server() throws Throwable {
        response = request.when().get(Util.getLocalBaseUri() +  "{bank}/customer/{customer}" );
        System.out.println("pozri tu response: " + response.prettyPrint());
    }

    @Then("^the status code is (\\d+)$")
    public void the_status_code_is(int statusCode) throws Throwable {
        json = response.then().statusCode(statusCode);
    }

    @Then("^body contains \"([^\"]*)\"$")
    public void body_contains(String bodyString) throws Throwable {
        Assert.assertThat(response.asString(), CoreMatchers.containsString(bodyString));
    }

}
