package com.example.bank.services;

import com.example.bank.enums.AccountType;
import com.example.bank.models.BankAccount;
import com.example.bank.models.Customer;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Map;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    public BankAccount create(Map<String, Object> payload) {
        BankAccount bankAccount = new BankAccount();
        Customer customer = this.customerRepository.findById(Long.parseLong((String) payload.get("customer"))).orElseThrow();
        bankAccount.setCustomer(customer);
        bankAccount.setAccountType(AccountType.valueOf((String) payload.get("accountType")));
        bankAccount.setAccountCode((String) payload.get("accountCode"));
        bankAccount.setOpeningDate(Date.valueOf((String) payload.get("openingDate")));
        bankAccount.setBalance((Double) payload.get("balance"));
        return this.accountRepository.save(bankAccount);
    }
}
