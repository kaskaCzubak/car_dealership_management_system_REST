package pl.hop.infrastructure.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public static ObjectMapper objectMapper() { //TODO Przez te faktyczne ustawienia na poziomie ObjectMappera ja manipuluje tym jak ma zostać wykonana zamiana jsonów na obiektyJava i odwrotnie
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .registerModule(new Jdk8Module())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false) //TODO jak tutaj damy true to wyjdzie że nasza data zostanie zwrócona jako ciąg liczb 1759320000
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL); // TODO jesli jakies pole w jsonie jest nullem wtedy go nie ma
    }
}

