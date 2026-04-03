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

        for (Map<String, Object> pet : pets) {

            if (((Number) pet.get("id")).longValue() == TestContext.petId) {
                found = true;
                break;
            }
        }
    }

    @Then("pet should exist in the list")
    public void validateFound() {
        Assert.assertTrue(found);
    }
}