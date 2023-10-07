package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    public static AuthUser getValidAuthUser() {
        return new AuthUser("vasya", "qwerty123");
    }

    private static Faker faker = new Faker(new Locale("en"));


    private static String getRandomLogin() {
        return faker.name().username();
    }

    private static String getRandomPassword() {
        return faker.internet().password();
    }

    public static AuthUser getRandomUser() {
        return new AuthUser(getRandomLogin(), getRandomPassword());
    }

    @Value
    public static class AuthUser {
        String login;
        String password;
    }

    @Value
    public static class VerificationCode {
        String verificationCode;
    }

    public static VerificationCode getRandomVerifyCode() {
        return new VerificationCode(faker.numerify("#####"));
    }

}
