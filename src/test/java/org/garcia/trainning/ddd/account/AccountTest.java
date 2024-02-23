package org.garcia.trainning.ddd.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void accounts_Should_Have_A_Unique_ID() {
        // Given
        var account1 = new Account(Money.from(10d));
        var account2 = new Account(Money.from(10d));

        // Then
        assertNotEquals(account1.getID(), account2.getID());
    }

    @Test
    void should_Not_Be_Able_To_Withdraw_When_Insufficient_Balance() {
        // Given
        var account = accountWithTen();

        // When
        var withdrawAction = withdrawHundred(account);

        // Then
        shouldNotBeAllowed(withdrawAction);
    }

    @Test
    void should_Decrease_Balance_When_Withdraw() throws InsufficientBalanceException {
        // Given
        var account = accountWithTen();

        // When
        withdrawThree(account);

        // Then
        balanceShouldBeSeven(account);
    }

    private void balanceShouldBeSeven(Account account) {
        assertEquals(Money.from(7d), account.getBalance());
    }

    private Account accountWithTen() {
        return new Account(Money.from(10d));
    }

    private static void shouldNotBeAllowed(Executable withdrawAction) {
        assertThrows(InsufficientBalanceException.class, withdrawAction);
    }

    private static void withdrawThree(Account account) throws InsufficientBalanceException {
        var three = Money.from(3d);
        account.withdraw(three);
    }

    private static Executable withdrawHundred(Account account) {
        return withdrawAction(account, Money.from(200d));
    }

    private static Executable withdrawAction(Account account, Money amount) {
        return () -> {
            account.withdraw(amount);
        };
    }
}
