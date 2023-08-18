package com.kaluzny.demo.web;

import com.kaluzny.demo.domain.Automobile;

import java.util.Collection;
import java.util.List;

public interface AutomobileResource {

    Automobile saveAutomobile(Automobile automobile);

    Collection<Automobile> getAllAutomobiles();

    Automobile getAutomobileById(Long id);

    Collection<Automobile> findAutomobileByName(String name);

    Automobile refreshAutomobile(Long id, Automobile automobile);

    String removeAutomobileById(Long id);

    void removeAllAutomobiles();

    Collection<Automobile> findAutomobileByColor(String color);

    Collection<Automobile> findAutomobileByNameAndColor(String name, String color);

    Collection<Automobile> findAutomobileByColorStartsWith(String colorStartsWith, int page, int size);

    List<String> getAllAutomobilesByName();
}
