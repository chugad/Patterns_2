package ru.netology;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.UserGenerator.getActiveRegisteredUser;
import static ru.netology.UserGenerator.getBlockedRegisteredUser;
import static ru.netology.UserGenerator.getWrongNameUser;

public class AuthorizationTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void happyPath() {
        val validUser = getActiveRegisteredUser();

        $("[data-test-id=login] input").setValue(validUser.getLogin());
        $("[data-test-id=password] input").setValue(validUser.getPassword());
        $("[data-test-id='action-login']").click();
        $$("h2").findBy(text("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldNotSussesLoginActiveUser_SameNameAnotherPassword() {
        val validUser = getActiveRegisteredUser();
        val wrongNameUser = getWrongNameUser();
        $("[data-test-id=login] input").setValue(validUser.getLogin());
        $("[data-test-id=password] input").setValue(wrongNameUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldNotSussesLoginActiveUser_AnotherNameSamePassword() {
        val validUser = getActiveRegisteredUser();
        val wrongNameUser = getWrongNameUser();
        $("[data-test-id=login] input").setValue(wrongNameUser.getLogin());
        $("[data-test-id=password] input").setValue(validUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldNotSussesLoginBlockedUser() {
        val blockedUser = getBlockedRegisteredUser();
        $("[data-test-id=login] input").setValue(blockedUser.getLogin());
        $("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Пользователь заблокирован")).shouldBe(visible);
    }
}