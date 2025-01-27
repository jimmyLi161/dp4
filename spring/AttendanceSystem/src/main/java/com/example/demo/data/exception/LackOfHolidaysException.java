package com.example.demo.data.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * @author 毕凯斌
 * @since 1.0.0
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Your holiday balance is insufficient")
public class LackOfHolidaysException extends RuntimeException{
    public LackOfHolidaysException(){}
    public static void throwHolidaysLackEx() throws LackOfHolidaysException{
        throw new LackOfHolidaysException();
    }
}
