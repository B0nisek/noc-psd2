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
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;

public class AccountSteps {
    private Response response;
    private RequestSpecification request;
    private ValidatableResponse json;

    @Given("^endpoint for user \"([^\"]*)\" from bank fidor$")
    public void endpoint_for_user_from_bank_fidor(String customer) throws Throwable {
        request = given().pathParam("customer", customer);
    }

    @When("^account data are received for fidor bank$")
    public void account_data_are_received_for_fidor_bank() throws Throwable {
        response = request.when().get(Util.getLocalBaseUri() +  "fidor/customer/{customer}" );
    }

    @Then("^the status code from account endpoint is (\\d+)$")
    public void the_status_code_is(int statusCode) throws Throwable {
        response.then().statusCode(statusCode);
    }

    @Then("^body in account contains \"([^\"]*)\"$")
    public void body_contains(String bodyString) throws Throwable {
        Assert.assertThat(response.asString(), CoreMatchers.containsString(bodyString));
    }

    @Then("^body in account contains is_verified section with value$")
    public void body_in_account_contains_is_verified_section_with_value() throws Throwable {
        response.then().body("is_verified", not(isEmptyString()));
    }
}
