package org.garcia.trainning.ddd.account.actions.withdraw;

import org.garcia.trainning.ddd.account.AccountID;
import org.garcia.trainning.ddd.account.Money;
import org.garcia.trainning.ddd.archetype.DomainEvent;

public record MoneyWithdrawn(AccountID accountID, Money amount) implements DomainEvent {
}
