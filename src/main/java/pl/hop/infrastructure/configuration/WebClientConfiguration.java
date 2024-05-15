package pl.hop.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import pl.hop.infrastructure.cepik.ApiClient;
import pl.hop.infrastructure.cepik.api.PojazdyApi;
import pl.hop.infrastructure.cepik.api.SownikiApi;

@Configuration
public class WebClientConfiguration {

    @Value("${api.cepik.url}") // TODO @Value jest ze springa a nie Lomboka
    private String cepikUrl; // TODO czyli adres jaki to api ma


    @Bean //TODO tutaj mamy klienta który przyjmuje ObjectMapper i zdefiniuje ExchangeStrategies, gdzie ten
    // objectMapper ma być używany w komunikacji z naszym wewnętrznym API do zamiany jsonów na obiekty i odwrotnie
    public WebClient webClient(final ObjectMapper objectMapper) {
        final var exchangeStrategies = ExchangeStrategies
                .builder()
                .codecs(configurer -> {
                    configurer
                            .defaultCodecs()
                            .jackson2JsonEncoder(
                                    new Jackson2JsonEncoder(
                                            objectMapper,
                                            MediaType.APPLICATION_JSON));
                    configurer
                            .defaultCodecs()
                            .jackson2JsonDecoder(
                                    new Jackson2JsonDecoder(
                                            objectMapper,
                                            MediaType.APPLICATION_JSON));
                }).build();

        return WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .build();
    }

//
//        final var webClient = WebClient.builder()
//                .exchangeStrategies(strategies)
//                .build();

    @Bean
    public ApiClient apiClient(final WebClient webClient) {
        ApiClient apiClient = new ApiClient(webClient);
        apiClient.setBasePath(cepikUrl);
        return apiClient;
    }

    //TODO te dwa poniższe beany zostały stworzone bo bedziemy wstrzykiwac sobie PojazdyAPi zeby komunikować się z API
    @Bean
    public PojazdyApi pojazdyApi(final ApiClient apiClient) {
        return new PojazdyApi(apiClient);
    }

    @Bean
    public SownikiApi sownikiApi(final ApiClient apiClient) {
        return new SownikiApi(apiClient);
    }
}
