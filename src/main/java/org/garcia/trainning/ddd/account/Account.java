package org.garcia.trainning.ddd.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Account {

    private AccountID id;
    private Money balance;
    private final List<MoneyWithdrawn> uncommitedChanges;

    public Account(Money balance) {
        this.id = AccountID.from(UUID.randomUUID().toString());
        this.balance = balance;
        this.uncommitedChanges = new ArrayList<>();
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
        this.uncommitedChanges.add(event);
        balance = new Money(balance.amount() - event.amount().amount());
    }

    public List<MoneyWithdrawn> getUncommittedChanges() {
        return this.uncommitedChanges;
    }
}
