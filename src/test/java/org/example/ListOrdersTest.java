package org.example;

import io.restassured.response.ValidatableResponse;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ListOrdersTest {


    private OrderClient OrderClient;

    @Before
    public void setUp() {
        OrderClient = new OrderClient();
    }

    @Test
    // Get respond 200
    public void checkSuccessfulRespondGetOrderReturnStatusCode200(){
        ValidatableResponse response = OrderClient.getOrders();
        int statusCode = response.extract().statusCode();

        assertEquals(SC_OK, statusCode);
    }

    @Test
    // Request of order list returns not an empty array
    public void checkSuccessfulGetOrderReturnsNotEmptyArray(){
        ValidatableResponse response = OrderClient.getOrders();

        ArrayList orders = response.extract().path("orders");

        assertTrue(orders.size()  > 0 );
    }
}