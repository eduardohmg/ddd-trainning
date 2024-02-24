package org.garcia.trainning.ddd.account;

import org.garcia.trainning.ddd.account.actions.open.AccountOpened;
import org.garcia.trainning.ddd.account.actions.withdraw.InsufficientBalanceException;
import org.garcia.trainning.ddd.account.actions.withdraw.MoneyWithdrawn;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

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
        thereAreThreeUncommitedChangesInThe(account);
        firstChangeIsTheAccountOpenedWithTen(account);
        secondChangeIsTheWithdrawThreeFrom(account);
        thirdChangeIsTheWithdrawTwoFrom(account);
    }

    private void firstChangeIsTheAccountOpenedWithTen(Account account) {
        assertInstanceOf(AccountOpened.class, account.getUncommittedChanges().getFirst());

        var event = (AccountOpened) account.getUncommittedChanges().getFirst();
        assertEquals(account.getID(), event.accountID());
    }

    private static void accountHaveDifferentIDs(Account firstAccount, Account secondAccount) {
        assertNotEquals(firstAccount.getID(), secondAccount.getID());
    }

    private static void secondChangeIsTheWithdrawThreeFrom(Account account) {
        var event = account.getUncommittedChanges().get(1);
        assertInstanceOf(MoneyWithdrawn.class, event);

        var withdraw = (MoneyWithdrawn) event;
        assertEquals(account.getID(), withdraw.accountID());
        assertEquals(Money.from(3d), withdraw.amount());
    }

    private static void thirdChangeIsTheWithdrawTwoFrom(Account account) {
        var event = account.getUncommittedChanges().get(2);
        assertInstanceOf(MoneyWithdrawn.class, event);

        var withdraw = (MoneyWithdrawn) event;
        assertEquals(account.getID(), withdraw.accountID());
        assertEquals(Money.from(2d), withdraw.amount());
    }

    private static void thereAreThreeUncommitedChangesInThe(Account account) {
        assertEquals(3, account.getUncommittedChanges().size());
    }

    private void balanceShouldBeSeven(Account account) {
        assertEquals(Money.from(7d), account.getBalance());
    }

    private Account accountWithTen() {
        return Account.openWith(Money.from(10d));
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
