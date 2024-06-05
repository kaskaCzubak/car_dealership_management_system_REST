package pl.hop.api.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.hop.api.dto.CarHistoryDTO;
import pl.hop.api.dto.mapper.CarMapper;
import pl.hop.business.CarService;

import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping(CarHistoryRestController.API_CAR)
public class CarHistoryRestController {

    public static final String API_CAR = "/api/car";
    public static final String CAR_VIN_HISTORY = "/{carVin}/history";

    private final CarService carService;
    private final CarMapper carMapper;

    @Operation(summary = "Get the car history by car VIN",
            description = "This endpoint returns a the repair history (including parts and services) of a car using its VIN number")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = CarHistoryDTO.class
                                    )
                            )
                    }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid car vin supplied",
                    content = @Content),
            @ApiResponse(
                    responseCode = "404",
                    description = "Car history not found",
                    content = @Content)
    })
    @GetMapping(value = CAR_VIN_HISTORY)
    public ResponseEntity<CarHistoryDTO> carHistory(
            @Parameter(description = "Car vin for history lookup")
            @PathVariable String carVin
    ) {
        if (Objects.isNull(carVin)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .ok(carMapper.map(carService.findCarHistoryByVin(carVin)));

    }
}

