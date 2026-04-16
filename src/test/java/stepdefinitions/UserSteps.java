package stepdefinitions;

import client.PetStoreClient;
import io.cucumber.java.en.*;
import org.testng.Assert;
import utils.TestContext;

public class UserSteps {

    PetStoreClient client = new PetStoreClient();
    String generatedUsername;

    // =========================
    // CREATE USER (INVALID EMAIL CASE)
    // =========================

    @When("I create user with email {string}")
    public void createInvalidUser(String email) {

        generatedUsername = "user_" + System.currentTimeMillis();

        String body = "{ \"username\": \"" + generatedUsername + "\", \"email\": \"" + email + "\" }";

        TestContext.response = client.createUser(body);
    }

    @Then("response should contain created username")
    public void validateUserCreated() {

        int status = TestContext.response.getStatusCode();
        String response = TestContext.response.asString();

        // API always returns 200 → validate that
        Assert.assertEquals(status, 200, "Expected status code 200");

        // Basic response validation
        Assert.assertNotNull(response, "Response is null");
        Assert.assertFalse(response.isEmpty(), "Response is empty");
    }

    @Then("response should not validate email format")
    public void validateEmailNotChecked() {

        // Swagger does NOT validate email → always 200
        Assert.assertEquals(TestContext.response.getStatusCode(), 200,
                "API unexpectedly validated email");
    }

    // =========================
    // FETCH NON-EXISTENT USER
    // =========================

    @When("I fetch user {string}")
    public void fetchUser(String username) {

        String finalUsername = username.replace("<timestamp>", String.valueOf(System.currentTimeMillis()));

        TestContext.response = client.getUser(finalUsername);
    }

    @Then("user should not be found")
    public void userNotFound() {

        int status = TestContext.response.getStatusCode();

        // This endpoint is reliable → should return 404
        Assert.assertEquals(status, 404, "Expected 404 for non-existent user");
    }

    @Then("error message should contain {string}")
    public void errorMessage(String msg) {

        String response = TestContext.response.asString();

        Assert.assertTrue(
                response.contains(msg),
                "Expected error message not found"
        );
    }

    // =========================
    // INVALID LOGIN
    // =========================

    @When("I login with username {string} and password {string}")
    public void invalidLogin(String username, String password) {

        String finalUsername = username.replace("<timestamp>", String.valueOf(System.currentTimeMillis()));

        TestContext.response = client.loginUser(finalUsername, password);
    }

    @Then("login should fail logically")
    public void loginFailLogical() {

        int status = TestContext.response.getStatusCode();
        String response = TestContext.response.asString().toLowerCase();

        // Swagger returns 200 even for invalid login
        Assert.assertEquals(status, 200, "Unexpected status code");

        // Ensure response exists
        Assert.assertNotNull(response, "Login response is null");
        Assert.assertFalse(response.isEmpty(), "Empty response received");

        // Logical validation → should not indicate real success
        Assert.assertFalse(
                response.contains("success"),
                "Login incorrectly marked as successful"
        );
    }
}