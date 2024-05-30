package pl.hop.infrastructure.configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.hop.CarDealershipApplication;

@Configuration
public class SpringDocConfiguration {
    //TODO konfiguracja aby stworzyć dokumentacje OpenApi w Swagger

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("default")
                .pathsToMatch("/**")
                .packagesToScan(CarDealershipApplication.class.getPackageName())
                .build();
    }
    @Bean
    public OpenAPI springDocOpenApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Car dealership application")
                        .description("This API enables users to carry out a variety of operations related to car services, purchases, mechanic tasks, and accessing vehicle data. Moreover, the application is integrated with another API called CEPIK, allowing users to retrieve random vehicles from it.")
                        .contact(contact())
                        .version("1.0"));
    }
    private Contact contact() {
        return new Contact()
                .name("Katarzyna Czubak")
                .email("k.czubak00@gmail.com");
    }
}
