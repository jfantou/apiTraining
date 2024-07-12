package com.jfantou.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;

    @Size(min = 4, message = "Name should have at least 4 characters")
    @JsonProperty("user_name")
    private String name;

    @Past(message = "Birthdate should be in the past")
    private LocalDate birthDate;
}
