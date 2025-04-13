package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.$;
public class TextBoxTests {

    @BeforeAll
    static void beforeAll(){
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        Configuration.holdBrowserOpen = true;
        Configuration.timeout = 5000;
    }
    @Test
    void fillFormTests() {

        open("/text-box");
        $("#userName").setValue("Medina").pressEnter();
        $("#userEmail").setValue("medina@gmail.com").pressEnter();
        $("#currentAddress").setValue("Some street").pressEnter();
        $("#permanentAddress").setValue("Some street_2").pressEnter();
        $("#submit").click();


        $("#output #name").shouldHave(text("Medina")); //тип оформления
        $("#output").$("#email").shouldHave(text("medina@gmail.com"));
        $("#output").$("#currentAddress").shouldHave(text("Some street"));
        $("#output").$("#permanentAddress").shouldHave(text("Some street_2"));



    }

}