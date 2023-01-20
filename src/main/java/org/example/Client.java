package org.example;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Client {

    private static final String BASE_URL = "http://qa-scooter.praktikum-services.ru";

    //Getting all settings collected within this particular method, which we shall use in tests
    protected RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build();

    }

}