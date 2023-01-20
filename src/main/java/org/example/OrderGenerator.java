package org.example;
import com.github.javafaker.Faker;

public class OrderGenerator {

    public static Faker faker = new Faker();

    private static String firstName = faker.name().firstName();
    private static String lastName = faker.name().lastName();
    private static String address = faker.address().fullAddress();
    private static int metroStation = faker.number().randomDigit();
    private static String phone = faker.phoneNumber().phoneNumber();
    private static int rentTime = faker.number().randomDigit();
    private static String deliveryDate = "2032-01-01";
    private static String comment = "Saske, come back to Konoha";

    public static String[] color;

    public static Order getOrder(String[] color) {
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment , color);
    }

}