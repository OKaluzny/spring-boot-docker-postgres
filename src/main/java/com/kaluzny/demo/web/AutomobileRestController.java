package com.kaluzny.demo.web;

import com.kaluzny.demo.domain.Automobile;
import com.kaluzny.demo.domain.AutomobileRepository;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Automobile", description = "the Automobile API")
public class AutomobileRestController {

    private final AutomobileRepository repository;

    @Transactional
    @PostConstruct
    public void init() {
        repository.save(new Automobile(1L, "Ford", "Green"));
    }

    @Operation(summary = "Add a new Automobile", description = "endpoint for creating an entity", tags = {"Automobile"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Automobile created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Automobile already exists")})
    @PostMapping("/automobiles")
    @ResponseStatus(HttpStatus.CREATED)
    public Automobile saveAutomobile(
            @Parameter(description = "Automobile", required = true) @NotNull @RequestBody Automobile automobile) {
        log.info("saveAutomobile() - start: automobile = {}", automobile);
        Automobile savedAutomobile = repository.save(automobile);
        log.info("saveAutomobile() - end: savedAutomobile = {}", savedAutomobile.getId());
        return savedAutomobile;
    }

    @Operation(summary = "Find all Automobiles", description = " ", tags = {"Automobile"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Automobile.class))))})
    @GetMapping("/automobiles")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Automobile> getAllAutomobiles() {
        log.info("getAllAutomobiles() - start");
        Collection<Automobile> collection = repository.findAll();
        log.info("getAllAutomobiles() - end");
        return collection;
    }

    @Operation(summary = "Find automobile by ID", description = "Returns a single automobile", tags = {"Automobile"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Automobile.class))),
            @ApiResponse(responseCode = "404", description = "Automobile not found")})
    @GetMapping("/automobiles/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Automobile getAutomobileById(
            @Parameter(description = "Id of the Automobile to be obtained. Cannot be empty.", required = true)
            @PathVariable Long id) {
        log.info("getAutomobileById() - start: id = {}", id);
        Automobile receivedAutomobile = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Automobile with id = Not found"));
        log.info("getAutomobileById() - end: Automobile = {}", receivedAutomobile.getId());
        return receivedAutomobile;
    }

    @Hidden
    @Operation(summary = "Find automobile by name", description = " ", tags = {"Automobile"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Automobile.class))))})
    @GetMapping(value = "/automobiles", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    public Collection<Automobile> findAutomobileByName(
            @Parameter(description = "Name of the Automobile to be obtained. Cannot be empty.", required = true)
            @RequestParam(value = "name") String name) {
        log.info("findAutomobileByName() - start: name = {}", name);
        Collection<Automobile> collection = repository.findByName(name);
        log.info("findAutomobileByName() - end: collection = {}", collection);
        return collection;
    }

    @Operation(summary = "Update an existing Automobile", description = "need to fill", tags = {"Automobile"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Automobile not found"),
            @ApiResponse(responseCode = "405", description = "Validation exception")})
    @PutMapping("/automobiles/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Automobile refreshAutomobile(
            @Parameter(description = "Id of the Automobile to be update. Cannot be empty.", required = true)
            @PathVariable Long id,
            @Parameter(description = "Automobile to update.", required = true)
            @RequestBody Automobile automobile) {
        log.info("refreshAutomobile() - start: id = {}, automobile = {}", id, automobile);
        Automobile updatedAutomobile = repository.findById(id)
                .map(entity -> {
                    entity.setName(automobile.getName());
                    entity.setColor(automobile.getColor());
                    return repository.save(entity);
                })
                .orElseThrow(() -> new EntityNotFoundException("Automobile with id = Not found"));
        log.info("refreshAutomobile() - end: updatedAutomobile = {}", updatedAutomobile);
        return updatedAutomobile;
    }

    @Operation(summary = "Deletes a Automobile", description = "need to fill", tags = {"Automobile"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Automobile not found")})
    @DeleteMapping("/automobiles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAutomobileById(
            @Parameter(description = "Id of the Automobile to be delete. Cannot be empty.", required = true)
            @PathVariable Long id) {
        log.info("removeAutomobileById() - start: id = {}", id);
        repository.deleteById(id);
        log.info("removeAutomobileById() - end: id = {}", id);
    }

    @Hidden
    @DeleteMapping("/automobiles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllAutomobiles() {
        log.info("removeAllAutomobiles() - start");
        repository.deleteAll();
        log.info("removeAllAutomobiles() - end");
    }
}
