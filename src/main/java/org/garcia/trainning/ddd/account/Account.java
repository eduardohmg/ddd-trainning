package org.garcia.trainning.ddd.account;

import org.garcia.trainning.ddd.account.actions.withdraw.InsufficientBalanceException;
import org.garcia.trainning.ddd.account.actions.withdraw.MoneyWithdrawn;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private AccountID id;
    private Money balance;
    private final List<MoneyWithdrawn> uncommittedChanges;

    public Account(AccountID accountID, Money balance) {
        this.id = accountID;
        this.balance = balance;
        this.uncommittedChanges = new ArrayList<>();
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

    private void apply(MoneyWithdrawn event) {
        this.uncommittedChanges.add(event);
        balance = new Money(balance.amount() - event.amount().amount());
    }

    public List<MoneyWithdrawn> getUncommittedChanges() {
        return this.uncommittedChanges;
    }
}
