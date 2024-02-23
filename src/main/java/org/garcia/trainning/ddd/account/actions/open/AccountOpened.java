package org.garcia.trainning.ddd.account.actions.open;

import org.garcia.trainning.ddd.account.AccountID;
import org.garcia.trainning.ddd.account.Money;
import org.garcia.trainning.ddd.archetype.DomainEvent;

public record AccountOpened(AccountID accountID, Money balance) implements DomainEvent {
}
