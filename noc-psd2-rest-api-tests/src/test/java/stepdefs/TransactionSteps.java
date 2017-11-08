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

public class TransactionSteps {
    private Response response;
    private RequestSpecification request;
    private ValidatableResponse validatableResponse;

    @Given("^transaction endpoint for IBAN \"([^\"]*)\" for fidor bank$")
    public void transaction_endpoint_for_IBAN_for_fidor_bank(String iban) throws Throwable {
        request = given().pathParam("iban", iban);
    }

    @When("^transaction data are received for fidor bank$")
    public void transaction_data_are_received_from_server() throws Throwable {
        response = request.when().get(Util.getLocalBaseUri() +  "fidor/account/{iban}/transactions" );
    }

    @Then("^the status code from transaction endpoint is (\\d+)$")
    public void the_status_code_from_transaction_endpoint_is(int statusCode) throws Throwable {
        validatableResponse = response.then().statusCode(statusCode);

    }

    @Given("^valid token from basic user$")
    public void valid_token_from_basic_user() throws Throwable {
        request = given().header(Util.getAutorizationHeaderBasicUserHeader());

    }

    @When("^all transaction are recieved from server$")
    public void all_transaction_are_recieved_from_server() throws Throwable {
        response = request.when().get(Util.getAllTransactionUrl() );
    }

    @Then("^response contains \"([^\"]*)\"$")
    public void response_contains(String bodyString) throws Throwable {
        Assert.assertThat(response.asString(), CoreMatchers.containsString(bodyString));
     }
}
