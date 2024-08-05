package akash.rana.financeapp.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import akash.rana.financeapp.viewmodels.TransactionViewModel
import akash.rana.financeapp.data.FinancePlusDatabase
import akash.rana.financeapp.repositories.ExpenseRepository
import akash.rana.financeapp.repositories.IncomeRepository
import akash.rana.financeapp.repositories.TransactionRepository
import akash.rana.financeapp.repositories.impl.TransactionRepositoryImpl

val uiModule = module {
    viewModel {
        TransactionViewModel(
            get(),
            get(),
            get()
        )
    }
}

val dataModule = module {
    single { FinancePlusDatabase.getDatabase(get()) }
    single { get<FinancePlusDatabase>().transactionDao() }
    single<TransactionRepository> { TransactionRepositoryImpl(get()) }
    single<IncomeRepository> { IncomeRepository(get()) }
    single<ExpenseRepository> { ExpenseRepository(get()) }
}

val repositoriesModule = module {
    single<TransactionRepository> { TransactionRepositoryImpl(get()) }
    single { IncomeRepository(get()) }
    single { ExpenseRepository(get()) }
}