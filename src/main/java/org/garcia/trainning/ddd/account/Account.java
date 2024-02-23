package org.garcia.trainning.ddd.account;

import java.util.UUID;

public class Account {

    private AccountID id;
    private Money balance;

    public Account(Money balance) {
        this.id = AccountID.from(UUID.randomUUID().toString());
        this.balance = balance;
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

        apply(new MoneyWithdrawn(amount));
    }

    private void apply(MoneyWithdrawn event) {
        balance = new Money(balance.amount() - event.amount().amount());
    }
}
