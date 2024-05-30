package pl.hop.api.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.hop.api.dto.CarPurchaseDTO;
import pl.hop.api.dto.CarServiceRequestsDTO;
import pl.hop.api.dto.CarsToBuyDTO;
import pl.hop.api.dto.InvoiceDTO;
import pl.hop.api.dto.mapper.CarPurchaseMapper;
import pl.hop.api.dto.mapper.InvoiceMapper;
import pl.hop.business.CarPurchaseService;
import pl.hop.domain.CarPurchaseRequest;
import pl.hop.domain.Invoice;

@RestController
@AllArgsConstructor
@RequestMapping(PurchaseRestController.API_PURCHASE)
public class PurchaseRestController {

     public static final String API_PURCHASE = "/api/purchase";

     private final CarPurchaseService carPurchaseService;
     private final CarPurchaseMapper carPurchaseMapper;
     private final InvoiceMapper invoiceMapper;

     @Operation(summary = "Get available cars",
             description = " This endpoint returns a list of cars available at the dealer.")
     @ApiResponses(value = {
             @ApiResponse(
                     responseCode = "200",
                     description = "Successful operation",
                     content = {
                             @Content(
                                     mediaType = "application/json",
                                     schema = @Schema(
                                             implementation = CarsToBuyDTO.class
                                     )
                             )
                     }),
             @ApiResponse(
                     responseCode = "400",
                     description = "Invalid input",
                     content = @Content),

     })
     @GetMapping
     public CarsToBuyDTO carsPurchaseData() {
          return CarsToBuyDTO.builder()
                  .carsToBuy(carPurchaseService.availableCars().stream() //TODO tutaj potrzebuje przekazać moje samochody czyli będę potrzebować serwisu i pozniej mappera
                          .map(car -> carPurchaseMapper.map(car)) // TODO a->carPurchaseMapper.map(a)
                          .toList())
                  .build();
     }

     @Operation(summary = "Make a purchase for a car",
             description = "This endpoint returns invoice after the purchase has been made.")
     @ApiResponses(value = {
             @ApiResponse(
                     responseCode = "200",
                     description = "Successful operation",
                     content = {
                             @Content(
                                     mediaType = "application/json",
                                     schema = @Schema(
                                             implementation = InvoiceDTO.class
                                     )
                             )
                     }),
             @ApiResponse(
                     responseCode = "400",
                     description = "Invalid input",
                     content = @Content),

     })
     @PostMapping
     public InvoiceDTO makePurchase(
             @Valid @RequestBody CarPurchaseDTO carPurchaseDTO
     ) {
          CarPurchaseRequest request = carPurchaseMapper.map(carPurchaseDTO);
          Invoice invoice = carPurchaseService.purchase(request);
          return invoiceMapper.map(invoice);
     }

}