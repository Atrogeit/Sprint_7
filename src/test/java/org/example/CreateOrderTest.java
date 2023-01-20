package org.example;

import io.restassured.response.ValidatableResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final String[] color;
    private final OrderClient OrderClient;


    public CreateOrderTest(String[] color) {
        OrderClient = new OrderClient();
        this.color = color;
    }


    @Parameterized.Parameters(name = "Test data: {0}, {1}")
    public static Object[][] setColor() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}
        };
    }


    @Test
    public void orderTest() {
        Order order = OrderGenerator.getOrder(color);
        ValidatableResponse response = OrderClient.createOrder(order);

        int statusCode = response.extract().statusCode();
        int track = response.extract().path("track");

        assertTrue(track > 0 );
        assertEquals(SC_CREATED, statusCode);


    }
}