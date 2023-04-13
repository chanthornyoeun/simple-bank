package com.example.bank.services;

import com.example.bank.models.Customer;
import com.example.bank.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(Customer payload) {
        Customer customer = new Customer();
        customer.setFirstName(payload.getFirstName());
        customer.setLastName(payload.getLastName());
        customer.setPhone(payload.getPhone());
        customer.setEmail(payload.getEmail());
        customer.setAddress(payload.getAddress());
        return this.customerRepository.save(customer);
    }

    public Customer updateById(long customerId, Customer payload) {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow();
        customer.setFirstName(payload.getFirstName());
        customer.setLastName(payload.getLastName());
        customer.setPhone(payload.getPhone());
        customer.setEmail(payload.getEmail());
        customer.setAddress(payload.getAddress());
        return this.customerRepository.save(customer);
    }

    public Customer deleteById(long customerId) {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow();
        this.customerRepository.delete(customer);
        return customer;
    }

}
