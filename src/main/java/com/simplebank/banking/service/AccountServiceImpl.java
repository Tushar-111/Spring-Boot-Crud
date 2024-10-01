package com.simplebank.banking.service;

import com.simplebank.banking.dto.Accountdto;
import com.simplebank.banking.entity.Account;
import com.simplebank.banking.mapper.AccountMapper;
import com.simplebank.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{

    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Accountdto createAccount(Accountdto accountdto) {
        Account account = AccountMapper.mapToAccount(accountdto);
        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountdto(savedAccount);
    }

    @Override
    public Accountdto getAccountById(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exists"));
        return AccountMapper.mapToAccountdto(account);
    }

    @Override
    public Accountdto deposit(Long id, double amount) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exists"));

        double total = account.getBalance() + amount;
        account.setBalance(total);

        Account saved = accountRepository.save(account);
        return AccountMapper.mapToAccountdto(saved);
    }

    @Override
    public Accountdto withdraw(Long id, double amount) {

        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exists"));

        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficent amount");
        }

        double total = account.getBalance() - amount ;
        account.setBalance(total);

        Account saved = accountRepository.save(account);

        return AccountMapper.mapToAccountdto(saved);
    }

    @Override
    public List<Accountdto> getAllAcounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map((account) -> AccountMapper.mapToAccountdto(account))
                .collect(Collectors.toList());

    }

    @Override
    public void deleteAccount(Long id) {

        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exists"));

        accountRepository.deleteById(id);
    }

}
