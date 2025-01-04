package ru.asocial.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Size;
import org.openapitools.api.CheckDealApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/checker")
public class CheckerApiController implements CheckDealApi {

    private static final Logger log = LoggerFactory.getLogger(CheckerApiController.class);

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }

    /**
     * GET /check-deal : Check Deal
     *
     * @param dealDate  (optional)
     * @param investorCode  (optional)
     * @param securityCode  (optional)
     * @return Successful response (status code 200)
     */
    @Operation(
            operationId = "checkDeal",
            summary = "Check Deal",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/check-deal",
            produces = { "application/json" }
    )
    @Override
    public ResponseEntity<String> checkDeal(
            @Parameter(name = "deal-date", description = "", in = ParameterIn.HEADER) @RequestHeader(value = "deal-date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dealDate,
            @Size(min = 5, max = 7) @Parameter(name = "investor-code", description = "", in = ParameterIn.HEADER) @RequestHeader(value = "investor-code", required = false) String investorCode,
            @Size(min = 10, max = 10) @Parameter(name = "security-code", description = "", in = ParameterIn.HEADER) @RequestHeader(value = "security-code", required = false) String securityCode) {
        if (dealDate == null || investorCode == null || securityCode == null) {
            log.debug("check deal: bad request");
            return ResponseEntity.badRequest().body("Bad request");
        }

        LocalDate now = LocalDate.now();
        if (dealDate.isAfter(now) || dealDate.isBefore(now.minusDays(3))) {
            log.debug("check deal: INVALID DATE");
            return ResponseEntity.ok("INVALID DATE");
        }

        List<String> supportedSecurityCodes = Arrays.asList("5555544444", "6655544444", "1111111777");
        if (!supportedSecurityCodes.contains(securityCode)) {
            log.debug("check deal: UNSUPPORTED SECURITY CODE");
            return ResponseEntity.ok("UNSUPPORTED SECURITY CODE");
        }

        log.debug("check deal ok");
        return ResponseEntity.ok("OK");
    }

}
