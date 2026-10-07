package be.telenet.css.pegacdh.nba.controller;

import be.telenet.css.pegacdh.nba.dto.NbaDto;
import be.telenet.css.pegacdh.nba.exception.ErrorResponse;
import be.telenet.css.pegacdh.nba.mapper.NbaRequestMapper;
import be.telenet.css.pegacdh.nba.service.NbaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/customer/{customerNumber}/next-best-actions")
@Tag(name = "Retrieve NBA from PEGA")
public class CustomerNbaController {

    private final NbaService nbaService;
    private final NbaRequestMapper nbaRequestMapper;

    @GetMapping
    @Operation(summary = "Retrieve NBA from PEGA for a given customer number, channel and direction.")
    @ApiResponse(responseCode = "200", description = "If PEGA API is successfully returned response.",
            content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NbaDto.class))
            }
    )
    @ApiResponse(responseCode = "400", description = "Bad request, if missing mandatory request parameters.",
            content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            }
    )
    @ApiResponse(responseCode = "500", description = "Unable to connect PEGA API or general errors",
            content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            }
    )
    public ResponseEntity<NbaDto> nextBestAction(@PathVariable String customerNumber,
                                                 @RequestParam String channel,
                                                 @RequestParam(required = false) String contextName,
                                                 @RequestParam(required = false) String direction,
                                                 HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(
                nbaService.getNextBestAction(nbaRequestMapper.map(customerNumber, channel, contextName, direction, httpServletRequest)));
    }

}
