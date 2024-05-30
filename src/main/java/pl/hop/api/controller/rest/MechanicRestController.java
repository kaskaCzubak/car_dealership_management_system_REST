package pl.hop.api.controller.rest;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.hop.api.dto.CarServiceMechanicProcessingUnitDTO;
import pl.hop.api.dto.CarServiceRequestDTO;
import pl.hop.api.dto.CarServiceRequestsDTO;
import pl.hop.api.dto.CepikVehicleDTO;
import pl.hop.api.dto.mapper.CarServiceRequestMapper;
import pl.hop.business.CarServiceProcessingService;
import pl.hop.business.CarServiceRequestService;
import pl.hop.domain.CarServiceProcessingRequest;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(MechanicRestController.API_MECHANIC)
public class MechanicRestController {

    public static final String API_MECHANIC = "/api/mechanic";
    public static final String AVAILABLE_SERVICE_REQUESTS = "/availableServiceRequests";
    public static final String MECHANIC_WORK_UNIT = "/workUnit";

    private final CarServiceRequestService carServiceRequestService;
    private final CarServiceProcessingService carServiceProcessingService;
    private final CarServiceRequestMapper carServiceRequestMapper;

    @Operation(summary = "Get available service requests",
    description = "This endpoint returns a list of service requests available at the dealer.")
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
                    })
    })
    @GetMapping(value = AVAILABLE_SERVICE_REQUESTS)
    public CarServiceRequestsDTO availableServiceRequests() {
        return getCarServiceRequestsDTO();
    }

    @Operation(summary = "Add mechanic work unit",
    description = " This endpoint returns a list of service requests available after the mechanic performed a work unit. " +
            "A mechanic can record the tasks performed and the parts used during the car repair.")
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
    @PostMapping(value = MECHANIC_WORK_UNIT)// TODO Mechanik wykonujący pracę będzie wysyłał request body na ten endpoint
    public CarServiceRequestsDTO mechanicPerformWorkUnit(
            @Valid
            @RequestBody CarServiceMechanicProcessingUnitDTO processingUnitDTO
    ) {
        CarServiceProcessingRequest request = carServiceRequestMapper.map(processingUnitDTO);
        carServiceProcessingService.process(request);
        return getCarServiceRequestsDTO();
    }

    private CarServiceRequestsDTO getCarServiceRequestsDTO() {
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

