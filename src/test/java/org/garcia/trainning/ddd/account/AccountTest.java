package org.garcia.trainning.ddd.account;

import org.garcia.trainning.ddd.account.actions.withdraw.InsufficientBalanceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void accounts_Should_Have_A_Unique_ID() {
        // Given
        var firstAccount = accountWithTen();
        var secondAccount = accountWithTen();

        // Then
        accountHaveDifferentIDs(firstAccount, secondAccount);
    }

    @Test
    void should_Not_Be_Able_To_Withdraw_When_Insufficient_Balance() {
        // Given
        var account = accountWithTen();

        // When
        var withdrawAction = withdrawHundredFrom(account);

        // Then
        shouldNotBeAllowedWhenPerforming(withdrawAction);
    }

    @Test
    void should_Decrease_Balance_When_Withdraw() throws InsufficientBalanceException {
        // Given
        var account = accountWithTen();

        // When
        withdrawThreeFrom(account);

        // Then
        balanceShouldBeSeven(account);
    }

    @Test
    void should_Return_All_Uncommitted_Changes() throws InsufficientBalanceException {
        // Given
        var account = accountWithTen();

        // When
        withdrawThreeFrom(account);
        withdrawTwoFrom(account);

        // Then
        thereAreTwoUncommitedChangesInThe(account);
        firstChangeIsTheWithdrawThreeFrom(account);
        secondChangeIsTheWithdrawTwoFrom(account);
    }

    private static void accountHaveDifferentIDs(Account firstAccount, Account secondAccount) {
        assertNotEquals(firstAccount.getID(), secondAccount.getID());
    }

    private static void secondChangeIsTheWithdrawTwoFrom(Account account) {
        assertEquals(account.getID(), account.getUncommittedChanges().get(1).accountID());
        assertEquals(Money.from(2d), account.getUncommittedChanges().get(1).amount());
    }

    private static void firstChangeIsTheWithdrawThreeFrom(Account account) {
        assertEquals(account.getID(), account.getUncommittedChanges().get(0).accountID());
        assertEquals(Money.from(3d), account.getUncommittedChanges().get(0).amount());
    }

    private static void thereAreTwoUncommitedChangesInThe(Account account) {
        assertEquals(2, account.getUncommittedChanges().size());
    }

    private void balanceShouldBeSeven(Account account) {
        assertEquals(Money.from(7d), account.getBalance());
    }

    private Account accountWithTen() {
        return new Account(AccountID.from(UUID.randomUUID().toString()), Money.from(10d));
    }

    private static void shouldNotBeAllowedWhenPerforming(Executable withdrawAction) {
        assertThrows(InsufficientBalanceException.class, withdrawAction);
    }

    private static void withdrawThreeFrom(Account account) throws InsufficientBalanceException {
        var three = Money.from(3d);
        account.withdraw(three);
    }
    private static void withdrawTwoFrom(Account account) throws InsufficientBalanceException {
        var three = Money.from(2d);
        account.withdraw(three);
    }

    private static Executable withdrawHundredFrom(Account account) {
        return withdrawAction(account, Money.from(200d));
    }

    private static Executable withdrawAction(Account account, Money amount) {
        return () -> {
            account.withdraw(amount);
        };
    }
}
