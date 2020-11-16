package ru.netology;

import com.github.javafaker.Faker;
import lombok.Value;

public class UserGenerator {
    private UserGenerator() {}

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
        private String status;

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123", "active");
    }


}