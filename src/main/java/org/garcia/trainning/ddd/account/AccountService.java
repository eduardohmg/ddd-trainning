package org.garcia.trainning.ddd.account;

import org.garcia.trainning.ddd.account.actions.withdraw.InsufficientBalanceException;

public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public void withdraw(AccountID accountID, Money amount) throws InsufficientBalanceException {
        var account = repository.loadAccountFrom(accountID);
        account.withdraw(amount);
        repository.update(account);
    }
}