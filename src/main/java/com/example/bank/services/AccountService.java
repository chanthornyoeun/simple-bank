package com.example.bank.services;

import com.example.bank.dto.DepositDTO;
import com.example.bank.enums.AccountType;
import com.example.bank.enums.TransactionType;
import com.example.bank.models.AccountTransaction;
import com.example.bank.models.BankAccount;
import com.example.bank.models.Customer;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.CustomerRepository;
import com.example.bank.utils.ResponseDTO;
import com.example.bank.utils.ResponseUtil;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Service
@Transactional(rollbackFor = Exception.class)
@AllArgsConstructor
public class AccountService {

    private static String API_URL = "http://localhost:3000";
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

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

    public AccountTransaction deposit(DepositDTO depositDTO) {
        BankAccount account = accountRepository.findById(depositDTO.getAccountId()).orElseThrow();
        AccountTransaction transaction = new AccountTransaction();
        transaction.setBalance(depositDTO.getBalance());
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setBankAccount(account);
        double totalBalance = account.getBalance() + depositDTO.getBalance();
        account.setBalance(totalBalance);
        Set<AccountTransaction> accountTransactions = new HashSet<>();
        accountTransactions.add(transaction);
        account.setAccountTransactions(accountTransactions);
        accountRepository.save(account);
        return transaction;
    }

    public ResponseDTO topUp(Map<String, Object> payload) throws JsonMappingException {
        BankAccount account = accountRepository.findById(Long.parseLong(payload.get("accountId").toString())).orElseThrow();
        double topUpAmount = (double) (payload.get("amount"));
        String url = API_URL + "/topup";
        Map<String, Object> response = restTemplate.postForObject(url, payload, Map.class);

        boolean isSucceed = (Boolean) response.get("success");
        if (isSucceed) {
            Map<String, Object> res = new LinkedHashMap<>();
            res.put("refNo", response.get("refNo"));
            updateBalance(account, topUpAmount);
            return ResponseUtil.object(res, true, (String) response.get("message"));
        }

        return ResponseUtil.object(null, false, "Top Up failed, Plz try again later");
    }

    public AccountTransaction createTopUpTransaction(double topUpAmount) {
        AccountTransaction transaction = new AccountTransaction();
        transaction.setBalance(topUpAmount);
        transaction.setTransactionType(TransactionType.TOP_UP);
        return transaction;
    }

    public BankAccount updateBalance(BankAccount bankAccount, double topUpAmount) throws JsonMappingException {
        ObjectMapper objectMapper = new ObjectMapper();
        bankAccount = objectMapper.updateValue(bankAccount, new LinkedHashMap<>());
        bankAccount.setBalance(bankAccount.getBalance() - topUpAmount);
        Set<AccountTransaction> transactions = bankAccount.getAccountTransactions();
        AccountTransaction transaction = createTopUpTransaction(topUpAmount);
        transaction.setBankAccount(bankAccount);
        transactions.add(transaction);
        bankAccount.setAccountTransactions(transactions);
        return accountRepository.save(bankAccount);
    }

}
