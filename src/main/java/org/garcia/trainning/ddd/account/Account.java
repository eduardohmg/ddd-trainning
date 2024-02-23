package org.garcia.trainning.ddd.account;

import org.garcia.trainning.ddd.account.actions.open.AccountOpened;
import org.garcia.trainning.ddd.account.actions.withdraw.InsufficientBalanceException;
import org.garcia.trainning.ddd.account.actions.withdraw.MoneyWithdrawn;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Account {

    private AccountID id;
    private Money balance;
    private final List<MoneyWithdrawn> uncommittedChanges;

    public Account(AccountID accountID, Money balance) {
        this.id = accountID;
        this.balance = balance;
        this.uncommittedChanges = new ArrayList<>();
    }

    private Account(AccountID accountID) {
        this.id = accountID;
        this.uncommittedChanges = new ArrayList<>();
    }

    public static Account openWith(Money balance) {
        var accountID = AccountID.from(UUID.randomUUID().toString());

        var newAccount = new Account(accountID);

        newAccount.apply(new AccountOpened(accountID, balance));

        return newAccount;
    }

    public AccountID getID() {
        return id;
    }

    public Money getBalance() {
        return balance;
    }

    public void withdraw(Money amount) throws InsufficientBalanceException {
        if (balance.amount() < amount.amount()) {
            throw new InsufficientBalanceException();
        }

        apply(new MoneyWithdrawn(this.id, amount));
    }

    private void apply(AccountOpened event) {
        this.balance = event.balance();
    }

    private void apply(MoneyWithdrawn event) {
        this.uncommittedChanges.add(event);
        balance = new Money(balance.amount() - event.amount().amount());
    }

    public List<MoneyWithdrawn> getUncommittedChanges() {
        return this.uncommittedChanges;
    }
}
