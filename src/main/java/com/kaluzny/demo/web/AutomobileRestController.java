package com.kaluzny.demo.web;

import com.kaluzny.demo.api.AutomobileResource;
import com.kaluzny.demo.domain.Automobile;
import com.kaluzny.demo.domain.AutomobileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import java.util.Collection;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class AutomobileRestController implements AutomobileResource {

    private final AutomobileRepository repository;

    @Transactional
    @PostConstruct
    public void init() {
        repository.save(new Automobile(1L, "Ford", "Blue"));
    }

    @PostMapping("/automobiles")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public Automobile saveAutomobile(@RequestBody Automobile automobile) {
        log.info("saveAutomobile() - start: automobile = {}", automobile);
        Automobile savedAutomobile = repository.save(automobile);
        log.info("saveAutomobile() - end: savedAutomobile = {}", savedAutomobile.getId());
        return savedAutomobile;
    }

    @GetMapping("/automobiles")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Collection<Automobile> getAllAutomobiles() {
        log.info("getAllAutomobiles() - start");
        Collection<Automobile> collection = repository.findAll();
        log.info("getAllAutomobiles() - end");
        return collection;
    }

    @GetMapping("/automobiles/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Automobile getAutomobileById(@PathVariable Long id) {
        log.info("getAutomobileById() - start: id = {}", id);
        Automobile receivedAutomobile = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Automobile with id = Not found"));
        log.info("getAutomobileById() - end: Automobile = {}", receivedAutomobile.getId());
        return receivedAutomobile;
    }

    @GetMapping(value = "/automobiles", params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Collection<Automobile> findAutomobileByName(@RequestParam(value = "name") String name) {
        log.info("findAutomobileByName() - start: name = {}", name);
        Collection<Automobile> collection = repository.findByName(name);
        log.info("findAutomobileByName() - end: collection = {}", collection);
        return collection;
    }

    @PutMapping("/automobiles/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public Automobile refreshAutomobile(@PathVariable("id") long id, @RequestBody Automobile automobile) {
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

    @DeleteMapping("/automobiles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void removeAutomobileById(@PathVariable Long id) {
        log.info("removeAutomobileById() - start: id = {}", id);
        repository.deleteById(id);
        log.info("removeAutomobileById() - end: id = {}", id);
    }

    @DeleteMapping("/automobiles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void removeAllAutomobiles() {
        log.info("removeAllAutomobiles() - start");
        repository.deleteAll();
        log.info("removeAllAutomobiles() - end");
    }
}
