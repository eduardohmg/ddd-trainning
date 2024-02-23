package org.garcia.trainning.ddd.account;

public interface AccountRepository {

    Account loadAccountFrom(AccountID accountID);

    void update(Account account);
}
