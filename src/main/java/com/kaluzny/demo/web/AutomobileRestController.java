package com.kaluzny.demo.web;

import com.kaluzny.demo.domain.Automobile;
import com.kaluzny.demo.domain.AutomobileRepository;
import com.kaluzny.demo.exception.AutoWasDeletedException;
import com.kaluzny.demo.exception.ThereIsNoSuchAutoException;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class AutomobileRestController implements AutomobileResource, AutomobileOpenApi {

    private final AutomobileRepository repository;

    public static double getTiming(Instant start, Instant end) {
        return Duration.between(start, end).toMillis();
    }

    @Transactional
    @PostConstruct
    public void init() {
        repository.save(new Automobile(1L, "Ford", "Green", Instant.now(), Instant.now(), true, false));
    }

    @PostMapping("/automobiles")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    //@RolesAllowed("ADMIN")
    public Automobile saveAutomobile(@Valid @RequestBody Automobile automobile) {
        log.info("saveAutomobile() - start: automobile = {}", automobile);
        Automobile savedAutomobile = repository.save(automobile);
        log.info("saveAutomobile() - end: savedAutomobile = {}", savedAutomobile.getId());
        return savedAutomobile;
    }

    @GetMapping("/automobiles")
    @ResponseStatus(HttpStatus.OK)
    //@Cacheable(value = "automobile", sync = true)
    @PreAuthorize("hasRole('ADMIN')")
    public Collection<Automobile> getAllAutomobiles() {
        log.info("getAllAutomobiles() - start");
        Collection<Automobile> collection = repository.findAll();
        log.info("getAllAutomobiles() - end");
        return collection;
    }

    @GetMapping("/automobiles/{id}")
    @ResponseStatus(HttpStatus.OK)
    //@Cacheable(value = "automobile", sync = true)
    public Automobile getAutomobileById(@PathVariable Long id) {
        log.info("getAutomobileById() - start: id = {}", id);
        Automobile receivedAutomobile = repository.findById(id)
                //.orElseThrow(() -> new EntityNotFoundException("Automobile not found with id = " + id));
                .orElseThrow(ThereIsNoSuchAutoException::new);
        if (receivedAutomobile.getDeleted()) {
            throw new AutoWasDeletedException();
        }
        log.info("getAutomobileById() - end: Automobile = {}", receivedAutomobile.getId());
        return receivedAutomobile;
    }

    @Hidden
    @GetMapping(value = "/automobiles", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    public Collection<Automobile> findAutomobileByName(@RequestParam(value = "name") String name) {
        log.info("findAutomobileByName() - start: name = {}", name);
        Collection<Automobile> collection = repository.findByName(name);
        log.info("findAutomobileByName() - end: collection = {}", collection);
        return collection;
    }

    @PutMapping("/automobiles/{id}")
    @ResponseStatus(HttpStatus.OK)
    //@CachePut(value = "automobile", key = "#id")
    public Automobile refreshAutomobile(@PathVariable Long id, @RequestBody Automobile automobile) {
        log.info("refreshAutomobile() - start: id = {}, automobile = {}", id, automobile);
        Automobile updatedAutomobile = repository.findById(id)
                .map(entity -> {
                    entity.checkColor(automobile);
                    entity.setName(automobile.getName());
                    entity.setColor(automobile.getColor());
                    entity.setUpdateDate(automobile.getUpdateDate());
                    if (entity.getDeleted()) {
                        throw new AutoWasDeletedException();
                    }
                    return repository.save(entity);
                })
                //.orElseThrow(() -> new EntityNotFoundException("Automobile not found with id = " + id));
                .orElseThrow(ThereIsNoSuchAutoException::new);
        log.info("refreshAutomobile() - end: updatedAutomobile = {}", updatedAutomobile);
        return updatedAutomobile;
    }

    @DeleteMapping("/automobiles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "automobile", key = "#id")
    public String removeAutomobileById(@PathVariable Long id) {
        log.info("removeAutomobileById() - start: id = {}", id);
        Automobile deletedAutomobile = repository.findById(id)
                .orElseThrow(ThereIsNoSuchAutoException::new);
        deletedAutomobile.setDeleted(Boolean.TRUE);
        repository.save(deletedAutomobile);
        log.info("removeAutomobileById() - end: id = {}", id);
        return "Deleted";
    }

    @Hidden
    @DeleteMapping("/automobiles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllAutomobiles() {
        log.info("removeAllAutomobiles() - start");
        repository.deleteAll();
        log.info("removeAllAutomobiles() - end");
    }

    @GetMapping(value = "/automobiles", params = {"color"})
    @ResponseStatus(HttpStatus.OK)
    public Collection<Automobile> findAutomobileByColor(
            @Parameter(description = "Name of the Automobile to be obtained. Cannot be empty.", required = true)
            @RequestParam(value = "color") String color) {
        Instant start = Instant.now();
        log.info("findAutomobileByColor() - start: time = {}", start);
        log.info("findAutomobileByColor() - start: color = {}", color);
        Collection<Automobile> collection = repository.findByColor(color);
        Instant end = Instant.now();
        log.info("findAutomobileByColor() - end: milliseconds = {}", getTiming(start, end));
        log.info("findAutomobileByColor() - end: collection = {}", collection);
        return collection;
    }

    @GetMapping(value = "/automobiles", params = {"name", "color"})
    @ResponseStatus(HttpStatus.OK)
    public Collection<Automobile> findAutomobileByNameAndColor(
            @Parameter(description = "Name of the Automobile to be obtained. Cannot be empty.", required = true)
            @RequestParam(value = "name") String name, @RequestParam(value = "color") String color) {
        log.info("findAutomobileByNameAndColor() - start: name = {}, color = {}", name, color);
        Collection<Automobile> collection = repository.findByNameAndColor(name, color);
        log.info("findAutomobileByNameAndColor() - end: collection = {}", collection);
        return collection;
    }

    @GetMapping(value = "/automobiles", params = {"colorStartsWith"})
    @ResponseStatus(HttpStatus.OK)
    public Collection<Automobile> findAutomobileByColorStartsWith(
            @RequestParam(value = "colorStartsWith") String colorStartsWith,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size) {
        log.info("findAutomobileByColorStartsWith() - start: color = {}", colorStartsWith);
        Collection<Automobile> collection = repository
                .findByColorStartsWith(colorStartsWith, PageRequest.of(page, size, Sort.by("color")));
        log.info("findAutomobileByColorStartsWith() - end: collection = {}", collection);
        return collection;
    }

    @GetMapping("/automobiles-names")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllAutomobilesByName() {
        log.info("getAllAutomobilesByName() - start");
        List<Automobile> collection = repository.findAll();
        List<String> collectionName = collection.stream()
                .map(Automobile::getName)
                .sorted()
                .collect(Collectors.toList());
        log.info("getAllAutomobilesByName() - end");
        return collectionName;
    }
}
