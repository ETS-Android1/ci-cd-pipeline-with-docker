package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.dbConfig.EDatabase;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistentAccountDAO implements AccountDAO {

    private EDatabase db;

    public static final String ACCOUNT_TABLE_NAME = "account";
    public static final String ACCOUNT_KEY_ACCOUNT_NO = "id";
    public static final String ACCOUNT_KEY_BANK_NAME = "bankName";
    public static final String ACCOUNT_KEY_ACCOUNT_HOLDER_NAME = "accountHolderName";
    public static final String ACCOUNT_KEY_BALANCE ="balance";

    public PersistentAccountDAO(EDatabase db) {
        this.db = db;
    }

    @Override
    public List<String> getAccountNumbersList() {

        String query = "SELECT "+ACCOUNT_KEY_ACCOUNT_NO+" FROM "+ACCOUNT_TABLE_NAME;


        Cursor cursor = selectData(query,null);
        List<String> accountNumbers = new ArrayList<>();

        if (isNullCursor(cursor))
            return accountNumbers;

        System.out.println("account list ids");
        while(cursor.moveToNext()){
            accountNumbers.add(cursor.getString(cursor.getColumnIndexOrThrow(ACCOUNT_KEY_ACCOUNT_NO)));
        }
        return accountNumbers;
    }

    @Override
    public List<Account> getAccountsList() {
        String query = "SELECT * FROM "+ACCOUNT_TABLE_NAME;

        Cursor cursor = selectData(query,null);
        List<Account> accounts = new ArrayList<>();

        if (isNullCursor(cursor))
            return accounts;


        System.out.println("account list");
        while(cursor.moveToNext()){
            accounts.add(getCursorAccount(cursor));
        }
        return accounts;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        String query = "SELECT * " +
                "FROM "+ACCOUNT_TABLE_NAME +
                " WHERE "+ACCOUNT_KEY_ACCOUNT_NO+" = ?";

        String [] selectionArgs = {accountNo};
        Cursor cursor = selectData(query,selectionArgs);

        if (isNullCursor(cursor))
            return null;


        if(cursor.moveToFirst()){
            return getCursorAccount(cursor);
        }else{
            throw new InvalidAccountException("account number "+ accountNo+ " is not exits");
        }

    }

    private Account getCursorAccount(Cursor cursor){
        return new Account(
                cursor.getString(cursor.getColumnIndexOrThrow(ACCOUNT_KEY_ACCOUNT_NO)),
                cursor.getString(cursor.getColumnIndexOrThrow(ACCOUNT_KEY_BANK_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(ACCOUNT_KEY_ACCOUNT_HOLDER_NAME)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(ACCOUNT_KEY_BALANCE))
        );
    }

    @Override
    public void addAccount(Account account) {

        try {
            getAccount(account.getAccountNo());
            System.out.println("Account "+account.getAccountNo()+" already exists.");
        } catch (InvalidAccountException e) {
            SQLiteDatabase db = this.db.getWritable();

            ContentValues contentValues = new ContentValues();
            contentValues.put(ACCOUNT_KEY_ACCOUNT_NO,account.getAccountNo());
            contentValues.put(ACCOUNT_KEY_BANK_NAME,account.getBankName());
            contentValues.put(ACCOUNT_KEY_ACCOUNT_HOLDER_NAME,account.getAccountHolderName());
            contentValues.put(ACCOUNT_KEY_BALANCE,account.getBalance());

            long result = db.insert(ACCOUNT_TABLE_NAME,null,contentValues);
            if(result>0){
                System.out.println("Successfully add account");
            }else{
                System.out.println("Failed to add account "+ account.getAccountNo()+".");
            }
        }

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        getAccount(accountNo);

        SQLiteDatabase db = this.db.getWritable();

        String [] whereArgs ={accountNo};
        String where =ACCOUNT_KEY_ACCOUNT_NO+"=?";

        if(db.delete(ACCOUNT_TABLE_NAME,where,whereArgs)>0){
            System.out.println("Successfully account "+accountNo+" deleted.");
        }else{
            System.out.println("Failed to delete "+accountNo+" account");
        }
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        Account account = getAccount(accountNo);

        switch (expenseType) {
            case EXPENSE:
                account.setBalance(account.getBalance() - amount);
                break;
            case INCOME:
                account.setBalance(account.getBalance() + amount);
                break;
        }


        SQLiteDatabase db = this.db.getWritable();

        ContentValues updateAccountCV = new ContentValues();
        updateAccountCV.put(ACCOUNT_KEY_BALANCE,account.getBalance());

        String updateAccountWhere = ACCOUNT_KEY_ACCOUNT_NO+"=?";
        String [] updateWhereArgs ={accountNo};

        int i = db.update(ACCOUNT_TABLE_NAME,updateAccountCV,updateAccountWhere,updateWhereArgs);
        System.out.println("update balance :"+i);

        if(i>0){
            System.out.println("Balance updated.");
        }else{
            System.out.println("Failed update balance.");
        }

    }

    private Cursor selectData(String query,String []selectArgs){

        SQLiteDatabase db = this.db.getReadable();

        try{
            Cursor cursor = db.rawQuery(query,selectArgs);
            return cursor;
        }catch(Exception e){
            System.out.println("sql select error: "+e.getMessage());
            return null;
        }

    }

    private boolean isNullCursor(Cursor cursor){
        if (cursor==null) return true;

        return false;
    }
}
