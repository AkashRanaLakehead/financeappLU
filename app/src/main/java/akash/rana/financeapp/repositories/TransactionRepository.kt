package akash.rana.financeapp.repositories

import androidx.lifecycle.LiveData
import akash.rana.financeapp.models.Transaction

interface TransactionRepository {
    suspend fun insertTransaction(transaction: Transaction): Long
    suspend fun updateTransaction(transaction: Transaction): Int
    suspend fun deleteTransaction(transaction: Transaction): Int
    suspend fun getAllTransactions(): List<Transaction>
    suspend fun getIncomeTransactions(): List<Transaction>
    suspend fun getExpenseTransactions(): List<Transaction>

    suspend fun getCurrentBalance(): Double

    suspend fun hasAnyTransactions(): Boolean

    suspend fun getTotalIncomes(): Double
    suspend fun getTotalExpenses(): Double

    suspend fun getIncomeCount(): Int
    suspend fun getExpenseCount(): Int

    fun getTotalIncomesLive(): LiveData<Double>
    fun getTotalExpensesLive(): LiveData<Double>

    suspend fun deleteAllTransactions()
}