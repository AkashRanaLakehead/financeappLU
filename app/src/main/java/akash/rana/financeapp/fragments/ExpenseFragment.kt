package akash.rana.financeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import akash.rana.financeapp.adapters.CategoryTransactionAdapter
import akash.rana.financeapp.databinding.FragmentExpenseBinding
import akash.rana.financeapp.viewmodels.TransactionViewModel
import akash.rana.financeapp.models.Transaction
import akash.rana.financeapp.models.TransactionType
import akash.rana.financeapp.models.categoryitems.CategoryHeader
import akash.rana.financeapp.models.categoryitems.CategoryItem
import akash.rana.financeapp.models.categoryitems.TransactionItem

class ExpenseFragment : Fragment() {

    private var _binding: FragmentExpenseBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TransactionViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val onTransactionItemClickListener: (Transaction) -> Unit = { transaction ->
            showTransactionDetailBottomSheet(transaction)
        }

        val categoryTransactionAdapter = CategoryTransactionAdapter(onTransactionItemClickListener)
        binding.expenseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.expenseRecyclerView.adapter = categoryTransactionAdapter
        viewModel.allTransactions.observe(viewLifecycleOwner) { transactions ->
            val filteredExpenseTransactions = filterExpenseTransactions(transactions)
            val categoryItems = createCategoryItems(filteredExpenseTransactions)
            categoryTransactionAdapter.submitList(categoryItems)
        }
    }

    private fun filterExpenseTransactions(transactions: List<Transaction>): List<Transaction> {
        return transactions.filter { it.transactionType == TransactionType.EXPENSE }
    }

    private fun createCategoryItems(transactions: List<Transaction>): List<CategoryItem> {
        val categoryItems = mutableListOf<CategoryItem>()
        val groupedTransactions = transactions.groupBy { it.category }
        groupedTransactions.forEach { (category, transactions) ->
            categoryItems.add(CategoryHeader(category))
            transactions.forEach { transaction ->
                categoryItems.add(TransactionItem(transaction))
            }
        }

        return categoryItems
    }

    private fun showTransactionDetailBottomSheet(transaction: Transaction) {
        val transactionDetailFragment = TransactionDetailFragment.newInstance(transaction)
        transactionDetailFragment.show(childFragmentManager, TransactionDetailFragment.TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}