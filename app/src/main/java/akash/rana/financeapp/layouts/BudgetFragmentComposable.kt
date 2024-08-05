package akash.rana.financeapp.layouts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import akash.rana.financeapp.models.Category
import akash.rana.financeapp.models.Transaction
import akash.rana.financeapp.models.TransactionType
import akash.rana.financeapp.viewmodels.TransactionViewModel
import java.util.*

@Composable
fun BudgetFragmentComposable(
    viewModel: TransactionViewModel,
    onTransactionItemClick: (Transaction) -> Unit
) {
}

@Composable
fun TransactionItem(
    transaction: Transaction,
    onTransactionItemClick: (Transaction) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onTransactionItemClick(transaction) }
    ) {
        transaction.description?.let { Text(text = it) }
        Text(text = "${transaction.amount}")
    }
}

@Preview
@Composable
fun TransactionItemPreview() {
    val transaction = Transaction(
        id = 0,
        amount = 3.0,
        category = Category.OTHER,
        date = Date(System.currentTimeMillis()),
        description = "First income",
        transactionType = TransactionType.INCOME
    )
    TransactionItem(transaction = transaction, onTransactionItemClick = {})
}