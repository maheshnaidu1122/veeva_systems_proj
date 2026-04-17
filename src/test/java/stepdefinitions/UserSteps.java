package stepdefinitions;

import client.PetStoreClient;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import utils.TestContext;

public class UserSteps {

    private static final Logger log = LogManager.getLogger(UserSteps.class);
    PetStoreClient client = new PetStoreClient();

    String generatedUsername;

    @When("I create user with email {string}")
    public void createInvalidUser(String email) {

        generatedUsername = "user_" + System.currentTimeMillis();

        log.info("Creating user: " + generatedUsername);

        String body = "{ \"username\": \"" + generatedUsername + "\", \"email\": \"" + email + "\" }";

        TestContext.response = client.createUser(body);
    }

    @Then("response should contain created username")
    public void validateUserCreated() {

        int status = TestContext.response.getStatusCode();

        log.info("User creation status: " + status);

        Assert.assertEquals(status, 200);
    }

    @Then("response should not validate email format")
    public void validateEmailNotChecked() {

        log.info("Validating email format behavior");

        Assert.assertEquals(TestContext.response.getStatusCode(), 200);
    }

    @When("I fetch user {string}")
    public void fetchUser(String username) {

        String finalUsername = username.replace("<timestamp>", String.valueOf(System.currentTimeMillis()));

        log.info("Fetching user: " + finalUsername);

        TestContext.response = client.getUser(finalUsername);
    }

    @Then("user should not be found")
    public void userNotFound() {

        int status = TestContext.response.getStatusCode();

        log.info("Fetch Status: " + status);

        Assert.assertEquals(status, 404);
    }

    @Then("error message should contain {string}")
    public void errorMessage(String msg) {

        String response = TestContext.response.asString();

        log.info("Error response: " + response);

        Assert.assertTrue(response.contains(msg));
    }

    @When("I login with username {string} and password {string}")
    public void invalidLogin(String username, String password) {

        String finalUsername = username.replace("<timestamp>", String.valueOf(System.currentTimeMillis()));

        log.info("Attempting login for: " + finalUsername);

        TestContext.response = client.loginUser(finalUsername, password);
    }

    @Then("login should fail logically")
    public void loginFailLogical() {

        int status = TestContext.response.getStatusCode();

        log.info("Login Status: " + status);

        Assert.assertEquals(status, 401);
    }
}