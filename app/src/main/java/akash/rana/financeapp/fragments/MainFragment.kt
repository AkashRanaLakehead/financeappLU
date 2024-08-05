package akash.rana.financeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import akash.rana.financeapp.R
import akash.rana.financeapp.databinding.FragmentMainBinding
import akash.rana.financeapp.models.Category
import akash.rana.financeapp.models.Transaction
import akash.rana.financeapp.models.TransactionType
import akash.rana.financeapp.viewmodels.TransactionViewModel
import java.util.*
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class MainFragment : Fragment(){
    private lateinit var binding: FragmentMainBinding
    private val viewModel: TransactionViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.addTransactionButton.setOnClickListener {
            showAddTransactionBottomSheet(TransactionType.INCOME)
        }
        binding.deleteAllTransactionButton.setOnClickListener {
            deleteAllTransaction()
        }
        addInitialTransactionIfEmpty()
        setupObservers()
        setupPieChart()
        return binding.root
    }

    private fun showAddTransactionBottomSheet(transactionType: TransactionType) {
        val bottomSheetDialogFragment = AddTransactionBottomSheetFragment.newInstance(transactionType)
        bottomSheetDialogFragment.show(childFragmentManager, AddTransactionBottomSheetFragment.TAG)
    }

    private fun addInitialTransactionIfEmpty() {
        lifecycleScope.launch {
            val hasAnyTransactions = viewModel.hasAnyTransactions()
            if (!hasAnyTransactions) {
                val initialTransaction = Transaction(
                    id = 0,
                    amount = 3.0,
                    category = Category.OTHER,
                    date = Date(System.currentTimeMillis()),
                    description = "First income",
                    transactionType = TransactionType.INCOME
                )

                val initialTransactionExpense = Transaction(
                    id = 0,
                    amount = 2.0,
                    category = Category.OTHER,
                    date = Date(System.currentTimeMillis()),
                    description = "First expense",
                    transactionType = TransactionType.EXPENSE
                )

                viewModel.insert(initialTransaction)
                viewModel.insert(initialTransactionExpense)

            }
        }
    }

    private fun setupObservers() {
        viewModel.totalIncomesLive.observe(viewLifecycleOwner, { total ->
            binding.totalIncomesTextView.text = total.toString()
        })

        viewModel.totalExpensesLive.observe(viewLifecycleOwner, { total ->
            binding.totalExpensesTextView.text = total.toString()
        })

        viewModel.totalIncomeCount.observe(viewLifecycleOwner, { count ->
            binding.incomeCountTextView.text = count.toString()
        })

        viewModel.totalExpenseCount.observe(viewLifecycleOwner, { count ->
            binding.expenseCountTextView.text = count.toString()
        })

        viewModel.balance.observe(viewLifecycleOwner, { balance ->
            binding.balanceTextView.text = getString(R.string.balance_format, balance)
        })


    }

    private fun setupPieChart() {
        val pieChart = binding.pieChart

        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.setCenterText("Incomes and Expenses")

        pieChart.setCenterTextSize(18f)

        updatePieChart()

        viewModel.totalIncomesLive.observe(viewLifecycleOwner) {
            updatePieChart()
        }
        viewModel.totalExpensesLive.observe(viewLifecycleOwner) {
            updatePieChart()
        }
    }

    private fun updatePieChart() {
        val pieChart = binding.pieChart

        lifecycleScope.launch {
            val totalIncomes = viewModel.totalIncomesLive.value ?:0.0
            val totalExpenses = viewModel.totalExpensesLive.value ?:0.0

            val entries = ArrayList<PieEntry>().apply {
                add(PieEntry(totalIncomes.toFloat(), "Incomes"))
                add(PieEntry(totalExpenses.toFloat(), "Expenses"))
            }

            val dataSet = PieDataSet(entries, "")
            dataSet.setColors(
                ContextCompat.getColor(requireContext(), R.color.colorPrimary),
                ContextCompat.getColor(requireContext(), R.color.nightColorError),
            )
            val pieData = PieData(dataSet).apply {
                setValueTextSize(16f)
            }
            pieChart.data = pieData
            pieChart.invalidate()
        }
    }

    private fun deleteAllTransaction(){

        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.delete_all_transactions_title))
            .setMessage(getString(R.string.delete_all_transactions_message))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                lifecycleScope.launch {
                    viewModel.deleteAllTransactions()
                   }
                addInitialTransactionIfEmpty()
            }
            .setNegativeButton(getString(R.string.no), null)
            .create()
        alertDialog.show()
    }
}