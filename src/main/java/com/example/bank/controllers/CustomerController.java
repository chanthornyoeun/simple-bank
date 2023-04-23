package com.example.bank.controllers;

import com.example.bank.models.Customer;
import com.example.bank.repository.CustomerRepository;
import com.example.bank.services.CustomerService;
import com.example.bank.utils.ResponseDTO;
import com.example.bank.utils.ResponseUtil;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

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
    ResponseDTO save(@RequestBody Map<String, Object> payload) {
        Customer customer = this.customerService.create(payload);
        return ResponseUtil.object(customer, true, "Customer has been created.");
    }

    @PutMapping("{id}")
    ResponseDTO update(@PathVariable long id, @RequestBody Map<String, Object> payload) throws JsonMappingException {
        Customer customer = this.customerService.updateById(id, payload);
        return ResponseUtil.object(customer, true, "Customer has been updated.");
    }

    @DeleteMapping("{id}")
    ResponseDTO delete(@PathVariable long id) {
        Customer customer = this.customerService.deleteById(id);
        return ResponseUtil.object(customer, true, "Customer has been deleted.");
    }

}
