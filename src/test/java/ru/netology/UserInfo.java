package ru.netology;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import lombok.Value;

import static io.restassured.RestAssured.given;


public class UserInfo {
    private UserInfo() {
    }
    @Value
    public static class User {

        private static Faker faker = new Faker();
        private static final String login = faker.name().username();
        private static final String password = faker.internet().password();

        private static final RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        static void setUpActiveUser() {

            // сам запрос
            given() // "дано"
                    .spec(requestSpec) // указываем, какую спецификацию используем
                    .body(new Gson().toJson(new createUser(login, password, "active")))// передаём в теле объект, который будет преобразован в JSON
                    .when() // "когда"
                    .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                    .then() // "тогда ожидаем"
                    .statusCode(200); // код 200 OK
        }

        static void setUpBlockedUser() {

            // сам запрос
            given() // "дано"
                    .spec(requestSpec) // указываем, какую спецификацию используем
                    .body(new Gson().toJson(new createUser(login, password, "blocked")))// передаём в теле объект, который будет преобразован в JSON
                    .when() // "когда"
                    .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                    .then() // "тогда ожидаем"
                    .statusCode(200); // код 200 OK
        }

        public static String getLogin() {
            return login;
        }

        public static String getLogin2() {
            return faker.name().username();
        }

        public static String getPassword() {
            return password;
        }

        public static String getPassword2() {
            return faker.internet().password();
        }
    }
    @Value
    @AllArgsConstructor
    private static class createUser {
        String login;
        String password;
        String status;
    }
}
