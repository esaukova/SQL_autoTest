package ru.netology.page;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    public DashboardPage() {
        $("[data-test-id=dashboard]").shouldBe(visible);
    }
}
