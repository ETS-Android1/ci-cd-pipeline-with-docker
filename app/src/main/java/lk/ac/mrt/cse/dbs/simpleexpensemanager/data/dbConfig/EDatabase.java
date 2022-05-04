package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.dbConfig;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;


public class EDatabase extends SQLiteOpenHelper {
    private Context context;

    private static SQLiteDatabase getReadable = null;
    private static SQLiteDatabase getWritable = null;

    private static final String DATABASE_NAME = "190649F";

    public EDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;


        System.out.println();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String accountTableSQL = "CREATE TABLE "+ PersistentAccountDAO.ACCOUNT_TABLE_NAME +"("+
                PersistentAccountDAO.ACCOUNT_KEY_ACCOUNT_NO +" VARCHAR(255) PRIMARY KEY,"+
                PersistentAccountDAO.ACCOUNT_KEY_BANK_NAME +" VARCHAR(255) NOT NULL ,"+
                PersistentAccountDAO.ACCOUNT_KEY_ACCOUNT_HOLDER_NAME + " VARCHAR(255) NOT NULL,"+
                PersistentAccountDAO.ACCOUNT_KEY_BALANCE + " DOUBLE NOT NULL );";

        String transactionTableSQL = "CREATE TABLE "+ PersistentTransactionDAO.TRANSACTION_TABLE_NAME+"("+
                PersistentTransactionDAO.TRANSACTION_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                PersistentTransactionDAO.TRANSACTION_KEY_DATE+" DATE NOT NULL ,"+
                PersistentTransactionDAO.TRANSACTION_KEY_ACCOUNT_NO+" VARCHAR(25) NOT NULL,"+
                PersistentTransactionDAO.TRANSACTION_EXPENSE_TYPE+" VARCHAR(10),"+
                PersistentTransactionDAO.TRANSACTION_AMOUNT+" DOUBLE NOT NULL,"+
                "FOREIGN KEY ("+PersistentTransactionDAO.TRANSACTION_KEY_ACCOUNT_NO+") REFERENCES "+PersistentTransactionDAO.TRANSACTION_ID+" ("+PersistentAccountDAO.ACCOUNT_TABLE_NAME+") ON DELETE CASCADE);";

        db.execSQL(accountTableSQL);
        db.execSQL(transactionTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sqlAccount = "DROP TABLE IF EXISTS "+ PersistentAccountDAO.ACCOUNT_TABLE_NAME;
        String sqlTransaction = "DROP TABLE IF EXISTS "+ PersistentTransactionDAO.TRANSACTION_TABLE_NAME;

        db.execSQL(sqlTransaction);
        db.execSQL(sqlAccount);
    }

    public SQLiteDatabase getReadable(){
//        if(getReadable==null){
//            getReadable = this.getReadableDatabase();
//        }

        return this.getReadableDatabase();
    }

    public SQLiteDatabase getWritable(){
//        if(getWritable == null){
//            getWritable = this.getWritableDatabase();
//        }

        return this.getWritableDatabase();
    }

}
