package org.example;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrderClient extends Client{

    private static final String ORDER_PATH = "/api/v1/orders";



    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();

    }

    public ValidatableResponse getOrders() {
        return given()
                .spec(getSpec())
                .when()
                .get(ORDER_PATH)
                .then();

    }

}

