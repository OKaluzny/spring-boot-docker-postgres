package com.kaluzny.demo.web;

import com.kaluzny.demo.domain.Automobile;
import org.springframework.http.ResponseEntity;

public interface JMSPublisher {

    ResponseEntity<Automobile> pushMessage(Automobile automobile);
}
