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

        TestContext.petName = finalName;
        TestContext.response = client.createPet(body);
    }

    @Then("API response should be successful")
    public void validateSuccess() {
        Assert.assertEquals(TestContext.response.getStatusCode(), 200);
    }

    @And("I store pet id from response")
    public void storeId() {
        TestContext.petId = TestContext.response.jsonPath().getLong("id");
    }

    @When("I get the pet by stored id")
    public void getPet() {
        TestContext.response = client.getPet(TestContext.petId);
    }

    @Then("pet name should match stored name")
    public void validateName() {
        Assert.assertEquals(TestContext.response.jsonPath().getString("name"), TestContext.petName);
    }

    @Then("pet status should be {string}")
    public void validateStatus(String status) {
        Assert.assertEquals(TestContext.response.jsonPath().getString("status"), status);
    }

    @When("I update pet status to {string}")
    public void updatePet(String status) {
        TestContext.response = client.updatePet(TestContext.petId, TestContext.petName, status);
    }

    @When("I delete the pet")
    public void deletePet() {
        TestContext.response = client.deletePet(TestContext.petId);
    }
}