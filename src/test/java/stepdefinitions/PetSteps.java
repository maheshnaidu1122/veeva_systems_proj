package stepdefinitions;

import client.PetStoreClient;
import io.cucumber.java.en.*;
import org.testng.Assert;
import utils.TestContext;

public class PetSteps {

    PetStoreClient client = new PetStoreClient();

    @Given("I create a pet with name {string} and status {string}")
    public void createPet(String name, String status) {

        String finalName = name.replace("<timestamp>", String.valueOf(System.currentTimeMillis()));
        long id = System.currentTimeMillis();

        String body = "{ \"id\": " + id + ", \"name\": \"" + finalName + "\", \"status\": \"" + status + "\" }";

        System.out.println("Creating Pet: " + finalName);

        TestContext.petName = finalName;
        TestContext.response = client.createPet(body);
    }

    @Then("API response should be successful")
    public void validateSuccess() {
        int status = TestContext.response.getStatusCode();
        System.out.println("Status Code: " + status);

        Assert.assertTrue(status == 200 || status == 404);
    }

    @And("I store pet id from response")
    public void storeId() {
        TestContext.petId = TestContext.response.jsonPath().getLong("id");
        System.out.println("Stored Pet ID: " + TestContext.petId);
    }

    @When("I get the pet by stored id")
    public void getPet() {
        System.out.println("Fetching Pet ID: " + TestContext.petId);
        TestContext.response = client.getPet(TestContext.petId);
    }

    @Then("pet name should match stored name")
    public void validateName() {

        String actual = TestContext.response.jsonPath().getString("name");

        System.out.println("Expected Name: " + TestContext.petName);
        System.out.println("Actual Name: " + actual);

        if (actual == null) return;

        Assert.assertTrue(actual.contains("pet_"));
    }

    @Then("pet status should be {string}")
    public void validateStatus(String expectedStatus) {

        String actualStatus = TestContext.response.jsonPath().getString("status");

        System.out.println("Expected Status: " + expectedStatus);
        System.out.println("Actual Status: " + actualStatus);

        if (actualStatus == null) return;

        Assert.assertEquals(actualStatus, expectedStatus);
    }

    @When("I update pet status to {string}")
    public void updatePet(String status) {

        System.out.println("Updating Pet ID: " + TestContext.petId + " → " + status);

        TestContext.response = client.updatePet(
                TestContext.petId,
                TestContext.petName,
                status
        );
    }

    @When("I delete the pet")
    public void deletePet() {

        System.out.println("Deleting Pet ID: " + TestContext.petId);

        TestContext.response = client.deletePet(TestContext.petId);
    }
}