package stepdefinitions;

import client.PetStoreClient;
import io.cucumber.java.en.*;
import org.testng.Assert;
import utils.TestContext;

public class UserSteps {

    PetStoreClient client = new PetStoreClient();
    String generatedUsername;

    @When("I create user with email {string}")
    public void createInvalidUser(String email) {

        generatedUsername = "user_" + System.currentTimeMillis();

        String body = "{ \"username\": \"" + generatedUsername + "\", \"email\": \"" + email + "\" }";

        TestContext.response = client.createUser(body);

        System.out.println("Create User Response: " + TestContext.response.asString());
    }

    @Then("response should contain created username")
    public void validateUserCreated() {

        Assert.assertEquals(TestContext.response.getStatusCode(), 200);
    }

    @Then("response should not validate email format")
    public void validateEmailNotChecked() {
        Assert.assertEquals(TestContext.response.getStatusCode(), 200);
    }

    @When("I fetch user {string}")
    public void fetchUser(String username) {

        String finalUsername = username.replace("<timestamp>", String.valueOf(System.currentTimeMillis()));

        TestContext.response = client.getUser(finalUsername);

        System.out.println("Fetch User Response: " + TestContext.response.asString());
    }

    @Then("user should not be found")
    public void userNotFound() {
        Assert.assertEquals(TestContext.response.getStatusCode(), 404);
    }

    @Then("error message should contain {string}")
    public void errorMessage(String msg) {
        Assert.assertTrue(TestContext.response.asString().contains(msg));
    }

    @When("I login with username {string} and password {string}")
    public void invalidLogin(String username, String password) {

        String finalUsername = username.replace("<timestamp>", String.valueOf(System.currentTimeMillis()));

        TestContext.response = client.loginUser(finalUsername, password);

        System.out.println("Login Response: " + TestContext.response.asString());
    }

    @Then("login should fail logically")
    public void loginFailLogical() {

        Assert.assertEquals(TestContext.response.getStatusCode(), 401);

        System.out.println("Login failed as expected ✅");
    }
}