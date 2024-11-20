package com.banking.controller;

import com.banking.dto.AccountDto;
import com.banking.entity.Account;
import com.banking.service.AccountService;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/addAccount")
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {

        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping("/getAccount/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable("id") Long id) {

      AccountDto accountDto =  accountService.getAccountById(id);
      return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/deposit/{id}")
    public ResponseEntity<AccountDto> deposit(@PathVariable("id") Long id , @RequestBody Map<String,Double> request) {

        // Extract the amount from the request
        Double amount = request.get("amount");

        // Check if the amount is valid
        if (amount == null || amount <= 0) {
            return ResponseEntity.badRequest().body(null); // Bad request if the amount is invalid
        }

        // Call the service method to perform the deposit operation
        AccountDto updatedAccount = accountService.deposit(id, amount);

        // Return the updated account info as a response
        return ResponseEntity.ok(updatedAccount);
    }

    @PutMapping("/withdraw/{id}")
    public ResponseEntity<AccountDto> withdraw(@PathVariable("id") Long id , @RequestBody Map<String,Double> request) {

        // Extract the amount from the request
        Double amount = request.get("amount");

        // Check if the amount is valid
        if (amount == null || amount <= 0) {
            return ResponseEntity.badRequest().body(null); // Bad request if the amount is invalid
        }

        // Call the service method to perform the withdraw operation
        AccountDto updatedAccount = accountService.withdraw(id, amount);

        // Return the updated account info as a response
        return ResponseEntity.ok(updatedAccount);
    }

    @GetMapping("/allAccount")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {

        List<AccountDto> accounts = accountService.getAllAccount();
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAccountById(@PathVariable("id") Long id) {

        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account is deleted successfully.");
    }

}
