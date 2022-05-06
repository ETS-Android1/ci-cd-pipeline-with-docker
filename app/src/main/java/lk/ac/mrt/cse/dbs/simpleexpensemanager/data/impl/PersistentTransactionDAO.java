package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.dbConfig.EDatabase;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO implements TransactionDAO {

    private EDatabase db;

    public static final String TRANSACTION_TABLE_NAME="bankTransaction";
    public static final String TRANSACTION_ID ="id";
    public static final String TRANSACTION_KEY_DATE="date";
    public static final String TRANSACTION_KEY_ACCOUNT_NO="accountNo";
    public static final String TRANSACTION_EXPENSE_TYPE ="expenseType";
    public static final String TRANSACTION_AMOUNT = "amount";

    public PersistentTransactionDAO(EDatabase db) {
        this.db =db;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String f_date = sdf.format(date);

        SQLiteDatabase db = this.db.getWritable();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TRANSACTION_KEY_DATE,f_date);
        contentValues.put(TRANSACTION_KEY_ACCOUNT_NO,accountNo);
        contentValues.put(TRANSACTION_EXPENSE_TYPE,String.valueOf(expenseType));
        contentValues.put(TRANSACTION_AMOUNT,amount);

        long result = db.insert(TRANSACTION_TABLE_NAME,null,contentValues);

        if(result>0){
            System.out.println("Transaction successful.");
        }else{
            System.out.println( "Failed to transaction.");

        }
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        String query = "SELECT * FROM "+TRANSACTION_TABLE_NAME;

        Cursor cursor = selectData(query,null);

        if(isNullCursor(cursor)){
            return new ArrayList<Transaction>();
        }

        return getCursorTransaction(cursor);
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        String query = "SELECT * " +
                "FROM "+TRANSACTION_TABLE_NAME +
                " LIMIT "+limit;
        Cursor cursor = selectData(query,null);


        if(isNullCursor(cursor)){
            System.out.println("error loading transactions ");
            return new ArrayList<Transaction>();
        }

        return getCursorTransaction(cursor);

    }

    public List<Transaction> getCursorTransaction(Cursor cursor){
        List<Transaction> transactions = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try{
            while(cursor.moveToNext()){
                transactions.add(new Transaction(
                        sdf.parse(cursor.getString(cursor.getColumnIndexOrThrow(TRANSACTION_KEY_DATE))),
                        cursor.getString(cursor.getColumnIndexOrThrow(TRANSACTION_KEY_ACCOUNT_NO)),
                        ExpenseType.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(TRANSACTION_EXPENSE_TYPE))),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(TRANSACTION_AMOUNT))
                ));

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return transactions;
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
