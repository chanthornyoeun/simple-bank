package com.example.bank.controllers;

import com.example.bank.models.Customer;
import com.example.bank.repository.CustomerRepository;
import com.example.bank.services.CustomerService;
import com.example.bank.utils.ResponseDTO;
import com.example.bank.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    CustomerController(CustomerRepository customerRepository, CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    @GetMapping
    ResponseDTO list() {
        Iterable<Customer> customers = this.customerRepository.findAll();
        long totalRecord = this.customerRepository.count();
        return ResponseUtil.list(customers, totalRecord, true, "List all customers");
    }

    @GetMapping("{id}")
    ResponseDTO get(@PathVariable long id) {
        Customer customer = this.customerRepository.findById(id).orElse(null);
        return ResponseUtil.object(customer, true, "Get customer by id: " + id);
    }

    @PostMapping
    ResponseDTO save(@RequestBody Customer payload) {
        try {
            Customer customer = this.customerService.create(payload);
            return ResponseUtil.object(customer, true, "Customer has been created.");
        } catch (Exception ex) {
            LOGGER.error("Error while creating customer: " + ex.getMessage());
            return ResponseUtil.object(null, false, ex.getMessage());
        }
    }

    @PutMapping("{id}")
    ResponseDTO update(@PathVariable long id, @RequestBody Customer payload) {
        try {
            Customer customer = this.customerService.updateById(id, payload);
            return ResponseUtil.object(customer, true, "Customer has been updated.");
        } catch (Exception ex) {
            LOGGER.error("Error while updating customer: " + id + " " + ex.getMessage());
            return ResponseUtil.object(null, false, ex.getMessage());
        }
    }

    @DeleteMapping("{id}")
    ResponseDTO delete(@PathVariable long id) {
        try {
            Customer customer = this.customerService.deleteById(id);
            return ResponseUtil.object(customer, true, "Customer has been deleted.");
        } catch (Exception ex) {
            LOGGER.error("Error while deleting customer: " + id + " " + ex.getMessage());
            return ResponseUtil.object(null, false, ex.getMessage());
        }
    }

}
