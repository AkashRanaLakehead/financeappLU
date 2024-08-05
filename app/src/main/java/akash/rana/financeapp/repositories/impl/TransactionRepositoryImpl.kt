package akash.rana.financeapp.repositories.impl

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import akash.rana.financeapp.data.TransactionDao
import akash.rana.financeapp.models.Transaction
import akash.rana.financeapp.repositories.TransactionRepository
import kotlin.math.abs

class TransactionRepositoryImpl(private val transactionDao: TransactionDao) : TransactionRepository {

    @WorkerThread
    override suspend fun insertTransaction(transaction: Transaction): Long {
        return transactionDao.insertTransaction(transaction)
    }

    @WorkerThread
    override suspend fun updateTransaction(transaction: Transaction): Int {
        return transactionDao.updateTransaction(transaction)
    }

    @WorkerThread
    override suspend fun deleteTransaction(transaction: Transaction): Int {
        return transactionDao.deleteTransaction(transaction)
    }

    @WorkerThread
    override suspend fun getAllTransactions(): List<Transaction> {
        return transactionDao.getAllTransactions()
    }

    @WorkerThread
    override suspend fun getIncomeTransactions(): List<Transaction> {
        return transactionDao.getTransactionsByType("income")
    }

    @WorkerThread
    override suspend fun getExpenseTransactions(): List<Transaction> {
        return transactionDao.getTransactionsByType("expense")
    }

    @WorkerThread
    override suspend fun getCurrentBalance(): Double {
        val totalIncome = transactionDao.getTotalIncome() ?: 0.0
        val totalExpense = transactionDao.getTotalExpense() ?: 0.0
        return totalIncome - abs(totalExpense)
    }

    override suspend fun hasAnyTransactions(): Boolean {
        return transactionDao.countTransactions() > 0
    }

    override suspend fun getTotalIncomes(): Double {
        return transactionDao.getTotalIncome() ?: 0.0
    }

    override suspend fun getTotalExpenses(): Double {
        return transactionDao.getTotalExpense() ?: 0.0
    }

    override suspend fun getIncomeCount(): Int {
        return transactionDao.getIncomeCount()
    }

    override suspend fun getExpenseCount(): Int {
        return transactionDao.getExpenseCount()
    }

    override fun getTotalIncomesLive(): LiveData<Double> {
        return transactionDao.getTotalIncomeLive()
    }

    override fun getTotalExpensesLive(): LiveData<Double> {
        return transactionDao.getTotalExpenseLive()
    }

    @WorkerThread
    override suspend fun deleteAllTransactions() {
        return transactionDao.deleteAllTransactions()
    }
}