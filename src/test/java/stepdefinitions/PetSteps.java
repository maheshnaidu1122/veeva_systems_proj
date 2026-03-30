package stepdefinitions;

import client.PetStoreClient;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.TestDataBuilder;

public class PetSteps {

    PetStoreClient client = new PetStoreClient();
    Response response;
    long petId;

    // CREATE (C)
    @Given("I create a pet")
    public void createPet() {
        response = client.createPet(TestDataBuilder.createPet().toString());
        Assert.assertEquals(response.getStatusCode(), 200);
        petId = response.jsonPath().getLong("id");
    }

    @Then("pet should be created successfully")
    public void validatePetCreated() {
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    // READ (R)
    @When("I fetch the pet")
    public void fetchPet() {
        response = client.getPetById(petId);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Then("pet should be available")
    public void validatePetAvailable() {
        String status = response.jsonPath().getString("status");
        Assert.assertEquals(status, "available");
    }

    // UPDATE (U)
    @When("I update pet status to {string}")
    public void updatePet(String status) {
        response = client.updatePet(petId, "doggie", status);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Then("pet status should be updated")
    public void validateUpdate() {
        response = client.getPetById(petId);
        String updatedStatus = response.jsonPath().getString("status");
        Assert.assertEquals(updatedStatus, "sold");
    }

    // DELETE (D)
    @When("I delete the pet")
    public void deletePet() {
        response = client.deletePet(petId);
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Then("pet should be deleted")
    public void validateDelete() {
        Response checkResponse = client.getPetById(petId);
        Assert.assertEquals(checkResponse.getStatusCode(), 404);
    }
}