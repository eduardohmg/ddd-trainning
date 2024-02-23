package org.garcia.trainning.ddd.account;

import org.garcia.trainning.ddd.archetype.DomainEvent;

public record MoneyWithdrawn(AccountID accountID, Money amount) implements DomainEvent {
}
