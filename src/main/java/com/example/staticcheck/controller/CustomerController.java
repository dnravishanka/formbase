package com.example.staticcheck.controller;


import com.example.staticcheck.dto.CustomerDto;
import com.example.staticcheck.service.CustomerService;
import com.example.staticcheck.util.StandResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v/customer")
@CrossOrigin
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping
    public ResponseEntity customerSave(@RequestBody CustomerDto dto) {
        customerService.saveCustomer(dto);
        return new ResponseEntity(new StandResponse(200, "customer Successfully added", null), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity customerUpdate(@RequestBody CustomerDto dto) {
        customerService.updateCustomer(dto);
        return new ResponseEntity(new StandResponse(200, "customer updated successfully", null), HttpStatus.OK);


    }

    @DeleteMapping
    public ResponseEntity customerDelete(@RequestParam String id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity(new StandResponse(200, "customer " + id + " deleted successfully", null), HttpStatus.OK);


    }

    @GetMapping("/all")
    public ResponseEntity<CustomerDto> getAllCustomer() {
        List<CustomerDto> allCustomers = customerService.getAllCustomers();
        return new ResponseEntity(new StandResponse(200, "true", allCustomers), HttpStatus.OK);
    }

    /*
    public List<CustomerDto> getAllCustomer() {
        List<CustomerDto> allCustomers = customerService.getAllCustomers();
        return allCustomers;
    }*/
    @GetMapping("/id")
    public ResponseEntity customerSearchById(@RequestParam String id) {
        CustomerDto customerDto = customerService.searchCustomerByID(id);

        return new ResponseEntity(new StandResponse(200, "customer " + id + " is available", customerDto), HttpStatus.OK);

    }

    @GetMapping("/address")
    public ResponseEntity customerSearchByAddress(@RequestParam String address) {
        CustomerDto customerDto = customerService.SearchCustomerByAddress(address);

        return new ResponseEntity(new StandResponse(200, address + " customers are", customerDto), HttpStatus.OK);

    }

    @GetMapping("/name")
    public ResponseEntity customerSearchByName(@RequestParam String name) {
        CustomerDto customerDto = customerService.SearchCustomerByName(name);
        return new ResponseEntity(new StandResponse(200, "true", customerDto), HttpStatus.OK);
    }

    @GetMapping("/addressess")
    public List<CustomerDto> customersSearchByAddressContaining(@RequestParam String address) {
        List<CustomerDto> dtoList = customerService.searchCustomersByAddressContaining(address);
        return dtoList;
    }

}
