package org.example;

import io.restassured.response.ValidatableResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class LoginTest {

    private Courier courier;
    private CourierClient CourierClient;
    private int id;
    private static final String MESSAGE_ACCOUNT_NOT_FOUND = "Учетная запись не найдена";
    private static final String MESSAGE_NOT_ENOUGH_DATA_FOR_ENTRY = "Недостаточно данных для входа";

    @Before
    public void setUp() {
        courier = CourierGenerator.getCourier();
        CourierClient = new CourierClient();
    }

    @After
    public void cleanUp() {
        if (id != 0) {
            CourierClient.delete(id);
        }
    }

    @Test
    // Successful login returns 200
    public void CheckSuccessfulLoginReturnsStatusCode200(){
        CourierClient.create(courier);

        ValidatableResponse loginResponse = CourierClient.login(CourierCredentials.from(courier));

        id = loginResponse.extract().path("id");
        int statusCode = loginResponse.extract().statusCode();

        assertEquals(SC_OK, statusCode);
    }

    @Test
    // Successful login returns id
    public void CheckSuccessfulLoginReturnsID(){
        CourierClient.create(courier);

        ValidatableResponse loginResponse = CourierClient.login(CourierCredentials.from(courier));
        id = loginResponse.extract().path("id");

        assertTrue(id  > 0 );
    }

    @Test
    // Can't login without a correct password, returns 400
    public void CheckAuthorizationWithoutPasswordReturnsStatusCode400(){

        CourierCredentials courierWithoutPassword = new CourierCredentials(courier.getLogin(), "");
        ValidatableResponse loginResponse = CourierClient.login(courierWithoutPassword);

        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");

        assertEquals(SC_BAD_REQUEST, statusCode);
        assertEquals(MESSAGE_NOT_ENOUGH_DATA_FOR_ENTRY, message);
    }


    @Test
    // Can't authorize without login and returns 400
    public void CheckAuthorizationWithoutLoginReturnsStatusCode400(){
        CourierCredentials courierWithoutLogin = new CourierCredentials("", courier.getPassword());
        ValidatableResponse loginResponse = CourierClient.login(courierWithoutLogin);

        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");

        assertEquals(SC_BAD_REQUEST, statusCode);
        assertEquals(MESSAGE_NOT_ENOUGH_DATA_FOR_ENTRY, message);
    }

    @Test
    // Login with not existing user
    public void CheckLoginWithNotExistingUserReturnsStatusCode404(){
        CourierCredentials courierWithoutLogin = new CourierCredentials("ТАКОЙ_ЮЗЕР_НЕ_СУЩЕСТВУЕТ", "12345");
        ValidatableResponse loginResponse = CourierClient.login(courierWithoutLogin);

        int statusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");

        assertEquals(SC_NOT_FOUND, statusCode);
        assertEquals(message, MESSAGE_ACCOUNT_NOT_FOUND);
    }
}