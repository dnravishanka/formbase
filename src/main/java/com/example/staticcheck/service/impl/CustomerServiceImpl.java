package com.example.staticcheck.service.impl;


import com.example.staticcheck.dto.CustomerDto;
import com.example.staticcheck.entity.Customer;
import com.example.staticcheck.handler.AppException;
import com.example.staticcheck.repository.CustomerRepository;
import com.example.staticcheck.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public void saveCustomer(CustomerDto dto) {

      /*  Customer customer = new Customer(dto.getId(),dto.getName(), dto.getAddress(),dto.getSalary());
        customerRepository.save(customer);*/

        //   add modelMapper
        if (customerRepository.existsById(dto.getId())) {
            throw new AppException("Already such customer exist");
        }
        Customer customer = modelMapper.map(dto, Customer.class);
        customerRepository.save(customer);


    }

    @Override
    public void updateCustomer(CustomerDto dto) {
        /*
        Customer customer = new Customer(dto.getId(),dto.getName(),dto.getAddress(),dto.getSalary());
        customerRepository.save(customer);

*/
        if (!customerRepository.existsById(dto.getId())) {
            throw new AppException("No such customer to update");
        }
        //add modelMapper
        Customer customer = modelMapper.map(dto, Customer.class);
        customerRepository.save(customer);

    }

    @Override
    public void deleteCustomer(String id) {
        if (!customerRepository.existsById(id)) {
            throw new AppException("No such customer to delete");
        }
    Optional<Customer> customer= customerRepository.findAllById(id);
    customerRepository.delete(customer.get());

    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> all = customerRepository.findAll();
        if (all.isEmpty()) {
            throw new AppException("Any customer is not exist");
        }
        List<CustomerDto> dtoList=new ArrayList<>();
        for (Customer cus:all) {
            /*
            dtoList.add(new CustomerDto(cus.getId(),cus.getName(),cus.getAddress(),cus.getSalary()));*/

            //add modelMapper
            CustomerDto customerDto = modelMapper.map(cus, CustomerDto.class);
            dtoList.add(customerDto);
        }

       return dtoList;
    }

    @Override
    public CustomerDto searchCustomerByID(String id) {
        if (!customerRepository.existsById(id)) {
            throw new AppException("such customer is not exist");
        }
        Optional<Customer> customerById = customerRepository.findAllById(id);
        Customer customer = customerById.get();

        /*CustomerDto customerDto = new CustomerDto(customer.getId(),customer.getName(),customer.getAddress(),customer.getSalary());*/
        //add modelMapper
        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);

        return customerDto;
    }

    @Override
    public CustomerDto SearchCustomerByAddress(String address) {


        Optional<Customer> customerByAddress = customerRepository.findCustomerByAddress(address);
        if (customerByAddress.isEmpty()) {
            throw new AppException("There is not any customer in "+address);
        }
        Customer customer = customerByAddress.get();


//        CustomerDto customerDto = new CustomerDto(customer.getId(),customer.getName(),customer.getAddress(),customer.getSalary());
//        add modelMapper
        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
        return customerDto;
    }

    @Override
    public CustomerDto SearchCustomerByName(String name) {
        Optional<Customer> customerByName = customerRepository.findCustomerByName(name);
        if (customerByName.isEmpty()) {
            throw new AppException("There is not any customer name as " + name);
        }
        Customer customer = customerByName.get();

//        CustomerDto customerDto = new CustomerDto(customer.getId(),customer.getName(),customer.getAddress(),customer.getSalary());
//          add modelMapper

        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
        return customerDto;
    }

    @Override
    public List<CustomerDto> searchCustomersByAddress(String address) {
        List<Customer> customersByAddress1 = customerRepository.findCustomersByAddress(address);
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer cus:customersByAddress1) {
            customerDtos.add(modelMapper.map(cus,CustomerDto.class));
        }
        return customerDtos;
    }

    @Override
    public List<CustomerDto> searchCustomersByAddressContaining(String address) {
        List<Customer> cus = customerRepository.findCustomersByAddressContaining(address);
        List<CustomerDto> customerDtoList = new ArrayList<>();
        for (Customer c : cus) {
            customerDtoList.add(modelMapper.map(c, CustomerDto.class));

        }

        return customerDtoList;
    }
}
