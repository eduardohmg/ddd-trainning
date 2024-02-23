package org.garcia.trainning.ddd.account;

public record MoneyWithdrawn(AccountID accountID, Money amount) {
}
