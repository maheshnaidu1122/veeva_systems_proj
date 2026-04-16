package stepdefinitions;

import client.PetStoreClient;
import io.cucumber.java.en.*;
import org.testng.Assert;
import utils.TestContext;

import java.util.Map;

public class InventorySteps {

    PetStoreClient client = new PetStoreClient();

    int availableCount;
    int listCount;

    @Given("I fetch inventory")
    public void fetchInventory() {
        TestContext.response = client.getInventory();
    }

    @Then("inventory response should be successful")
    public void validateInventory() {
        Assert.assertEquals(TestContext.response.getStatusCode(), 200);
    }

    @Then("I extract available count")
    public void extractAvailable() {

        Map<String, Integer> map =
                TestContext.response.jsonPath().getMap("$");

        availableCount = map.getOrDefault("available", 0);

        System.out.println("Inventory Available Count: " + availableCount);
    }

    @When("I fetch pets with status {string}")
    public void fetchPets(String status) {

        System.out.println("Fetching pets with status: " + status);

        TestContext.response = client.findPetsByStatus(status);
    }

    @Then("pet list response should be successful")
    public void validatePetList() {
        Assert.assertEquals(TestContext.response.getStatusCode(), 200);
    }

    @Then("I count pets in list")
    public void countPets() {

        listCount = TestContext.response.jsonPath().getList("$").size();

        System.out.println("List Count: " + listCount);
    }

    @Then("both counts should match")
    public void validateCounts() {

        System.out.println("Inventory: " + availableCount);
        System.out.println("List: " + listCount);

        Assert.assertEquals(
                listCount,
                availableCount,
                "Count mismatch between inventory and list"
        );

        System.out.println("Counts Match ✅");
    }
}