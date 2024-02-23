package org.garcia.trainning.ddd.account;

import org.garcia.trainning.ddd.account.actions.withdraw.InsufficientBalanceException;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @Test
    void shouldWithdrawMoneyFromAccount() throws InsufficientBalanceException {
        // Given
        var repository = mock(AccountRepository.class);
        var service = new AccountService(repository);

        var account = mock(Account.class);
        var accountID = mock(AccountID.class);
        var amount = Money.from(50d);

        when(repository.loadAccountFrom(accountID)).thenReturn(account);

        // When
        service.withdraw(accountID, amount);

        // Then
        repositoryWasCalledToFetchTheRightAccountID(accountID, repository);
        withdrawOfTheCorrectAmountWasIssued(account, amount);
        repositoryWasCalledToPersistAccountChanges(account, repository);
    }

    private void repositoryWasCalledToFetchTheRightAccountID(AccountID accountID, AccountRepository repository) {
        verify(repository).loadAccountFrom(accountID);
    }

    private void withdrawOfTheCorrectAmountWasIssued(Account account, Money amount) throws InsufficientBalanceException {
        verify(account).withdraw(amount);
    }

    private void repositoryWasCalledToPersistAccountChanges(Account account, AccountRepository repository) {
        verify(repository).update(account);
    }
}
