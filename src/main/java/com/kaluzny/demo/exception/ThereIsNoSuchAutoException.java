package com.kaluzny.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "There is no such automobile")
public class ThereIsNoSuchAutoException extends RuntimeException{
}
