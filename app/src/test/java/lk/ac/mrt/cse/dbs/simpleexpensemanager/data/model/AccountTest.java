package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class AccountTest {
    private static final String TEST_ACCOUNT_NO = "12345sdf";
    private static final String TEST_BANK_ACCOUNT_NAME = "BOC";
    private static final String TEST_ACCOUNT_HOLDER_NAME = "Ayesh";
    private static final double TEST_ACCOUNT_BALANCE = 1000.0;

    private Account newAccount;

    public AccountTest() {
        newAccount = new Account(TEST_ACCOUNT_NO, TEST_BANK_ACCOUNT_NAME, TEST_ACCOUNT_HOLDER_NAME, TEST_ACCOUNT_BALANCE);
    }

    @Test
    public void testGetAccountNo() {
        assertTrue(newAccount.getAccountNo().equals(TEST_ACCOUNT_NO));
    }

    @Test
    public void testGetBankAccountName() {
        assertTrue(newAccount.getBankName().equals(TEST_BANK_ACCOUNT_NAME));
    }

    @Test
    public void testGetAccountHolderName() {
        assertTrue(newAccount.getAccountHolderName().equals(TEST_ACCOUNT_HOLDER_NAME));
    }

    @Test
    public void testGetAccountBalance() {
        assertTrue(newAccount.getBalance()==TEST_ACCOUNT_BALANCE);
    }

    @Test
    public void testSetAccountNo() {
        newAccount.setAccountNo("98765");
        assertTrue(newAccount.getAccountNo().equals("98765"));
        newAccount.setAccountNo(TEST_ACCOUNT_NO);
    }

    @Test
    public void testSetBankAccountName() {
        newAccount.setBankName("HDFC");
        assertTrue(newAccount.getBankName().equals("HDFC"));
        newAccount.setBankName(TEST_BANK_ACCOUNT_NAME);
    }

    @Test
    public void testSetAccountHolderName() {
        newAccount.setAccountHolderName("Vininda");
        assertTrue(newAccount.getAccountHolderName().equals("Vininda"));
        newAccount.setAccountHolderName(TEST_ACCOUNT_HOLDER_NAME);
    }

    @Test
    public void testSetAccountBalance() {
        newAccount.setBalance(100.0);
        assertTrue(newAccount.getBalance()==100.0);
        newAccount.setBalance(TEST_ACCOUNT_BALANCE);
    }

}