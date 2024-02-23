package org.garcia.trainning.ddd.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    @Test
    void should_Not_Be_Able_To_Withdraw_When_Insufficient_Balance() {
        // Given
        var account = accountWithTen();

        // When
        var withdrawAction = withdrawHundred(account);

        // Then
        shouldNotBeAllowed(withdrawAction);
    }

    private Account accountWithTen() {
        Account account = new Account(Money.from(10d));
        return account;
    }

    private static void shouldNotBeAllowed(Executable withdrawAction) {
        assertThrows(InsufficientBalanceException.class, withdrawAction);
    }

    private static Executable withdrawHundred(Account account) {
        var withdrawAction = withdrawAction(account, Money.from(200d));
        return withdrawAction;
    }

    private static Executable withdrawAction(Account account, Money amount) {
        Executable withdrawAction = () -> {
            account.withdraw(amount);
        };
        return withdrawAction;
    }
}
