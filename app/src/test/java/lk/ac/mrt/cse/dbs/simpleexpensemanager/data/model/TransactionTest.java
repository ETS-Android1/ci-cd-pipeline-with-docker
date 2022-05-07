package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model;

import static org.junit.Assert.*;

import org.junit.Test;

import java.text.SimpleDateFormat;

public class TransactionTest {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final String TEST_DATE = "2022-01-01";
    private static final String TEST_ACCOUNT_NO = "12345sdf";
    private static final ExpenseType TEST_EXPENSE_TYPE = ExpenseType.EXPENSE;
    private static final double TEST_AMOUNT= 100.0;

    private Transaction transaction;
    public TransactionTest() {
        try{
            transaction = new Transaction(sdf.parse(TEST_DATE), TEST_ACCOUNT_NO, TEST_EXPENSE_TYPE, TEST_AMOUNT);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testGetDate() {
        assertTrue(sdf.format(transaction.getDate()).equals(TEST_DATE));
    }

    @Test
    public void testGetAccountNo() {
        assertTrue(transaction.getAccountNo().equals(TEST_ACCOUNT_NO));
    }

    @Test
    public void testGetExpenseType() {
        assertTrue(transaction.getExpenseType().equals(TEST_EXPENSE_TYPE));
    }

    @Test
    public void testGetAmount() {
        assertTrue(transaction.getAmount()==TEST_AMOUNT);
    }

    @Test
    public void testSetDate() {
        String newDate = "2023-01-01";
        try{
            transaction.setDate(sdf.parse(newDate));
            assertTrue(sdf.format(transaction.getDate()).equals(newDate));
            transaction.setDate(sdf.parse(TEST_DATE));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testSetAccountNo() {
        String newAccountNo = "12345sdf";
        transaction.setAccountNo(newAccountNo);
        assertTrue(transaction.getAccountNo().equals(newAccountNo));
        transaction.setAccountNo(TEST_ACCOUNT_NO);
    }

    @Test
    public void testSetExpenseType() {
        ExpenseType newExpenseType = ExpenseType.INCOME;
        transaction.setExpenseType(newExpenseType);
        assertTrue(transaction.getExpenseType().equals(newExpenseType));
        transaction.setExpenseType(TEST_EXPENSE_TYPE);
    }

    @Test
    public void testSetAmount() {
        double newAmount = 150.0;
        transaction.setAmount(newAmount);
        assertTrue(transaction.getAmount()==newAmount);
        transaction.setAmount(TEST_AMOUNT);
    }

}