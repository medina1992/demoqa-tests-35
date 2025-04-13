import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SimpleJUnitTest {

    private int getResult() {
        return 0;
    }

    @BeforeAll
     static void beforeAll(){
        System.out.println("###   beforeAll()\n");
    }
    @AfterAll
    static void afterAll(){
        System.out.println("###   afterAll()\n");
    }


    int result;
    @BeforeEach
    void beforeEach(){
        System.out.println("###    beforeEach()");
        result = getResult();
    }

    @AfterEach
    void afterEach(){
        System.out.println("###    AfterEach()\n");
        result = 0;
    }



    @Test
    void firstTest() {
        int result = getResult();
        System.out.println("###    firstTest()");
        Assertions.assertTrue(3>2);
    }
    @Test
    void secondTest() {
        System.out.println("###    secondTest()");
        Assertions.assertTrue(3>2);
    }
    @Test
    void thirdTest() {
        System.out.println("###    thirdTest()");
        Assertions.assertTrue(3>2);
    }
}




