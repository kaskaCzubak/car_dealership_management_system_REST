package pl.hop.api.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.hop.api.dto.CarServiceCustomerRequestDTO;
import pl.hop.api.dto.CarServiceRequestDTO;
import pl.hop.api.dto.CarServiceRequestsDTO;
import pl.hop.api.dto.InvoiceDTO;
import pl.hop.api.dto.mapper.CarServiceRequestMapper;
import pl.hop.business.CarServiceRequestService;
import pl.hop.domain.CarServiceRequest;

import java.util.Map;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(ServiceRestController.API_SERVICE)
public class ServiceRestController {

    public static final String API_SERVICE = "/api/service";
    public static final String SERVICE_REQUEST = "/request";

    private final CarServiceRequestService carServiceRequestService;
    private final CarServiceRequestMapper carServiceRequestMapper;

    @Operation(summary = "Make a service request for a car",
            description = "This endpoint returns a list of service requests available after a new service request has been registered.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = CarServiceRequestsDTO.class
                                    )
                            )
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input",
                    content = @Content),

    })
    @PostMapping(value = SERVICE_REQUEST)
    public CarServiceRequestsDTO makeServiceRequest(
            @Valid @RequestBody CarServiceCustomerRequestDTO carServiceCustomerRequestDTO
    ) {
        CarServiceRequest serviceRequest = carServiceRequestMapper.map(carServiceCustomerRequestDTO);
        carServiceRequestService.makeServiceRequest(serviceRequest);
        return CarServiceRequestsDTO.builder()
                .carServiceRequests(getAvailableCarServiceRequests())
                .build();
    }

    private List<CarServiceRequestDTO> getAvailableCarServiceRequests() {
        return carServiceRequestService.availableServiceRequests().stream()
                .map(carServiceRequestMapper::map)
                .toList();
    }
}
