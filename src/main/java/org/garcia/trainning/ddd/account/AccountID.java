package org.garcia.trainning.ddd.account;

public record AccountID (String value) {

    public static AccountID from(String value) {
        return new AccountID(value);
    }
}
