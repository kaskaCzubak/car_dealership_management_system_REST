package pl.hop.integration;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import pl.hop.integration.configuration.AbstractIT;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HomeControllerIT extends AbstractIT {


    private final TestRestTemplate restTemplate;

    @Test
    public void thatHomePageWorksCorrectly() {
        String url = String.format("http://localhost:%s%s", port,basePath);

        String renderedPage = this.restTemplate.getForObject(url, String.class);
        Assertions.assertThat(renderedPage).contains(("Hop Car Dealer!"));
    }

    //TODO security layer and sign in
//    @Test
//    public void thatMechanicPageRequiredSigningIn() {
//        String url = String.format("http://localhost:%s%s/mechanic", port,basePath);
//
//        String renderedPage = this.restTemplate.getForObject(url, String.class);
//        Assertions.assertThat(renderedPage).contains(("Please sign in"));
//    }
}
