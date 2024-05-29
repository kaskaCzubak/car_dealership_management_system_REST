package pl.hop.integration.support;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Map;


public interface AuthenticationTestSupport {

//    String LOGIN = "/login";
//    String LOGOUT = "/logout";
//
//    RequestSpecification requestSpecification();
//    RequestSpecification requestSpecificationNoAuthentication();
//
//    default ValidatableResponse login(final String username, final String password) {
//        System.out.println("Próba logowania z nazwą użytkownika login: " + username); //TODO
//        return requestSpecificationNoAuthentication()
//                .params(Map.of("username", username, "password", password))
//                .post(LOGIN)
//                .then()
//                .statusCode(HttpStatus.FOUND.value());
//    }
//    default ValidatableResponse logout() {
//        return requestSpecification()
//                .post(LOGOUT)
//                .then()
//                .statusCode(HttpStatus.FOUND.value());
//    }

}
