package stepdefs;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import utils.Util;

import static com.jayway.restassured.RestAssured.given;

public class IbanSteps {
    private Response response;
    private RequestSpecification request;

    @Given("^endpoint for IBAN \"([^\"]*)\" from bank \"([^\"]*)\"$")
    public void endpoint_for_IBAN_from_bank(String iban, String bank) throws Throwable {
        request = given().pathParam("bank", bank).pathParam("iban", iban);

    }

    @Given("^endpoint for IBAN \"([^\"]*)\" from bank fidor$")
    public void endpoint_for_IBAN_from_bank_fidor(String iban) throws Throwable {
        request = given().pathParam("iban", iban);

    }

    @When("^IBAN data are received for fidor bank$")
    public void iban_data_are_received_for_fidor_bank() throws Throwable {
        response = request.when().get(Util.getLocalBaseUri() +  "fidor/account/{iban}" );

    }

    @Then("^the status code from IBAN endpoint is (\\d+)$")
    public void the_status_code_from_IBAN_endpoint_is(int statusCode) throws Throwable {
        response.then().statusCode(statusCode);
    }
}
