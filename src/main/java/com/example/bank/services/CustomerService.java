package com.example.bank.services;

import com.example.bank.models.Customer;
import com.example.bank.repository.CustomerRepository;
import com.example.bank.utils.ObjectMapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer create(Map<String, Object> payload) {
        Customer customer = ObjectMapperUtil.convertToObject(payload, Customer.class);
        return this.customerRepository.save(customer);
    }

    public Customer updateById(long customerId, Map<String, Object> payload) {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow();
        customer = ObjectMapperUtil.updateObject(customer, payload);
        assert customer != null;
        return this.customerRepository.save(customer);
    }

    public Customer deleteById(long customerId) {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow();
        this.customerRepository.delete(customer);
        return customer;
    }

}
