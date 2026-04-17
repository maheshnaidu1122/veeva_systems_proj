package stepdefinitions;

import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import utils.TestContext;

import java.util.List;
import java.util.Map;

public class CrossEndPointSteps {

    private static final Logger log = LogManager.getLogger(CrossEndPointSteps.class);
    boolean found = false;

    @When("I search for stored pet id in list")
    public void searchPet() {

        log.info("Searching for Pet ID: " + TestContext.petId);

        List<Map<String, Object>> pets =
                TestContext.response.jsonPath().getList("$");

        for (Map<String, Object> pet : pets) {
            if (((Number) pet.get("id")).longValue() == TestContext.petId) {
                found = true;
                break;
            }
        }
    }

    @Then("pet should exist in the list")
    public void validateFound() {

        if (found) {
            log.info("Pet found in sold list");
        } else {
            log.warn("Pet NOT found in sold list");
        }

        Assert.assertTrue(found, "Pet not found in sold list");
    }
}