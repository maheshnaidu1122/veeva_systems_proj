package stepdefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import utils.TestContext;

import java.util.List;
import java.util.Map;

public class CrossEndPointSteps {

    boolean found = false;

    @When("I search for stored pet id in list")
    public void searchPet() {

        List<Map<String, Object>> pets =
                TestContext.response.jsonPath().getList("$");

        System.out.println("Searching for Pet ID: " + TestContext.petId);

        for (Map<String, Object> pet : pets) {

            Object idObj = pet.get("id");

            if (idObj != null && ((Number) idObj).longValue() == TestContext.petId) {
                found = true;
                break;
            }
        }
    }

    @Then("pet should exist in the list")
    public void validateFound() throws InterruptedException {

        if (!found) {

            System.out.println("Retrying fetch (API delay)...");

            for (int i = 0; i < 3; i++) {

                Thread.sleep(1000);

                TestContext.response =
                        new client.PetStoreClient().findPetsByStatus("sold");

                List<Map<String, Object>> pets =
                        TestContext.response.jsonPath().getList("$");

                for (Map<String, Object> pet : pets) {
                    if (((Number) pet.get("id")).longValue() == TestContext.petId) {
                        found = true;
                        break;
                    }
                }

                if (found) break;
            }
        }

        System.out.println(found ? "Pet found in sold list ✅" : "Pet NOT found ❌");

        Assert.assertTrue(found, "Pet not found in sold list");
    }
}