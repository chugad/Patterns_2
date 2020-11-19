package ru.netology;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import static io.restassured.RestAssured.given;


public abstract class UserGenerator {
    private static Faker faker = new Faker();
    private static final String login = faker.name().username();
    private static final String password = faker.internet().password();


    private UserGenerator() {
    }

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static CreateUser getCreateActiveUser() {
        CreateUser activeUser = new CreateUser(login, password, "active");
        return activeUser;
    }

    static void setUpActiveUser() {

        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(new Gson().toJson(getCreateActiveUser()))// передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static CreateUser getCreateBlockedUser() {
        CreateUser blockedUser = new CreateUser(login, password, "blocked");
        return blockedUser;
    }

    static void setUpBlockedUser() {

        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(new Gson().toJson(getCreateBlockedUser()))// передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Value
    public static class CreateUser {
        String login;
        String password;
        String status;

        public String getLogin() {
            return login;
        }

        public String getLogin2() {
            return faker.name().username();
        }

        public String getPassword() {
            return password;
        }

        public String getPassword2() {
            return faker.internet().password();
        }
    }
}