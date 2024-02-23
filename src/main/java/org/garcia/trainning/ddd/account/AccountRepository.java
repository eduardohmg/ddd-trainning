package org.garcia.trainning.ddd.account;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository {

    private final List<Account> accounts;

    public AccountRepository() {
        this.accounts = new ArrayList<>();
    }

    public Account loadAccountFrom(AccountID accountID) {
        var savedAccount = accounts.stream()
                .filter(account -> account.getID().equals(accountID))
                .findFirst()
                .orElseThrow();

        return new Account(savedAccount.getID(), savedAccount.getBalance());
    }

    public void update(Account account) {
        var savedAccount = accounts.stream()
                .filter(acc -> acc.getID().equals(account.getID()))
                .findFirst();

        if (savedAccount.isPresent())
            accounts.remove(savedAccount);

        accounts.add(account);
    }
}
