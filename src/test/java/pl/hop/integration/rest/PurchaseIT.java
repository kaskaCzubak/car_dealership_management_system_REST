package pl.hop.integration.rest;

import org.junit.jupiter.api.Test;
import pl.hop.api.dto.CarPurchaseDTO;
import pl.hop.api.dto.CarsToBuyDTO;
import pl.hop.api.dto.InvoiceDTO;
import pl.hop.integration.configuration.RestAssuredIntegrationTestBase;
import pl.hop.integration.support.PurchaseControllerTestSupport;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseIT
    extends RestAssuredIntegrationTestBase
    implements PurchaseControllerTestSupport {

        @Test
        void thatCarPurchaseWorksCorrectly() {
            // given
            CarsToBuyDTO carsToBuyDTO = findAvailableCars(); // TODO na poczatku wyciagamy samochody do zakupu
            CarPurchaseDTO carPurchaseDTO = someCarPurchaseDTO();

            // when
            InvoiceDTO invoiceDTO = purchaseCar(carPurchaseDTO);

            // then
            CarsToBuyDTO carsToBuyDTOAfterPurchase = findAvailableCars();
            // TODO sprawdzamy ile mamy dostepnych samochodów po zakupie (-1)

            assertThat(invoiceDTO.getInvoiceNumber()).isNotNull();
            assertThat(invoiceDTO.getDateTime()).isNotNull();
            //TODO nasz serwer odpowiada, wiec my nie wiemy jaka bedzie data czy invoiceNumber
            var carsToBuyBeforePurchase = new ArrayList<>(carsToBuyDTO.getCarsToBuy());
            var carsToBuyAfterPurchase = new ArrayList<>(carsToBuyDTOAfterPurchase.getCarsToBuy());
            carsToBuyBeforePurchase.removeAll(carsToBuyAfterPurchase);
            //TODO lista cars przed zakupem odjąc liste cars po zakupie i zostaje nam zakupiony car
            assertThat(carsToBuyBeforePurchase).hasSize(1);
            //TODO tutaj sprawdzamy czy zakupione auto ma ten VIN
            assertThat(carsToBuyBeforePurchase.get(0).getVin()).isEqualTo(carPurchaseDTO.getCarVin());
        }

        public static CarPurchaseDTO someCarPurchaseDTO() {
            return CarPurchaseDTO.buildDefaultData()
                    .withCarVin("1FT7X2B60FEA74019")
                    .withSalesmanPesel("73021314515");
        }

    }
