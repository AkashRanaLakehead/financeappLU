package akash.rana.financeapp.models.categoryitems

import akash.rana.financeapp.models.Transaction

data class TransactionItem(
    val transaction: Transaction
) : CategoryItem()