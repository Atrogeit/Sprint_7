package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CreateCourierTest {

    private Courier courier;

    private CourierClient CourierClient;

    private static final String MESSAGE_NOT_ENOUGH_DATA = "Недостаточно данных для создания учетной записи";

    private int id;

    @Before
    public void setUp() {
        courier = CourierGenerator.getCourier();
        CourierClient = new CourierClient();
    }

    @After
    public void cleanUp() {
        ValidatableResponse loginResponse = CourierClient.login(CourierCredentials.from(courier));
        int statusCode = loginResponse.extract().statusCode();
        if(statusCode == SC_OK) {
            id = loginResponse.extract().path("id");
            ValidatableResponse deleteResponse = CourierClient.delete(id);
            int statusCodeResult= deleteResponse.extract().statusCode();
        }
    }



    @Test
    //Checking if Courier can be created
    //Here and after all status codes replaced with static finals from HttpStatus lib
    public void createNewCourier(){
        ValidatableResponse response = CourierClient.create(courier);
        ValidatableResponse loginResponse = CourierClient.login(CourierCredentials.from(courier));

        id = loginResponse.extract().path("id");
        int statusCode = response.extract().statusCode();

        assertEquals(SC_CREATED, statusCode);
    }

    @Test
    //Such user is already existing
    public void checkToNotCreateNewCourierWithExistingLogin(){
        Courier existingCourier = CourierGenerator.getExistingCourier();
        ValidatableResponse response = CourierClient.create(existingCourier);

        int statusCode = response.extract().statusCode();
        String message = response.extract().path("message");

        assertEquals(SC_CONFLICT, statusCode);
        assertEquals(message, "Этот логин уже используется."); // Тут идет ошибка в документации или ошибка в беке
    }

    @Test
    //Can't create new courier user without login
    public void checkCreationNewCourierWithoutLogin(){
        Courier courierWithoutLogin = CourierGenerator.getCourierWithoutLogin();
        ValidatableResponse response = CourierClient.create(courierWithoutLogin);


        int statusCode = response.extract().statusCode();
        String message = response.extract().path("message");

        assertEquals(SC_BAD_REQUEST, statusCode);
        assertEquals(message, MESSAGE_NOT_ENOUGH_DATA);

    }

    @Test
    //Can't create new courier user without password
    public void checkCreationNewCourierWithoutPassword(){
        Courier courierWithoutPassword = CourierGenerator.getCourierWithoutPassword();
        ValidatableResponse response = CourierClient.create(courierWithoutPassword);

        int statusCode = response.extract().statusCode();
        String message = response.extract().path("message");

        assertEquals(SC_BAD_REQUEST, statusCode);
        assertEquals(message, MESSAGE_NOT_ENOUGH_DATA);

    }

    @Test
    //Creating new courier without first name
    public void checkCreationNewCourierWithoutFirstName(){
        Courier courierWithoutFirstName = CourierGenerator.getCourierWithoutFirstName();
        ValidatableResponse response = CourierClient.create(courierWithoutFirstName);
        ValidatableResponse loginResponse = CourierClient.login(CourierCredentials.from(courier));

        int statusCode = response.extract().statusCode();
        id = loginResponse.extract().path("id");

        assertEquals(SC_CREATED, statusCode);

    }

    @Test
    @DisplayName("Checking successful request to return OK TRUE")
    // Checking successful request to return OK TRUE
    public void checkSuccessfulRequestReturnsValueOkTrue(){
        Courier courierWithoutFirstName = CourierGenerator.getCourierWithoutFirstName();
        ValidatableResponse response = CourierClient.create(courierWithoutFirstName);
        ValidatableResponse loginResponse = CourierClient.login(CourierCredentials.from(courier));

        boolean ok_field = response.extract().path("ok");
        id = loginResponse.extract().path("id");

        assertTrue(ok_field);





    }
}

