package com.example.bank.controllers;

import com.example.bank.dto.DepositDTO;
import com.example.bank.models.AccountTransaction;
import com.example.bank.services.AccountService;
import com.example.bank.utils.ResponseDTO;
import com.example.bank.utils.ResponseUtil;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/api/v1/transactions")
public class AccountTransactionController {

    private final AccountService accountService;

    AccountTransactionController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/deposit")
    ResponseEntity<ResponseDTO> deposit(@RequestBody DepositDTO depositDTO) {
        AccountTransaction transaction = this.accountService.deposit(depositDTO);
        return ResponseEntity.ok(ResponseUtil.object(transaction, true, "Balance has been deposited successfully."));
    }

    @PostMapping("/topup")
    ResponseEntity<ResponseDTO> topUpCard(@RequestBody Map<String, Object> payload) throws JsonMappingException {
        return ResponseEntity.ok(accountService.topUp(payload));
    }

}
