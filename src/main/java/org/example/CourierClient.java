package org.example;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierClient extends Client{

    private static final String CREATE_PATH = "/api/v1/courier";

    private static final String LOGIN_PATH = "/api/v1/courier/login";

    private static final String DELETE_PATH = "/api/v1/courier/";

    public ValidatableResponse create (Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(CREATE_PATH)
                .then();

    }

    public ValidatableResponse login(CourierCredentials courierCredentials) {
        return given()
                .spec(getSpec())
                .body(courierCredentials)
                .when()
                .post(LOGIN_PATH)
                .then();

    }

    public ValidatableResponse delete(int id) {
        return given()
                .spec(getSpec())
                .when()
                .delete(DELETE_PATH + id)
                .then();

    }

}
