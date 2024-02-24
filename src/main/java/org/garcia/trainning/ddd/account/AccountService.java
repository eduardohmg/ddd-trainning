package org.garcia.trainning.ddd.account;

import org.garcia.trainning.ddd.account.actions.withdraw.InsufficientBalanceException;
import org.garcia.trainning.ddd.infra.AccountInMemoryRepository;

public class AccountService {

    private final AccountInMemoryRepository repository;

    public AccountService(final AccountInMemoryRepository repository) {
        this.repository = repository;
    }

    public void withdraw(final AccountID accountID, final Money amount) throws InsufficientBalanceException {
        var account = repository.loadAccountFrom(accountID);
        account.withdraw(amount);
        repository.update(account);
    }
}
