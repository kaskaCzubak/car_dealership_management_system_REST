package pl.hop.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import pl.hop.infrastructure.database.entity.CarToBuyEntity;
import pl.hop.integration.configuration.PersistenceContainerTestConfiguration;
import pl.hop.util.EntityFixtures;

import java.util.List;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CarToBuyJpaRepositoryTest {

    private CarToBuyJpaRepository carToBuyJpaRepository;

    @Test
    void thatCarCanBeSavedCorrectly() {
        //given
        List<CarToBuyEntity> cars = List.of(
                EntityFixtures.somaCar1(),
                EntityFixtures.somaCar2(),
                EntityFixtures.somaCar3());
        carToBuyJpaRepository.saveAllAndFlush(cars);
        //when
        List<CarToBuyEntity> availableCars = carToBuyJpaRepository.findAvailableCars();
        //then
        Assertions.assertThat(availableCars).hasSize(9);
    }

    @Test //TODO
    void findByVin() {
    }
}