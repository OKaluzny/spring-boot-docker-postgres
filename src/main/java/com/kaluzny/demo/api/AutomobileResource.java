package com.kaluzny.demo.api;

import com.kaluzny.demo.domain.Automobile;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.constraints.NotNull;

@Tag(name = "Automobile", description = "the Automobile API")
public interface AutomobileResource {

    @Operation(summary = "Add a new Automobile", description = " ", tags = {"Automobile"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Automobile created",
                    content = @Content(schema = @Schema(implementation = Automobile.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Automobile already exists")})
    Automobile saveAutomobile(
            @Parameter(
                    description = "Automobile", required = true, schema = @Schema(implementation = Automobile.class))
            @NotNull @RequestBody Automobile automobile);

    @Operation(summary = "Find all Automobiles", description = " ", tags = {"Automobile"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Automobile.class))))})
    Collection<Automobile> getAllAutomobiles();

    ////
    Automobile getAutomobileById(@PathVariable Long id);

    Collection<Automobile> findAutomobileByName(@RequestParam(value = "name") String name);

    Automobile refreshAutomobile(long id, @RequestBody Automobile automobile);

    void removeAutomobileById(@PathVariable Long id);

    void removeAllAutomobiles();

}
