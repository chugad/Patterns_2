package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;
import lombok.val;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class UserGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static Faker faker = new Faker(new Locale("en"));

    private UserGenerator() {
    }

    static void setUpUser(UserInfo user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getLogin() {
        return faker.name().username();
    }

    public static String getPassword() {
        return faker.internet().password();
    }

    public static UserInfo getActiveRegisteredUser() {
        val user = new UserInfo(getLogin(), getPassword(), "active");
        setUpUser(user);
        return user;
    }

    public static UserInfo getBlockedRegisteredUser() {
        val user = new UserInfo(getLogin(), getPassword(), "blocked");
        setUpUser(user);
        return user;
    }

    public static UserInfo getWrongNameUser() {
        val password = getPassword();
        setUpUser(new UserInfo(getLogin(), password, "active"));
        return new UserInfo(getLogin(), password, "active");
    }

    @Value
    public static class UserInfo {
        String login;
        String password;
        String status;
    }
}