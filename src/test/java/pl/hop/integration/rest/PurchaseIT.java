package pl.hop.integration.rest;

import org.junit.jupiter.api.Test;
import pl.hop.api.dto.CarPurchaseDTO;
import pl.hop.api.dto.CarsToBuyDTO;
import pl.hop.api.dto.InvoiceDTO;
import pl.hop.integration.configuration.RestAssuredIntegrationTestBase;
import pl.hop.integration.support.PurchaseControllerTestSupport;
import pl.hop.util.DtoFixtures;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseIT
    extends RestAssuredIntegrationTestBase
    implements PurchaseControllerTestSupport {

        @Test
        void thatCarPurchaseWorksCorrectly() {
            // given
            CarsToBuyDTO carsToBuyDTO = findAvailableCars();
            CarPurchaseDTO carPurchaseDTO = DtoFixtures.someCarPurchaseDTO();

            // when
            InvoiceDTO invoiceDTO = purchaseCar(carPurchaseDTO);

            // then
            CarsToBuyDTO carsToBuyDTOAfterPurchase = findAvailableCars();


            assertThat(invoiceDTO.getInvoiceNumber()).isNotNull();
            assertThat(invoiceDTO.getDateTime()).isNotNull();

            var carsToBuyBeforePurchase = new ArrayList<>(carsToBuyDTO.getCarsToBuy());
            var carsToBuyAfterPurchase = new ArrayList<>(carsToBuyDTOAfterPurchase.getCarsToBuy());
            carsToBuyBeforePurchase.removeAll(carsToBuyAfterPurchase);

            assertThat(carsToBuyBeforePurchase).hasSize(1);
            assertThat(carsToBuyBeforePurchase.get(0).getVin()).isEqualTo(carPurchaseDTO.getCarVin());
        }



    }
