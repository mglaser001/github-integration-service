package com.branch_app.demo.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ApiException(String message, Throwable throwable, int status, ZonedDateTime timeStamp) {
}
