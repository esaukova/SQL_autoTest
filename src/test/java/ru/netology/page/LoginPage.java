package ru.netology.page;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

public class LoginPage {

    public VerificationPage validLogin(DataHelper.AuthUser user) {
        $("[data-test-id=login] input").setValue(user.getLogin());
        $("[data-test-id=password] input").setValue(user.getPassword());
        $("[data-test-id=action-login]").click();
        return new VerificationPage();
    }


    public void getErrorNotificationIfWrongLogin(String expectedText) {
        $("[data-test-id='error-notification'] .notification__content").shouldHave(exactText(expectedText)).shouldBe(Condition.visible);
    }

    public void getErrorNotificationIfUserBlocked(String expectedText) {
        $("[data-test-id='error-notification'] .notification__content").shouldHave(exactText(expectedText)).shouldBe(Condition.visible);
    }

    public void getWarningNotificationIfLoginEmpty(String expectedText) {
        $("[data-test-id='login'] .input__sub").shouldHave(exactText(expectedText)).shouldBe(Condition.visible);
    }

    public void getWarningNotificationIfPasswordEmpty(String expectedText) {
        $("[data-test-id='password'] .input__sub").shouldHave(exactText(expectedText)).shouldBe(Condition.visible);
    }

    public void cleanForm() {
        $("[data-test-id=login] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=password] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
    }

}
