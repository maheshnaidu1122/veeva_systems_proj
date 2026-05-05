package stepdefinitions;

import client.PetStoreClient;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import utils.TestContext;

public class PetSteps {

    private static final Logger log = LogManager.getLogger(PetSteps.class);
    PetStoreClient client = new PetStoreClient();

    @Given("I create a pet with name {string} and status {string}")
    public void createPet(String name, String status) {

        String finalName = name.replace("<timestamp>", String.valueOf(System.currentTimeMillis()));
        long id = System.currentTimeMillis();

        String body = "{ \"id\": " + id + ", \"name\": \"" + finalName + "\", \"status\": \"" + status + "\" }";

        log.info("Creating Pet: " + finalName);

        TestContext.petName = finalName;
        TestContext.response = client.createPet(body);
    }

    @Then("API response should be successful")
    public void validateSuccess() {
        int status = TestContext.response.getStatusCode();
        log.info("Status Code: " + status);

        Assert.assertTrue(status == 200);
    }

    @And("I store pet id from response")
    public void storeId() {
        TestContext.petId = TestContext.response.jsonPath().getLong("id");
        log.info("Stored Pet ID: " + TestContext.petId);
    }

    @When("I get the pet by stored id")
    public void getPet() {
        log.info("Fetching Pet ID: " + TestContext.petId);
        TestContext.response = client.getPet(TestContext.petId);
    }

    @Then("pet name should match stored name")
    public void validateName() {

        String actual = TestContext.response.jsonPath().getString("name");

        log.info("Expected Name: " + TestContext.petName);
        log.info("Actual Name: " + actual);

        if (actual == null) return;

        Assert.assertEquals(actual, TestContext.petName);
    }

    @Then("pet status should be {string}")
    public void validateStatus(String expectedStatus) {

        String actualStatus = TestContext.response.jsonPath().getString("status");

        log.info("Expected Status: " + expectedStatus);
        log.info("Actual Status: " + actualStatus);

        if (actualStatus == null) return;

        Assert.assertEquals(actualStatus, expectedStatus);
    }

    @When("I update pet status to {string}")
    public void updatePet(String status) {

        log.info("Updating Pet ID: " + TestContext.petId + " -> " + status);

        TestContext.response = client.updatePet(
                TestContext.petId,
                TestContext.petName,
                status
        );
    }

    @When("I delete the pet")
    public void deletePet() {

        log.info("Deleting Pet ID: " + TestContext.petId);

        TestContext.response = client.deletePet(TestContext.petId);
    }
}
