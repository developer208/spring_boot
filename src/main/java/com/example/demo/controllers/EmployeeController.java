package com.example.demo.controllers;

import com.example.demo.dto.EmployeeDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @GetMapping(path = "/health-check")
    public String sayHello(){
    return  "Hello Guys";
    }

//    using path variables
    @GetMapping(path = "/employee/{id}")
    public EmployeeDto getEmpById(@PathVariable int id){
        return new EmployeeDto(id,"Vedang Mule","@Google208");
    }

//    using request params
    @GetMapping(path = "/params")
    public String getAllRequestParams(@RequestParam(required = false) int id, @RequestParam(required = false) String name){
        return "params " + id +" " + name;
    }

}
