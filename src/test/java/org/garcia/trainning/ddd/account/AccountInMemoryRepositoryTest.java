package org.garcia.trainning.ddd.account;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountInMemoryRepositoryTest {

    @Test
    void should_Save_And_Load_Account() {
        // Given
        var repository = new AccountRepository();
        var accountID = AccountID.from(UUID.randomUUID().toString());
        var savedAccount = new Account(accountID, Money.from(100d));

        // When
        repository.update(savedAccount);
        var loadedAccount = repository.loadAccountFrom(savedAccount.getID());

        // Then
        savedAndLoadedAccountShouldBeTheSame(savedAccount, loadedAccount);
        loadedAccountShouldHaveNoUncommittedChanges(loadedAccount);
    }

    private void savedAndLoadedAccountShouldBeTheSame(Account savedAccount, Account loadedAccount) {
        assertEquals(savedAccount.getID(), loadedAccount.getID());
        assertEquals(savedAccount.getBalance(), loadedAccount.getBalance());
    }

    private void loadedAccountShouldHaveNoUncommittedChanges(Account loadedAccount) {
        assert loadedAccount.getUncommittedChanges().isEmpty();
    }
}
