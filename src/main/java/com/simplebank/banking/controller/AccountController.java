package com.simplebank.banking.controller;

import com.simplebank.banking.dto.Accountdto;
import com.simplebank.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // add account rest api

    @PostMapping
    public ResponseEntity<Accountdto> addAccount(@RequestBody Accountdto accountdto){
        return new ResponseEntity<>(accountService.createAccount(accountdto) , HttpStatus.CREATED);
    }

    // get Accounnt
    @GetMapping("/{id}")
    public ResponseEntity<Accountdto> getAccountById(@PathVariable Long id){
        Accountdto accountdto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountdto);
    }

    @PutMapping("/{id}/deposit")
    public ResponseEntity<Accountdto> depositAmount(@PathVariable  Long id ,
                                                    @RequestBody Map<String , Double> request){

        Double amount = request.get("amount");
        Accountdto accountdto = accountService.deposit(id , request.get("amount"));
        return ResponseEntity.ok(accountdto);
    }

    // withdraw
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<Accountdto> withdraw (@PathVariable  Long id ,
                                                @RequestBody Map<String , Double> request){
        double amount = request.get("amount");

        Accountdto accountdto = accountService.withdraw(id,amount);

        return ResponseEntity.ok(accountdto);
    }

    // get all
    @GetMapping
    public ResponseEntity<List<Accountdto>> getAllAcounts(){
        List<Accountdto> accounts = accountService.getAllAcounts();
        return ResponseEntity.ok(accounts);
    }

    // delete account
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account Deleted!!!");
    }
}
