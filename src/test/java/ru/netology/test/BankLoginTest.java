package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.*;

public class BankLoginTest {
    LoginPage loginPage;

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @AfterEach
    void authCodeCleaner() {
        cleanAuthCodes();
    }

    @AfterAll
    static void cleaner() {
        cleanDB();
    }

    @Test
    void shouldSuccessfulLogin() {
        var authUser = DataHelper.getValidAuthUser();
        var verificationPage = loginPage.validLogin(authUser);
        verificationPage.verifyPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerification(verificationCode.getVerificationCode());
    }

    @Test
    void shouldGetErrorWithRandomUser() {
        var authUser = DataHelper.getRandomUser();
        loginPage.validLogin(authUser);
        loginPage.getErrorNotificationIfWrongLogin("Ошибка! " + "Неверно указан логин или пароль");
    }

    @Test
    void shouldGetErrorWithWrongPassword() {
        var authUser = new DataHelper.AuthUser(DataHelper.getValidAuthUser().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authUser);
        loginPage.getErrorNotificationIfWrongLogin("Ошибка! " + "Неверно указан логин или пароль");
    }

    @Test
    void shouldGetErrorWithWrongLogin() {
        var authUser = new DataHelper.AuthUser(DataHelper.getRandomUser().getLogin(),
                DataHelper.getValidAuthUser().getPassword());
        loginPage.validLogin(authUser);
        loginPage.getErrorNotificationIfWrongLogin("Ошибка! " + "Неверно указан логин или пароль");
    }

    @Test
    void shouldGetWarningWithEmptyLogin() {
        var authUser = new DataHelper.AuthUser("",
                DataHelper.getValidAuthUser().getPassword());
        loginPage.validLogin(authUser);
        loginPage.getWarningNotificationIfLoginEmpty("Поле обязательно для заполнения");
    }

    @Test
    void shouldGetWarningWithEmptyPassword() {
        var authUser = new DataHelper.AuthUser(DataHelper.getValidAuthUser().getLogin(),
                "");
        loginPage.validLogin(authUser);
        loginPage.getWarningNotificationIfPasswordEmpty("Поле обязательно для заполнения");
    }

    @Test
    void shouldGetErrorWithInvalidVerificationCode() {
        var authUser = DataHelper.getValidAuthUser();
        var verificationPage = loginPage.validLogin(authUser);
        verificationPage.verifyPageVisibility();
        var verificationCode = DataHelper.getRandomVerifyCode();
        verificationPage.verification(verificationCode.getVerificationCode());
        verificationPage.getErrorMessageIfWrongVerificationCode("Ошибка! " + "Неверно указан код! Попробуйте ещё раз.");
    }

    @Test
    void shouldGetWarningWithEmptyVerificationCode() {
        var authUser = DataHelper.getValidAuthUser();
        var verificationPage = loginPage.validLogin(authUser);
        verificationPage.verifyPageVisibility();
        var verificationCode = "";
        verificationPage.verification(verificationCode);
        verificationPage.getWarningMessageIfWrongVerificationCode("Поле обязательно для заполнения");
    }

    @Test
    void shouldGetErrorMessageIfUserBlockedAfterInputWrongPasswordThreeTimes() {
        var authUserFirstAttempt = new DataHelper.AuthUser(DataHelper.getValidAuthUser().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authUserFirstAttempt);
        loginPage.getErrorNotificationIfWrongLogin("Ошибка! " + "Неверно указан логин или пароль");
        loginPage.cleanForm();
        var authUserSecondAttempt = new DataHelper.AuthUser(DataHelper.getValidAuthUser().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authUserSecondAttempt);
        loginPage.getErrorNotificationIfWrongLogin("Ошибка! " + "Неверно указан логин или пароль");
        loginPage.cleanForm();
        var authUserThirdAttempt = new DataHelper.AuthUser(DataHelper.getValidAuthUser().getLogin(),
                DataHelper.getRandomUser().getPassword());
        loginPage.validLogin(authUserThirdAttempt);
        loginPage.getErrorNotificationIfUserBlocked("Ошибка! " + "Пользователь заблокирован");
    }

}
