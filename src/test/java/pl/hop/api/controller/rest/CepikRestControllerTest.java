package pl.hop.api.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import pl.hop.api.dto.CepikVehicleDTO;
import pl.hop.api.dto.mapper.CepikVehicleMapperDto;
import pl.hop.business.CepikService;
import pl.hop.util.DtoFixtures;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CepikRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class CepikRestControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    @SuppressWarnings("unused")
    private CepikService cepikService;
    @MockBean
    @SuppressWarnings("unused")
    private CepikVehicleMapperDto cepikVehicleMapper;

    @Test
    void thatCepikRandomVehicleWorksCorrectly() throws Exception {
        // given
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("firstRegistrationDateFrom", LocalDate.of(2022, 10, 10).toString());
        parameters.add("firstRegistrationDateTo", LocalDate.of(2023, 10, 10).toString());
        CepikVehicleDTO cepikVehicle = DtoFixtures.someCepikVehicle();
        String responseBody = objectMapper.writeValueAsString(cepikVehicle);

        when(cepikVehicleMapper.map(any())).thenReturn(cepikVehicle);

        // when, then
        MvcResult result = mockMvc.perform(get(CepikRestController.API_CEPIK + CepikRestController.CEPIK_RANDOM)
                        .params(parameters))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cepikId", is(cepikVehicle.getCepikId())))
                .andExpect(jsonPath("$.brand", is(cepikVehicle.getBrand())))
                .andExpect(jsonPath("$.model", is(cepikVehicle.getModel())))
                .andExpect(jsonPath("$.type", is(cepikVehicle.getType())))
                .andExpect(jsonPath("$.engineCapacity", is(cepikVehicle.getEngineCapacity()), BigDecimal.class))
                .andExpect(jsonPath("$.weight", is(cepikVehicle.getWeight())))
                .andExpect(jsonPath("$.fuel", is(cepikVehicle.getFuel())))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(responseBody);
    }


}