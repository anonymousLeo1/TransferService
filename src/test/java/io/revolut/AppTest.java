package io.revolut;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static io.restassured.RestAssured.*;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import org.jooby.Jooby;
import org.jooby.test.JoobyRule;
import org.jooby.test.MockRouter;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import java.math.BigDecimal;
import java.math.BigInteger;


import io.revolut.api.TransferService;
import io.revolut.domain.CachedAccountRepository;
import io.revolut.domain.model.Balance;
import io.revolut.domain.model.Account;
import io.revolut.api.RequestPacket;
import io.revolut.domain.AccountRepository;

/**
 * @author jooby generator
 */
public class AppTest {

    private static App testApp = new App();

    @ClassRule
    public static JoobyRule bootstrap = new JoobyRule(testApp);


    @BeforeClass
    public static void config() {
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL));
    }

    @Before
    public void clearCachedRepo() {
        testApp.require(CachedAccountRepository.class).clear();
    }

    @Test
    public void testGetAccountsSize() {
        setupMockAccount("INR1");
        setupMockAccount("INR2");
        setupMockAccount("INR3");
        setupMockAccount("INR4");

        when()
                .get("/accounts")
                .then()
                .body("$", hasSize(4));
    }
    @Test
    public void testGetAccount() {

        setupMockAccount("INR123243");

        when()
        .get("/accounts/INR123243")
        .then()
        .body("accountNumber", is("INR123243"));
    }

    @Test
    public void testNonExistingAccount() {

        setupMockAccount("INR01000000029");

        when()
        .get("/accounts/INR010000000112")
        .then()
        .statusCode(404);
    }

    private void setupMockAccount(String accNum) {

        Account test = new Account(accNum);
        test.credit("INR", new BigDecimal("100"));
        testApp.require(AccountRepository.class).save(test);
    }


    @Test
    public void testValidTransfer() {

        setupMockAccount("INR0000000102");
        setupMockAccount("INR1000000105");
        given().
        when()
        .body(new RequestPacket("INR0000000102", "INR1000000105",new BigDecimal("2"), "INR"))
        .contentType("application/json")
        .post("/accounts/transfer")
        .then().statusCode(200);

    }
    @Test
    public void testSameAccountTransfer() {

        setupMockAccount("INR1000000105");
        given().
        when()
        .body(new RequestPacket("INR1000000105", "INR1000000105",new BigDecimal("2"), "INR"))
        .contentType("application/json")
        .post("/accounts/transfer")
        .then().statusCode(400);

    }
    @Test
    public void testInSufficientAccountTransfer() {

        setupMockAccount("INR1000000105");
        setupMockAccount("INR0002000002");
        given().
        when()
        .body(new RequestPacket("INR1000000105", "INR0002000002",new BigDecimal("1000000000"), "INR"))
        .contentType("application/json")
        .post("/accounts/transfer")
        .then().statusCode(400);

    }















}