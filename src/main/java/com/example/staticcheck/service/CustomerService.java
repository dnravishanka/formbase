package com.example.staticcheck.service;


import com.example.staticcheck.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    void saveCustomer(CustomerDto dto);
    void updateCustomer(CustomerDto dto);
    void deleteCustomer(String id);
    List<CustomerDto> getAllCustomers();
    CustomerDto searchCustomerByID(String id);
    CustomerDto SearchCustomerByAddress(String address);
    CustomerDto SearchCustomerByName(String name);
    List<CustomerDto> searchCustomersByAddress(String address);

    List<CustomerDto> searchCustomersByAddressContaining(String address);

}
