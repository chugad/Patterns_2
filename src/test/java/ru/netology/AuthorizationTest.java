package ru.netology;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AuthorizationTest {

    static UserGenerator.CreateUser activeUser = UserGenerator.getCreateActiveUser();
    static UserGenerator.CreateUser blockedUser = UserGenerator.getCreateBlockedUser();

    @Test
    void happyPath() {
        UserGenerator.setUpActiveUser();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(activeUser.getLogin());
        $("[data-test-id=password] input").setValue(activeUser.getPassword());
        $("[data-test-id='action-login']").click();
        $$("h2").findBy(text("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldNotSussesLoginActiveUser_SameNameAnotherPassword() {
        UserGenerator.setUpActiveUser();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(activeUser.getLogin());
        $("[data-test-id=password] input").setValue(activeUser.getPassword2());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldNotSussesLoginActiveUser_AnotherNameSamePassword() {
        UserGenerator.setUpActiveUser();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(activeUser.getLogin2());
        $("[data-test-id=password] input").setValue(activeUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldNotSussesLoginBlockedUser() {
        UserGenerator.setUpBlockedUser();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(blockedUser.getLogin());
        $("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Пользователь заблокирован")).shouldBe(visible);
    }
}