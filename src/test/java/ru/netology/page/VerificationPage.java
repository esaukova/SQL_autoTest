package ru.netology.page;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class VerificationPage {

    public DashboardPage validVerification(String VerificationCode) {
        verification(VerificationCode);
        return new DashboardPage();
    }

    public void verifyPageVisibility() {
        $("[data-test-id=code] input").shouldBe(visible);
    }

    public void getErrorMessageIfWrongVerificationCode(String expectedText) {
        $("[data-test-id='error-notification'] .notification__content").shouldHave(text(expectedText)).shouldBe(visible);
    }
    public void getWarningMessageIfWrongVerificationCode(String expectedText) {
        $("[data-test-id=code] .input__sub").shouldHave(text(expectedText)).shouldBe(visible);
    }

    public void verification(String VerificationCode) {
        $("[data-test-id=code] input").setValue(VerificationCode);
        $("[data-test-id=action-verify]").click();
    }
}
