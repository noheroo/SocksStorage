package ru.skypro.socksstorage.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.skypro.socksstorage.dto.OperateSocksDto;
import ru.skypro.socksstorage.dto.SocksDto;
import ru.skypro.socksstorage.service.SocksService;

@RestController
@RequestMapping("/api/socks")
@RequiredArgsConstructor
@Validated
public class SocksController {
    private final SocksService socksService;

    /**
     * Get quantity of socks from storage filtered by cotton part(can use 3 operation)
     * Use method of service {@link SocksService#getQuantityOfSocks(String, String, Byte)}
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Got quantity of socks"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )})
    @GetMapping
    public ResponseEntity<String> getQuantityOfSocks(@RequestParam @NotBlank @Size(min = 3, max = 50) String color,
                                                     @RequestParam @NotBlank String operation,
                                                     @RequestParam @Min(0) @Max(100) Byte cottonPart) {
        return new ResponseEntity<>(socksService.getQuantityOfSocks(color, operation, cottonPart), HttpStatus.OK);
    }

    /**
     * Increase quantity of existed socks in storage or add new socks to storage if socks are not existed
     * Use method of service {@link SocksService#addSocks(OperateSocksDto)}
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Income of socks was successful",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SocksDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )})
    @PostMapping("/income")
    public ResponseEntity<SocksDto> addSocks(@RequestBody @Valid OperateSocksDto incomeSocksDto) {
        return new ResponseEntity<>(socksService.addSocks(incomeSocksDto), HttpStatus.OK);
    }

    /**
     * Decrease quantity of existed socks in storage if socks are existed
     * Use method of service {@link SocksService#deleteSocks(OperateSocksDto)}
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Outcome of socks was successful",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SocksDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )})
    @PostMapping("/outcome")
    public ResponseEntity<SocksDto> deleteSocks(@RequestBody @Valid OperateSocksDto outcomeSocksDto) {
        return new ResponseEntity<>(socksService.deleteSocks(outcomeSocksDto), HttpStatus.OK);
    }
}
