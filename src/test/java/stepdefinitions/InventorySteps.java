package stepdefinitions;

import client.PetStoreClient;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import utils.TestContext;

import java.util.Map;

public class InventorySteps {

    private static final Logger log = LogManager.getLogger(InventorySteps.class);
    PetStoreClient client = new PetStoreClient();

    int availableCount;
    int listCount;

    @Given("I fetch inventory")
    public void fetchInventory() {
        log.info("Fetching inventory...");
        TestContext.response = client.getInventory();
    }

    @Then("inventory response should be successful")
    public void validateInventory() {
        log.info("Inventory Status: " + TestContext.response.getStatusCode());
        Assert.assertEquals(TestContext.response.getStatusCode(), 200);
    }

    @Then("I extract available count")
    public void extractAvailable() {
        Map<String, Integer> map = TestContext.response.jsonPath().getMap("$");
        availableCount = map.getOrDefault("available", 0);
        log.info("Inventory Available Count: " + availableCount);
    }

    @When("I fetch pets with status {string}")
    public void fetchPets(String status) {
        log.info("Fetching pets with status: " + status);
        TestContext.response = client.findPetsByStatus(status);
    }

    @Then("pet list response should be successful")
    public void validatePetList() {
        log.info("Pet List Status: " + TestContext.response.getStatusCode());
        Assert.assertEquals(TestContext.response.getStatusCode(), 200);
    }

    @Then("I count pets in list")
    public void countPets() {
        listCount = TestContext.response.jsonPath().getList("$").size();
        log.info("Pet List Count: " + listCount);
    }

    @Then("both counts should match")
    public void validateCounts() {
        log.info("Comparing counts -> Inventory: " + availableCount + " | List: " + listCount);
        Assert.assertEquals(listCount, availableCount);
    }
}