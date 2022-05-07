/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;

import org.junit.BeforeClass;
import org.junit.Test;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.PersistentExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest {
    private static final String TEST_ACCOUNT_NO = "12345sdf";
    private static final String TEST_BANK_ACCOUNT_NAME = "BOC";
    private static final String TEST_ACCOUNT_HOLDER_NAME = "Ayesh";
    private static final double TEST_ACCOUNT_BALANCE = 10000000.0;

    private static int TEST_DAY = 1;
    private static int TEST_MONTH = 1;
    private static int TEST_YEAR = 2022;

    private static final ExpenseType TEST_EXPENSE_TYPE = ExpenseType.EXPENSE;
    private static final String TEST_AMOUNT = "100.0";

    private static Context context;
    private static ExpenseManager expenseManager;

    @BeforeClass
    public static void setExpenseManager() {
        context = ApplicationProvider.getApplicationContext();
        expenseManager = new PersistentExpenseManager(context);
    }

    @Test
    public void checkExpenseManagerPackage() {
        assertTrue(context.getPackageName().equals("lk.ac.mrt.cse.dbs.simpleexpensemanager"));
    }

    @Test
    public void checkDatabaseExits() {
        assertTrue(context.getDatabasePath("190649F").exists());
    }

    @Test
    public void addAccount() {
        expenseManager.addAccount(TEST_ACCOUNT_NO, TEST_BANK_ACCOUNT_NAME, TEST_ACCOUNT_HOLDER_NAME, TEST_ACCOUNT_BALANCE);
        assertTrue(expenseManager.getAccountNumbersList().contains(TEST_ACCOUNT_NO));

    }

    @Test
    public void addTransaction() {
        int initialLogsCount = expenseManager.getTransactionLogs().size();
//        System.out.println(initialLogsCount);
        Log.e("initial logs count :", initialLogsCount + "");
        try {
            expenseManager.updateAccountBalance(TEST_ACCOUNT_NO, TEST_DAY, TEST_MONTH, TEST_YEAR, TEST_EXPENSE_TYPE, TEST_AMOUNT);

        } catch (InvalidAccountException e) {
            fail();
        }
        int endLogsCount = expenseManager.getTransactionLogs().size();
//        Log.d("end logs count :",endLogsCount+"");
        assertTrue(endLogsCount == initialLogsCount + 1 || initialLogsCount == 10);
    }
}