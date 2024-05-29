package pl.hop.api.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.hop.api.dto.CarPurchaseDTO;
import pl.hop.api.dto.CarsToBuyDTO;
import pl.hop.api.dto.InvoiceDTO;
import pl.hop.api.dto.mapper.CarPurchaseMapper;
import pl.hop.api.dto.mapper.InvoiceMapper;
import pl.hop.business.CarPurchaseService;
import pl.hop.domain.CarToBuy;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PurchaseRestController.class)
@AutoConfigureMockMvc(addFilters = false) // TODO security nadal wyłączone w tym teście
@AllArgsConstructor(onConstructor = @__(@Autowired))
class PurchaseRestControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;// TODO obiekt tej klasy służył do zamiany jsonoów na obiekty i odwrotnie


    @MockBean
    private CarPurchaseService carPurchaseService;
    @MockBean
    private CarPurchaseMapper carPurchaseMapper;
    @MockBean
    private final InvoiceMapper invoiceMapper;

// TODO
//    @Test
//    void thatAvailableCarsCanBeRetrievedCorrectly() throws Exception {
//        // given
//        List<CarToBuy> availablesCars
//
//        when(carPurchaseService.availableCars()).thenReturn(availablesCars);
//        when(carPurchaseMapper.map(any(CarToBuy.class))).thenReturn(carToBuyDTO);
//
//        // when, then
//
//        mockMvc.perform(MockMvcRequestBuilders.get(PurchaseRestController.API_PURCHASE)) //czyli wywołujemy taki endpoint z takim employeeID czyli tutaj musimy przekazać jakiś numer
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId", Matchers.is(carToBuyDTO.getEmployeeId())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(carToBuyDTO.getName())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.surname", Matchers.is(carToBuyDTO.getSurname())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.salary", Matchers.is(carToBuyDTO.getSalary()), BigDecimal.class))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.phone", Matchers.is(carToBuyDTO.getPhone())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(carToBuyDTO.getEmail())));
//
//
//    }


    @Test
    void carPurchaseWorksCorrectly() throws Exception {
        // given
        CarPurchaseDTO carPurchaseBody = CarPurchaseDTO.buildDefaultData();
        InvoiceDTO someInvoiceDTO = someInvoiceDTO();
        String requestBody = objectMapper.writeValueAsString(carPurchaseBody); //TODO efektem wywołania tego będzie json
        String responseBody = objectMapper.writeValueAsString(someInvoiceDTO);

        when(invoiceMapper.map(any())).thenReturn(someInvoiceDTO);

        // when, then
        MvcResult result = mockMvc.perform(post(PurchaseRestController.API_PURCHASE)
                        .content(requestBody) //TODO tutaj musimy podac jsona
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.invoiceNumber", Matchers.is(someInvoiceDTO.getInvoiceNumber())))
                .andExpect(jsonPath("$.dateTime", Matchers.is(someInvoiceDTO.getDateTime().toString())))
                .andReturn(); // TODO dzieki tej metodzie możemy przypisac to wywołanie do zmiennej

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(responseBody);
    }

    @Test
    void thatEmailValidationWorksCorrectly() throws Exception {
        // given
        CarPurchaseDTO carPurchaseBody = CarPurchaseDTO.buildDefaultData().withCustomerEmail("badEmail");
        String requestBody = objectMapper.writeValueAsString(carPurchaseBody);

        // when, then
        mockMvc.perform(post(PurchaseRestController.API_PURCHASE)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorId", Matchers.notNullValue())); //TODO nie mamy tutaj is() bo errorId jest losowo generowany
    }

    @ParameterizedTest
    @MethodSource
    void thatPhoneValidationWorksCorrectly(Boolean correctPhone, String phone) throws Exception {
        // given
        CarPurchaseDTO carPurchaseBody = CarPurchaseDTO.buildDefaultData().withCustomerPhone(phone);
        String requestBody = objectMapper.writeValueAsString(carPurchaseBody);

        // when, then
        if (correctPhone) {
            InvoiceDTO someInvoiceDTO = someInvoiceDTO();
            String responseBody = objectMapper.writeValueAsString(someInvoiceDTO);
            when(invoiceMapper.map(any())).thenReturn(someInvoiceDTO);

            MvcResult result = mockMvc.perform(post(PurchaseRestController.API_PURCHASE)
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.invoiceNumber", Matchers.is(someInvoiceDTO.getInvoiceNumber())))
                    .andExpect(jsonPath("$.dateTime", Matchers.is(someInvoiceDTO.getDateTime().toString())))
                    .andReturn();

            assertThat(result.getResponse().getContentAsString()).isEqualTo(responseBody);

        } else {
            mockMvc.perform(post(PurchaseRestController.API_PURCHASE)
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorId", Matchers.notNullValue()));
        }
    }

    public static Stream<Arguments> thatPhoneValidationWorksCorrectly() {
        return Stream.of(
                Arguments.of(false, ""),
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

    private static InvoiceDTO someInvoiceDTO() {
        return InvoiceDTO.builder()
                .invoiceNumber("invNumb")
                .dateTime(OffsetDateTime.of(2020, 10, 10, 10, 30, 15, 0, ZoneOffset.UTC))
                .build();
    }


}