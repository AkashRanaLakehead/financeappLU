package akash.rana.financeapp.repositories

import akash.rana.financeapp.data.TransactionDao
import akash.rana.financeapp.models.Expense

class ExpenseRepository(private val transactionDao: TransactionDao) {

    suspend fun getAllExpenses(): List<Expense> {
        return transactionDao.getAllExpenses()
    }

    suspend fun insertExpense(expense: Expense) {
        transactionDao.insertExpense(expense)
    }

    suspend fun updateExpense(expense: Expense) {
        transactionDao.updateExpense(expense)
    }

    suspend fun deleteExpense(expense: Expense) {
        transactionDao.deleteExpense(expense)
    }
}