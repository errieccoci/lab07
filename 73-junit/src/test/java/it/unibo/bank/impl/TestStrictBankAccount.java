package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    private final double MANAGEMENT_FEE=5;
    private final int ACCEPTABLE_MESSAGE_LENGTH=3;
    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;
    private final double TRANSACTION_FEE=0.1;
    private final double AMOUNT=100;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi=new AccountHolder("mario", "rossi", 1);
        this.bankAccount= new StrictBankAccount(mRossi, 0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        //test se accountbalance==0
        assertNotNull(this.bankAccount);
        assertEquals(this.bankAccount.getBalance(), 0, "Initial state not zero");
        assertEquals(bankAccount.getTransactionsCount(), 0);
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        this.bankAccount.deposit( this.mRossi.getUserID(), AMOUNT);
        final double tot=AMOUNT-(MANAGEMENT_FEE+bankAccount.getTransactionsCount()*TRANSACTION_FEE);
        this.bankAccount.chargeManagementFees(this.mRossi.getUserID());
        assertEquals(tot, this.bankAccount.getBalance());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
       // assertThrows(IllegalArgumentException.class, ()->{this.bankAccount.withdraw(this.mRossi.getUserID(), -1000);});

        try{
            bankAccount.withdraw(mRossi.getUserID(), -1000);
            fail("There shoul be an error");
        }catch(final IllegalArgumentException e){
            assertEquals(0, bankAccount.getBalance());
            assertNotNull(e.getMessage());
            assertTrue(ACCEPTABLE_MESSAGE_LENGTH<=e.getMessage().length());
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        try{
            bankAccount.withdraw(mRossi.getUserID(), AMOUNT);
            fail("there should be an error");
        }catch(IllegalArgumentException e){
            assertEquals(0, bankAccount.getBalance());
            assertNotNull(e.getMessage());
            assertTrue(ACCEPTABLE_MESSAGE_LENGTH<=e.getMessage().length());

        }
    }
}
