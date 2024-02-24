package org.garcia.trainning.ddd.account;

public record Money (double amount) {
    public static Money from(final double amount) {
        return new Money(amount);
    }
}
