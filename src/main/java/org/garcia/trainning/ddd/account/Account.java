package org.garcia.trainning.ddd.account;

public class Account {

    private Money balance;

    public Account(Money balance) {
        this.balance = balance;
    }

    public void withdraw(Money amount) throws InsufficientBalanceException {
        if (balance.amount() < amount.amount()) {
            throw new InsufficientBalanceException();
        }
        balance = new Money(balance.amount() - amount.amount());
    }

    public Money getBalance() {
        return balance;
    }
}
