package stepdefs;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import utils.Util;

import static com.jayway.restassured.RestAssured.given;

public class TransactionSteps {
    private Response response;
    private RequestSpecification request;
    private ValidatableResponse json;

    @Given("^transaction endpoint for IBAN \"([^\"]*)\" from bank \"([^\"]*)\"$")
    public void transaction_endpoint_for_IBAN_from_bank(String iban, String bank) throws Throwable {
        request = given().pathParam("bank", bank).pathParam("iban", iban);
    }

    @When("^transaction data are received from server$")
    public void transaction_data_are_received_from_server() throws Throwable {
        response = request.when().get(Util.getLocalBaseUri() +  "{bank}/account/{iban}/transactions" );
    }

    @Then("^the status code from transaction endpoint is (\\d+)$")
    public void the_status_code_from_transaction_endpoint_is(int statusCode) throws Throwable {
        json = response.then().statusCode(statusCode);

    }
}
