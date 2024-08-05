package akash.rana.financeapp.repositories

import akash.rana.financeapp.data.TransactionDao
import akash.rana.financeapp.models.Income

class IncomeRepository(private val transactionDao: TransactionDao) {

    suspend fun getAllIncomes(): List<Income> {
        return transactionDao.getAllIncomes()
    }

    suspend fun insertIncome(income: Income) {
        transactionDao.insertIncome(income)
    }

    suspend fun updateIncome(income: Income) {
        transactionDao.updateIncome(income)
    }

    suspend fun deleteIncome(income: Income) {
        transactionDao.deleteIncome(income)
    }
}