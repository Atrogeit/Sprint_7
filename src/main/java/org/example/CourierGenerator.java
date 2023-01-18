package org.example;
import com.github.javafaker.Faker;

public class CourierGenerator {

    public static Faker faker = new Faker();


    private static String login = faker.name().username();
    private static String password = faker.name().firstName();
    private static String firstName = faker.name().lastName();

    public static Courier getCourier() {
        return new Courier(login, password, firstName);
    }

    public static Courier getExistingCourier() {
        return new Courier("existingCourier", "12345", "Courier");
    }

    public static Courier getCourierWithoutLogin() {
        return new Courier("", password, "Courier");
    }

    public static Courier getCourierWithoutPassword() {
        return new Courier(login, "", "Courier");
    }

    public static Courier getCourierWithoutFirstName() {
        return new Courier(login, password, "");
    }
}