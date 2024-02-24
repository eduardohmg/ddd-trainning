package org.garcia.trainning.ddd.infra;

import org.garcia.trainning.ddd.account.Account;
import org.garcia.trainning.ddd.account.AccountID;
import org.garcia.trainning.ddd.account.AccountRepository;

import java.util.ArrayList;
import java.util.List;

public class AccountInMemoryRepository implements AccountRepository {

    private final List<Account> accounts;

    public AccountInMemoryRepository() {
        this.accounts = new ArrayList<>();
    }

    public Account loadAccountFrom(final AccountID accountID) {
        var savedAccount = accounts.stream()
                .filter(account -> account.getID().equals(accountID))
                .findFirst()
                .orElseThrow();

        return new Account(savedAccount.getID(), savedAccount.getBalance());
    }

    public void update(final Account account) {
        var savedAccount = accounts.stream()
                .filter(acc -> acc.getID().equals(account.getID()))
                .findFirst();

        if (savedAccount.isPresent())
            accounts.remove(savedAccount);

        accounts.add(account);
    }
}
