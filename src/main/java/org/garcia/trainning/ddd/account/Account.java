package org.garcia.trainning.ddd.account;

import org.garcia.trainning.ddd.account.actions.open.AccountOpened;
import org.garcia.trainning.ddd.account.actions.withdraw.InsufficientBalanceException;
import org.garcia.trainning.ddd.account.actions.withdraw.MoneyWithdrawn;
import org.garcia.trainning.ddd.archetype.DomainEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Account {

    private final AccountID id;
    private Money balance;
    private final List<DomainEvent> uncommittedChanges;

    public Account(final AccountID accountID, final Money balance) {
        this.id = accountID;
        this.balance = balance;
        this.uncommittedChanges = new ArrayList<>();
    }

    private Account(final AccountID accountID) {
        this.id = accountID;
        this.balance = Money.from(0);
        this.uncommittedChanges = new ArrayList<>();
    }

    public static Account openWith(final Money balance) {
        var accountID = AccountID.from(UUID.randomUUID().toString());

        var newAccount = new Account(accountID);

        newAccount.emit(new AccountOpened(accountID, balance));

        return newAccount;
    }

    // add test comment 2
    public static Account restoreFromHistory(List<DomainEvent> history) {

        if (history.getFirst() instanceof AccountOpened accountOpened) {
            final var account = new Account(accountOpened.accountID(), accountOpened.balance());

            // apply all event skipping first one
            history.stream().skip(1).forEach(event -> {
                if (event instanceof MoneyWithdrawn moneyWithdrawn) {
                    account.apply(moneyWithdrawn);
                }
            });

            return account;
        }

        return null;
    }

    public AccountID getID() {
        return id;
    }

    public Money getBalance() {
        return balance;
    }

    public void withdraw(final Money amount) throws InsufficientBalanceException {
        if (balance.amount() < amount.amount()) {
            throw new InsufficientBalanceException();
        }

        emit(new MoneyWithdrawn(this.id, amount));
    }

    private void emit(final AccountOpened event) {
        this.uncommittedChanges.add(event);
        this.apply(event);
    }

    private void emit(final MoneyWithdrawn event) {
        this.uncommittedChanges.add(event);
        this.apply(event);
    }

    private void apply(final AccountOpened event) {
        this.balance = event.balance();
    }

    private void apply(final MoneyWithdrawn event) {
        balance = new Money(balance.amount() - event.amount().amount());
    }

    public List<DomainEvent> getUncommittedChanges() {
        return this.uncommittedChanges;
    }
}
