package com.example.demo.controllers;

import com.example.demo.entities.EmployeeEntity;
import com.example.demo.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {


    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @GetMapping(path = "/health-check")
    public String sayHello(){
    return  "Hello Guys";
    }

    @GetMapping
    public List<EmployeeEntity> getAllEmp(){
        return employeeRepository.findAll();
    }

//    using path variables
    @GetMapping(path = "/employee/{id}")
    public EmployeeEntity getEmpById(@PathVariable Long id){
        return employeeRepository.findById(id).orElse(null);
    }

//    using request params
    @GetMapping(path = "/params")
    public String getAllRequestParams(@RequestParam(required = false) int id, @RequestParam(required = false) String name){
        return "params " + id +" " + name;
    }

    @PostMapping
    public EmployeeEntity registerEmp(@RequestBody EmployeeEntity employeeEntity){
        return employeeRepository.save(employeeEntity);
    }

}
