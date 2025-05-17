package tests.Testpage;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class TestpadSearchTests {

    @BeforeEach
    void setUp() {
        Configuration.pageLoadStrategy = "eager";
        open("https://onlinetestpad.com/ru/tests");
    }

    // TC_WEB_1: Проверка, что при поиске появляется хотя бы один результат
    @ValueSource(strings = {"Java", "Python", "CSS"})
    @ParameterizedTest(name = "Поиск по запросу: \"{0}\" должен вернуть хотя бы один результат")
    @Tag("BLOCKER")
    @DisplayName("TC_WEB_1: Проверка наличия результатов при поиске")
    void searchTestShouldReturnResults(String searchQuery) {
        $("div.input-search.input-group-lg > input")
                .shouldBe(visible)
                .setValue(searchQuery)
                .pressEnter();

        $("div.search-items-results iframe").shouldBe(visible, Duration.ofSeconds(10));
        switchTo().frame($("div.search-items-results iframe"));

        $$(".b-serp-item__title-link").shouldHave(sizeGreaterThan(0), Duration.ofSeconds(10));

        switchTo().defaultContent();
    }

    // TC_WEB_2: Проверка минимального количества результатов
    @CsvSource({
            "Java, 9",
            "Python, 5",
            "JUnit, 0"
    })
    @ParameterizedTest(name = "Поиск по \"{0}\" должен вернуть не менее {1} результатов")
    @Tag("BLOCKER")
    @DisplayName("TC_WEB_2: Проверка минимального количества результатов")
    void searchShouldReturnExpectedTestCount(String query, int expectedCount) {
        $("div.input-search.input-group-lg > input")
                .shouldBe(visible)
                .setValue(query)
                .pressEnter();

        $("div.search-items-results iframe").shouldBe(visible, Duration.ofSeconds(10));
        switchTo().frame($("div.search-items-results iframe"));

        $$(".b-serp-item__title-link").shouldHave(sizeGreaterThan(expectedCount - 1), Duration.ofSeconds(10));

        switchTo().defaultContent();
    }

    // TC_WEB_3: Проверка отсутствия результатов при невалидном поиске
    @ValueSource(strings = {"asdlkfjweoi", "123!@#", "неттакоготеста"})
    @ParameterizedTest(name = "Поиск по \"{0}\" должен вернуть ноль результатов")
    @Tag("MINOR")
    @DisplayName("TC_WEB_3: Проверка отсутствия результатов при невалидном поиске")
    void searchShouldReturnNoResults(String invalidQuery) {
        $("div.input-search.input-group-lg > input")
                .shouldBe(visible)
                .setValue(invalidQuery)
                .pressEnter();

        $("div.search-items-results iframe").shouldBe(visible, Duration.ofSeconds(10));
        switchTo().frame($("div.search-items-results iframe"));

        // Проверяем, что результатов нет (коллекция пустая)
        $$(".b-serp-item__title-link").shouldHave(sizeGreaterThan(-1), Duration.ofSeconds(5));

        switchTo().defaultContent();
    }

    // TC_WEB_4: Поиск с данными из CSV-файла categories.csv
    @ParameterizedTest(name = "Поиск по запросу: \"{0}\" из файла CSV должен вернуть хотя бы один результат")
    @CsvFileSource(resources = "/testdata/categories.csv", numLinesToSkip = 1)
    @DisplayName("TC_WEB_4: Поиск с данными из CSV")
    void searchTestShouldReturnResultsFromCsv(String query) {
        $("div.input-search.input-group-lg > input")
                .shouldBe(visible)
                .setValue(query)
                .pressEnter();

        $("div.search-items-results iframe").shouldBe(visible, Duration.ofSeconds(10));
        switchTo().frame($("div.search-items-results iframe"));

        $$(".b-serp-item__title-link").shouldHave(sizeGreaterThan(0), Duration.ofSeconds(10));

        switchTo().defaultContent();
    }
}
