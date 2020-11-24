package ru.netology;

import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AuthorizationTest {

    @Test
    void happyPath() {
        UserInfo.User.setUpActiveUser();

        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(UserInfo.User.getLogin());
        $("[data-test-id=password] input").setValue(UserInfo.User.getPassword());
        $("[data-test-id='action-login']").click();
        $$("h2").findBy(text("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldNotSussesLoginActiveUser_SameNameAnotherPassword() {
        UserInfo.User.setUpActiveUser();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(UserInfo.User.getLogin());
        $("[data-test-id=password] input").setValue(UserInfo.User.getPassword2());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldNotSussesLoginActiveUser_AnotherNameSamePassword() {
        UserInfo.User.setUpActiveUser();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(UserInfo.User.getLogin2());
        $("[data-test-id=password] input").setValue(UserInfo.User.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldNotSussesLoginBlockedUser() {
        UserInfo.User.setUpBlockedUser();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(UserInfo.User.getLogin());
        $("[data-test-id=password] input").setValue(UserInfo.User.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Пользователь заблокирован")).shouldBe(visible);
    }
}