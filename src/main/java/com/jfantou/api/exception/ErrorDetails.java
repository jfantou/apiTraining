package com.jfantou.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class ErrorDetails {
    private LocalDate timestamp;
    private String message;
    private String details;
}
