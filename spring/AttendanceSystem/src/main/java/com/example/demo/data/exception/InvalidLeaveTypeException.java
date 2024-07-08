package com.example.demo.data.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * @author 毕凯斌
 * @since 1.0.0
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid leave type")
public class InvalidLeaveTypeException extends RuntimeException
{
}
