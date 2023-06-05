package com.example.staticcheck.repository;


import com.example.staticcheck.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findAllById(String id);
    Optional<Customer> findCustomerByAddress(String address);

    Optional<Customer> findCustomerByName(String name);
    List<Customer> findCustomersByAddress(String address);

    List<Customer> findCustomersByAddressIsLike(String address);
    List<Customer> findCustomersByAddressContaining(String address);

}
