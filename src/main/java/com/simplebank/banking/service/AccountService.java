package com.simplebank.banking.service;

import com.simplebank.banking.dto.Accountdto;

import java.util.List;

public interface AccountService {

    Accountdto createAccount(Accountdto account);

    Accountdto getAccountById(Long id);

    Accountdto deposit(Long id, double amount);

    Accountdto withdraw(Long id , double amount);

    List<Accountdto> getAllAcounts();

    void deleteAccount(Long id);
}
