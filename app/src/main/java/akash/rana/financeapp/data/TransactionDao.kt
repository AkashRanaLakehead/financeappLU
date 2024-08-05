package akash.rana.financeapp.data


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import akash.rana.financeapp.models.Expense
import akash.rana.financeapp.models.Income
import akash.rana.financeapp.models.Transaction

@Dao
interface TransactionDao {
    @Insert
    suspend fun insertTransaction(transaction: Transaction): Long

    @Update
    suspend fun updateTransaction(transaction: Transaction): Int

    @Delete
    suspend fun deleteTransaction(transaction: Transaction): Int

    @Query("SELECT * FROM transactions")
    suspend fun getAllTransactions(): List<Transaction>

    @Query("SELECT * FROM transactions WHERE transaction_type = :transactionType")
    suspend fun getTransactionsByType(transactionType: String): List<Transaction>

    @Insert
    suspend fun insertIncome(income: Income): Long

    @Update
    suspend fun updateIncome(income: Income): Int

    @Delete
    suspend fun deleteIncome(income: Income): Int

    @Query("SELECT * FROM incomes")
    suspend fun getAllIncomes(): List<Income>

    @Insert
    suspend fun insertExpense(expense: Expense): Long

    @Update
    suspend fun updateExpense(expense: Expense): Int

    @Delete
    suspend fun deleteExpense(expense: Expense): Int

    @Query("SELECT * FROM expenses")
    suspend fun getAllExpenses(): List<Expense>

    //Aktuální bilance
    @Query("SELECT SUM(amount) FROM transactions WHERE transaction_type = 'INCOME'")
    suspend fun getTotalIncome(): Double?

    @Query("SELECT SUM(amount) FROM transactions WHERE transaction_type = 'EXPENSE'")
    suspend fun getTotalExpense(): Double?

    @Query("SELECT COUNT(*) FROM transactions")
    suspend fun countTransactions(): Int

    @Query("SELECT COUNT(*) FROM transactions WHERE transaction_type = 'INCOME'")
    suspend fun getIncomeCount(): Int

    @Query("SELECT COUNT(*) FROM transactions WHERE transaction_type = 'EXPENSE'")
    suspend fun getExpenseCount(): Int

    @Query("SELECT SUM(amount) FROM transactions WHERE transaction_type = 'INCOME'")
    fun getTotalIncomeLive(): LiveData<Double>

    @Query("SELECT SUM(amount) FROM transactions WHERE transaction_type = 'EXPENSE'")
    fun getTotalExpenseLive(): LiveData<Double>

    @Query("DELETE FROM transactions")
    suspend fun deleteAllTransactions()
}