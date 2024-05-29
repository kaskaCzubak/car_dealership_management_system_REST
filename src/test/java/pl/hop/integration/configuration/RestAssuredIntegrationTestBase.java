package pl.hop.integration.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import pl.hop.integration.support.AuthenticationTestSupport;
import pl.hop.integration.support.ControllerTestSupport;

import static org.assertj.core.api.Assertions.assertThat;


public abstract class RestAssuredIntegrationTestBase
        extends AbstractIT
        implements ControllerTestSupport, AuthenticationTestSupport {
    // TODO w tej klasie wpisaliśmy różne rzezcy które sa potrzebne żeby skonfigurować testy

    protected static WireMockServer wireMockServer;

    private String jSessionIdValue;

    @Autowired
    @SuppressWarnings("unused")
    private ObjectMapper objectMapper;

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Test //TODO to jest weryfikaja czy Spring Contex wstał poprawnie
    void contextLoaded() {
        assertThat(true).isTrue(); //ten test ma przejść zawsze, a on nam przejdzie jeżeli Context zostanie załadowany poprawnie inaczej mówiąc jak po prostu aplikacja wstanie
        //TODO Assertions.assertTrue(true, "Context loaded"); inny sposób
    }


    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(
                WireMockConfiguration.wireMockConfig()
                        .port(9999) //TODO  ustawić port który będzie odpytywany zamiast naszego zewnętrznego API i extensions aby móc używać templatingu
                        .extensions(new ResponseTemplateTransformer(false))
        );
        wireMockServer.start();
    }

//    @BeforeEach
//    void beforeEach() { //TODO ta metoda jest zrobiona przez Security
//        jSessionIdValue = login("test_user", "test")
//                .and()
//                .cookie("JSESSIONID")
//                .header(HttpHeaders.LOCATION, "http://localhost:%s%s/".formatted(port, basePath))
//                .extract()
//                .cookie("JSESSIONID");
//        //TODO po zalogowaniu wyciagniemy z odpowiedzi serwera wartość tego cookie sesyjnego,
//        // a na końcu wylogujemy się po każdym teście i uzupełnimy się że to cookie będzie puste
//    }


//    @AfterEach
//    void afterEach() {
//        //TODO ta część jest zrobiona przez Security
//        logout()
//                .and()
//                .cookie("JSESSIONID", "");
//        jSessionIdValue = null;
//        wireMockServer.resetAll();
//    }

    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }

    public RequestSpecification requestSpecification() {
        return restAssuredBase()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .cookie("JSESSIONID", jSessionIdValue);
    }

    //TODO ta metoda jest zrobiona przez Security
    public RequestSpecification requestSpecificationNoAuthentication() {
        return restAssuredBase();
    }

    public RequestSpecification restAssuredBase() {
        return RestAssured
                .given()
                .config(getConfig())
                .basePath(basePath)
                .port(port)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);
    }

    private RestAssuredConfig getConfig() { //TODO metoda wykorzytywana po to żeby naszego objectMappera wykorzystać do mapowania jsonow na klasy i odwrotnie
        return RestAssuredConfig
                .config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> objectMapper));
    }
}
