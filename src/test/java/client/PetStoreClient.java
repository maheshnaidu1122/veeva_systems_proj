package client;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PetStoreClient {

    // Supports Maven override: mvn clean test -DbaseUrl=...
    private final String BASE_URL = System.getProperty("baseUrl", "https://petstore.swagger.io/v2");

    // =========================
    // PET APIs
    // =========================

    public Response createPet(String body) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/pet");
    }

    public Response getPet(long id) {
        return given()
                .baseUri(BASE_URL)
                .when()
                .get("/pet/" + id);
    }

    public Response updatePet(long id, String name, String status) {

        String body = "{ \"id\": " + id + ", \"name\": \"" + name + "\", \"status\": \"" + status + "\" }";

        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .put("/pet");
    }

    public Response deletePet(long id) {
        return given()
                .baseUri(BASE_URL)
                .when()
                .delete("/pet/" + id);
    }

    public Response findPetsByStatus(String status) {
        return given()
                .baseUri(BASE_URL)
                .queryParam("status", status)
                .when()
                .get("/pet/findByStatus");
    }

    // =========================
    // STORE APIs
    // =========================

    public Response getInventory() {
        return given()
                .baseUri(BASE_URL)
                .when()
                .get("/store/inventory");
    }

    // =========================
    // USER APIs
    // =========================

    public Response createUser(String body) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/user");
    }

    public Response loginUser(String username, String password) {
        return given()
                .baseUri(BASE_URL)
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get("/user/login");
    }

    public Response getUser(String username) {
        return given()
                .baseUri(BASE_URL)
                .when()
                .get("/user/" + username);
    }
}