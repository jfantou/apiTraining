package com.jfantou.api.controller;

import com.jfantou.api.model.Person;
import com.jfantou.api.model.PersonV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @GetMapping(value = "/v1/person")
    public Person getV1Person(){
        return new Person("test jerone");
    }

    @GetMapping(value = "/v2/person")
    public PersonV2 getV2Person(){
        return new PersonV2("jerome", "test");
    }

    @GetMapping(value = "/person", params = "version=1")
    public Person getPersonParam1(){
        return new Person("test jerone");
    }
    @GetMapping(value = "/person", params = "version=2")
    public PersonV2 getPersonParam2(){
        return new PersonV2("jerome", "test");
    }

    @GetMapping(value = "/person", headers = "X-API-VERSION=1")
    public Person getPersonHeader1(){
        return new Person("test jerone");
    }
    @GetMapping(value = "/person", headers = "X-API-VERSION=2")
    public PersonV2 getPersonHeader2(){
        return new PersonV2("jerome", "test");
    }
}
