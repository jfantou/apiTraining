package com.jfantou.api.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonFilter("FirstFilter")
public class SomeBean {
    private String field1;
    //@JsonIgnore
    private String field2;
    private String field3;
}
