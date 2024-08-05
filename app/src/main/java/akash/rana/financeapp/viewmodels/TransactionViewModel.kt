package akash.rana.financeapp.viewmodels

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import akash.rana.financeapp.models.categoryitems.CategoryItem
import akash.rana.financeapp.models.Expense
import akash.rana.financeapp.models.Income
import akash.rana.financeapp.models.Transaction
import akash.rana.financeapp.models.categoryitems.CategoryHeader
import akash.rana.financeapp.models.categoryitems.TransactionItem
import akash.rana.financeapp.repositories.ExpenseRepository
import akash.rana.financeapp.repositories.IncomeRepository
import akash.rana.financeapp.repositories.TransactionRepository
import kotlin.math.abs

class TransactionViewModel(
    private val repository: TransactionRepository,
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository
) : ViewModel(), KoinComponent {

    private val _allTransactions = MutableLiveData<List<Transaction>>()
    val allTransactions: LiveData<List<Transaction>> = _allTransactions

    private val _incomeTransactions = MutableLiveData<List<Transaction>>()
    val incomeTransactions: LiveData<List<Transaction>> = _incomeTransactions

    private val _expenseTransactions = MutableLiveData<List<Transaction>>()
    val expenseTransactions: LiveData<List<Transaction>> = _expenseTransactions

    private val _allIncomes = MutableLiveData<List<Income>>()
    val allIncomes: LiveData<List<Income>> = _allIncomes

    private val _allExpenses = MutableLiveData<List<Expense>>()
    val allExpenses: LiveData<List<Expense>> = _allExpenses

    private val _currentBalance = MutableLiveData<Double>()
    val currentBalance: LiveData<Double> = _currentBalance

    //Získání celkových příjmů pro main fragment
    private val _totalIncomes = MutableLiveData<Double>()
    val totalIncomes: LiveData<Double> get() = _totalIncomes
    //Získání celkových výdajů pro main fragment
    private val _totalExpenses = MutableLiveData<Double>()
    val totalExpenses: LiveData<Double> get() = _totalExpenses

    private val _totalIncomeCount = MutableLiveData<Int>()
    val totalIncomeCount: LiveData<Int> = _totalIncomeCount

    private val _totalExpenseCount = MutableLiveData<Int>()
    val totalExpenseCount: LiveData<Int> = _totalExpenseCount

    init {
        loadAllTransactions()
        loadIncomeTransactions()
        loadExpenseTransactions()
        loadCurrentBalance()
        loadTotalIncomesAndCount()
        loadTotalExpensesAndCount()
    }

    private fun loadAllTransactions() = viewModelScope.launch {
        _allTransactions.value = repository.getAllTransactions()
    }

    private fun loadIncomeTransactions() = viewModelScope.launch {
        _incomeTransactions.value = repository.getIncomeTransactions()
    }

    private fun loadExpenseTransactions() = viewModelScope.launch {
        _expenseTransactions.value = repository.getExpenseTransactions()
    }

    // Metoda pro přidání transakce
    fun insert(transaction: Transaction) = viewModelScope.launch {
        repository.insertTransaction(transaction)
        loadAllTransactions()
        loadIncomeTransactions()
        loadExpenseTransactions()
    }

    // Metoda pro aktualizaci transakce
    fun update(transaction: Transaction) = viewModelScope.launch {
        repository.updateTransaction(transaction)
        loadAllTransactions()
        loadIncomeTransactions()
        loadExpenseTransactions()
    }

    // Metoda pro smazání transakce
    fun delete(transaction: Transaction) = viewModelScope.launch {
        repository.deleteTransaction(transaction)
        loadAllTransactions()
        loadIncomeTransactions()
        loadExpenseTransactions()
    }

    fun refreshData() {
        // This method triggers the data refresh logic
        loadAllTransactions()
    }

    // Metody pro Income

    init {
        getAllIncomes()
        getAllExpenses()
    }

    private fun getAllIncomes() = viewModelScope.launch {
        _allIncomes.value = incomeRepository.getAllIncomes()
    }

    fun insertIncome(income: Income) = viewModelScope.launch {
        incomeRepository.insertIncome(income)
        getAllIncomes()
    }

    fun updateIncome(income: Income) = viewModelScope.launch {
        incomeRepository.updateIncome(income)
        getAllIncomes()
    }

    fun deleteIncome(income: Income) = viewModelScope.launch {
        incomeRepository.deleteIncome(income)
        getAllIncomes()
    }

    // Metody pro Expense

    private fun getAllExpenses() = viewModelScope.launch {
        _allExpenses.value = expenseRepository.getAllExpenses()
    }

    fun insertExpense(expense: Expense) = viewModelScope.launch {
        expenseRepository.insertExpense(expense)
        getAllExpenses()
    }

    fun updateExpense(expense: Expense) = viewModelScope.launch {
        expenseRepository.updateExpense(expense)
        getAllExpenses()
    }

    fun deleteExpense(expense: Expense) = viewModelScope.launch {
        expenseRepository.deleteExpense(expense)
        getAllExpenses()
    }

    private fun loadCurrentBalance() = viewModelScope.launch {
        _currentBalance.value = repository.getCurrentBalance()
    }

    suspend fun hasAnyTransactions(): Boolean {
        return repository.hasAnyTransactions()
    }

    private fun loadTotalIncomesAndCount() {
        viewModelScope.launch {
            _totalIncomes.value = repository.getTotalIncomes()
            _totalIncomeCount.value = repository.getIncomeCount()
        }
    }

    private fun loadTotalExpensesAndCount() {
        viewModelScope.launch {
            _totalExpenses.value = repository.getTotalExpenses()
            _totalExpenseCount.value = repository.getExpenseCount()
        }
    }


    fun prepareCategoryItems(transactions: List<Transaction>): List<CategoryItem> {
        val categoryItems = mutableListOf<CategoryItem>()

        // Třídit transakce podle kategorie
        val transactionsByCategory = transactions.groupBy { it.category }

        // Procházet každou kategorii a vytvářet CategoryHeader a TransactionItem objekty
        transactionsByCategory.forEach { (category, transactions) ->
            categoryItems.add(CategoryHeader(category))
            transactions.forEach { transaction ->
                categoryItems.add(TransactionItem(transaction))
            }
        }
        return categoryItems
    }

    val totalIncomesLive: LiveData<Double> = repository.getTotalIncomesLive()
    val totalExpensesLive: LiveData<Double> = repository.getTotalExpensesLive()

    val balance: LiveData<Double> = MediatorLiveData<Double>().apply {
        var income = 0.0
        var expense = 0.0

        fun update() {
            value = income - abs(expense)
        }

        addSource(totalIncomesLive) { newIncome ->
            income = newIncome ?: 0.0
            update()
        }

        addSource(totalExpensesLive) { newExpense ->
            expense = newExpense ?: 0.0
            update()
        }
    }

    suspend fun deleteAllTransactions(){
        viewModelScope.launch {
            repository.deleteAllTransactions()
        }
    }
}