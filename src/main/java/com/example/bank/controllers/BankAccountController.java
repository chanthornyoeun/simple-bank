package com.example.bank.controllers;

import com.example.bank.models.BankAccount;
import com.example.bank.models.Customer;
import com.example.bank.repository.AccountRepository;
import com.example.bank.services.AccountService;
import com.example.bank.utils.ResponseDTO;
import com.example.bank.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/accounts")
public class BankAccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountController.class);
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    BankAccountController(AccountRepository accountRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    @GetMapping
    ResponseDTO list(@RequestParam long customerId) {
        Customer customer = new Customer(customerId);
        Iterable<BankAccount> customers = this.accountRepository.findAllByCustomer(customer);
        long totalRecord = this.accountRepository.countAllByCustomer(customer);
        return ResponseUtil.list(customers, totalRecord, true, "List all accounts by customer");
    }

    @PostMapping
    ResponseDTO createAccount(@RequestBody Map<String, Object> payload) {
        try {
            BankAccount bankAccount = this.accountService.create(payload);
            return ResponseUtil.object(bankAccount, true, "Account has been created.");
        } catch (Exception ex) {
            LOGGER.error("Error while creating customer's account: " + ex.getMessage());
            ex.printStackTrace();
            return ResponseUtil.object(null, false, ex.getMessage());
        }
    }

}
