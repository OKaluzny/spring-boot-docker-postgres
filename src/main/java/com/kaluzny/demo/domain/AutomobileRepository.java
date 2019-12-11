package com.kaluzny.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AutomobileRepository extends JpaRepository<Automobile, Long> {
    List<Automobile> findByName(String name);
}
