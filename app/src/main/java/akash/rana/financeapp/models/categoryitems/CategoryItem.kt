package akash.rana.financeapp.models.categoryitems

import akash.rana.financeapp.models.Transaction

sealed class CategoryItem {
    data class CategoryTitle(val title: String) : CategoryItem()
    data class CategoryTransaction(val transaction: Transaction) : CategoryItem()
}