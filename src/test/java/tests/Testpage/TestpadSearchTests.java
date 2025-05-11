package tests.Testpage;


import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.CsvFileSource;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class TestpadSearchTests {

    @BeforeEach
    void setUp() {
        open("https://onlinetestpad.com/ru/tests");
        Configuration.pageLoadStrategy = "eager";
    }

    @ValueSource(strings = {"Java", "Python", "JUnit"})
    @ParameterizedTest(name = "Search for query '{0}' should return at least one result")
    @Tag("BLOCKER")
    @DisplayName("TC_WEB_1: Search result verification")
    void searchTestShouldReturnResults(String searchQuery) {

        $("div.input-search.input-group-lg > input").shouldBe(visible);
        $("div.input-search.input-group-lg > input").setValue(searchQuery).pressEnter();
        $$("div.test-item").shouldHave(sizeGreaterThan(0));
    }

    @CsvSource(value = {
            "Java, 10",
            "Python, 5",
            "JUnit, 3"
    })
    @ParameterizedTest(name = "Category '{0}' should have at least {1} tests")
    @Tag("BLOCKER")
    @DisplayName("TC_WEB_2: Category test count verification")
    void categoryTestShouldContainExpectedTests(String category, int expectedTestCount) {
        $$(".category-list .category-item").findBy(text(category)).click();
        $$("div.test-item").shouldHave(sizeGreaterThan(expectedTestCount - 1));
    }

    @CsvFileSource(resources = "/testdata/categories.csv", numLinesToSkip = 1)
    @ParameterizedTest(name = "Category '{0}' should contain at least one test")
    @Tag("BLOCKER")
    @DisplayName("TC_WEB_3: Category should contain tests (from CSV)")
    void categoryTestShouldHaveTestsFromFile(String category) {
        System.out.println("Category from CSV: '" + category + "'");
        $$(".category-list .category-item").findBy(text(category)).shouldBe(visible, enabled).click();
        $$("div.test-item").shouldHave(sizeGreaterThan(0));
    }
}
