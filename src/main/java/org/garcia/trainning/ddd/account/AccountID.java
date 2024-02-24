package org.garcia.trainning.ddd.account;

public record AccountID (String value) {

    public static AccountID from(final String value) {
        return new AccountID(value);
    }
}
