package pl.hop.api.controller;

import lombok.AllArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import pl.hop.api.dto.CarPurchaseDTO;
import pl.hop.api.dto.mapper.CarMapper;
import pl.hop.api.dto.mapper.CarPurchaseMapper;
import pl.hop.business.CarPurchaseService;
import pl.hop.domain.Invoice;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@WebMvcTest(controllers = PurchaseController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class PurchaseControllerWebMvcTest {

    private MockMvc mockMvc;


    @MockBean
    private CarPurchaseService carPurchaseService;
    @MockBean
    private CarPurchaseMapper carPurchaseMapper;
    @MockBean
    private CarMapper carMapper;

    @Test
    void carPurchasePageWorksCorrectly() throws Exception { //GetMapping
        //given, when, then
        mockMvc.perform(MockMvcRequestBuilders.get(PurchaseController.PURCHASE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("availableCarDTOs", "availableCarVins", "availableSalesmanPesels", "carPurchaseDTO"))
                .andExpect(MockMvcResultMatchers.view().name("car_purchase"));

    }

    @Test
    void makeCarPurchaseWorksCorrectly() throws Exception {
        //given
        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        CarPurchaseDTO carPurchaseDTO = CarPurchaseDTO.buildDefaultData();
        asMap(carPurchaseDTO).forEach(parameters::add);

        Invoice expectedInvoice = Invoice.builder()
                .invoiceNumber("test")
                .build();
        Mockito.when(carPurchaseService.purchase(Mockito.any())).thenReturn(expectedInvoice);
        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post(PurchaseController.PURCHASE).params(parameters))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("invoiceNumber"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("customerName"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("customerSurname"))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("existingCustomerEmail"))
                .andExpect(MockMvcResultMatchers.view().name("car_purchase_done"));
    }

    private Map<String, String> asMap(CarPurchaseDTO carPurchaseDTO) {
        Map<String, String> result = new HashMap<>();
        Optional.ofNullable(carPurchaseDTO.getCustomerName()).ifPresent(value -> result.put("customerName", value));
        Optional.ofNullable(carPurchaseDTO.getCustomerSurname()).ifPresent(value -> result.put("customerSurname", value));
        Optional.ofNullable(carPurchaseDTO.getCustomerPhone()).ifPresent(value -> result.put("customerPhone", value));
        Optional.ofNullable(carPurchaseDTO.getCustomerEmail()).ifPresent(value -> result.put("customerEmail", value));
        Optional.ofNullable(carPurchaseDTO.getCustomerAddressCountry()).ifPresent(value -> result.put("customerAddressCountry", value));
        Optional.ofNullable(carPurchaseDTO.getCustomerAddressCity()).ifPresent(value -> result.put("customerAddressCity", value));
        Optional.ofNullable(carPurchaseDTO.getCustomerAddressPostalCode()).ifPresent(value -> result.put("customerAddressPostalCode", value));
        Optional.ofNullable(carPurchaseDTO.getCustomerAddressStreet()).ifPresent(value -> result.put("customerAddressStreet", value));
        Optional.ofNullable(carPurchaseDTO.getCarVin()).ifPresent(value -> result.put("carVin", value));
        Optional.ofNullable(carPurchaseDTO.getSalesmanPesel()).ifPresent(value -> result.put("salesmanPesel", value));
        return result;
    }

    @Test
    void thatEmailValidationWorksCorrectly() throws Exception {
        //given
        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        CarPurchaseDTO carPurchaseDTO = CarPurchaseDTO.buildDefaultData();
        Map<String, String> parametersMap = asMap(carPurchaseDTO);
        String badEmail = "badEmail";
        parametersMap.put("customerEmail", badEmail);
        parametersMap.forEach(parameters::add);
        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post(PurchaseController.PURCHASE).params(parameters))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"))
                .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", Matchers.containsString(badEmail)))
                .andExpect(MockMvcResultMatchers.view().name("error"));
    }

    @ParameterizedTest
    @MethodSource
    void thatPhoneValidationWorksCorrectly(Boolean correctPhonePattern, String phone) throws Exception {
        //given
        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        CarPurchaseDTO carPurchaseDTO = CarPurchaseDTO.buildDefaultData();
        Map<String, String> parametersMap = asMap(carPurchaseDTO);
        parametersMap.put("customerPhone", phone);
        parametersMap.forEach(parameters::add);

        //when, then
        if (correctPhonePattern) {
            Invoice expectedInvoice = Invoice.builder()
                    .invoiceNumber("test")
                    .build();
            Mockito.when(carPurchaseService.purchase(Mockito.any())).thenReturn(expectedInvoice);

            mockMvc.perform(MockMvcRequestBuilders.post(PurchaseController.PURCHASE).params(parameters))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.model().attributeExists("invoiceNumber")) //jakie atrybuty powinny istnieć w modelu
                    .andExpect(MockMvcResultMatchers.model().attributeExists("customerName")) //jakie atrybuty powinny istnieć w modelu
                    .andExpect(MockMvcResultMatchers.model().attributeExists("customerSurname")) //jakie atrybuty powinny istnieć w modelu
                    .andExpect(MockMvcResultMatchers.view().name("car_purchase_done"));
        } else {
            mockMvc.perform(MockMvcRequestBuilders.post(PurchaseController.PURCHASE).params(parameters))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.model().attributeExists("errorMessage"))
                    .andExpect(MockMvcResultMatchers.model().attribute("errorMessage", Matchers.containsString(phone)))
                    .andExpect(MockMvcResultMatchers.view().name("error"));
        }
    }


    public static Stream<Arguments> thatPhoneValidationWorksCorrectly() {
        return Stream.of(
                Arguments.of(false, "+48 504 203 260@@"),
                Arguments.of(false, "+48.504.203.260"),
                Arguments.of(false, "+55(123) 456-78-90-"),
                Arguments.of(false, "+55(123) - 456-78-90"),
                Arguments.of(false, "504.203.260"),
                Arguments.of(false, " "),
                Arguments.of(false, "-"),
                Arguments.of(false, "()"),
                Arguments.of(false, "() + ()"),
                Arguments.of(false, "(21 7777"),
                Arguments.of(false, "+48 (21)"),
                Arguments.of(false, "+"),
                Arguments.of(false, " 1"),
                Arguments.of(false, "1"),
                Arguments.of(false, "+48 (12) 504 203 260"),
                Arguments.of(false, "+48 (12) 504-203-260"),
                Arguments.of(false, "+48(12)504203260"),
                Arguments.of(false, "555-5555-555"),

                Arguments.of(true, "+48 504 203 260")
        );
    }

}