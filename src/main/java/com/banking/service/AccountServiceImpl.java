package com.banking.service;

import com.banking.dto.AccountDto;
import com.banking.entity.Account;
import com.banking.mapper.AccountMapper;
import com.banking.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccount(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                                           .orElseThrow(() -> new RuntimeException("account does not exist."));
        return AccountMapper.mapToAccount(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {

        //check account existence
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("account does not exist."));

        //get account balance + deposit amount
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccount(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {

        //check account existence
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("account does not exist."));

        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient amount ");
        }

        //get account balance - withdraw amount
        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccount(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccount() {
        List<Account> accounts = accountRepository.findAll();

        return accounts.stream().map(AccountMapper::mapToAccount).toList();
    }

    @Override
    public void deleteAccount(Long id) {
        // get the account by id
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("account does not exist."));

        //delete the account
        accountRepository.deleteById(id);
    }
}
