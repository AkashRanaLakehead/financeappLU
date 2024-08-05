package akash.rana.financeapp.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import akash.rana.financeapp.databinding.FragmentAddIncomeBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import akash.rana.financeapp.R
import akash.rana.financeapp.adapters.CategoryArrayAdapter
import akash.rana.financeapp.fragments.AddTransactionBottomSheetFragment
import akash.rana.financeapp.models.Category
import akash.rana.financeapp.models.Transaction
import akash.rana.financeapp.models.TransactionType
import akash.rana.financeapp.viewmodels.TransactionViewModel
import java.util.*

class AddIncomeFragment : Fragment() {
    private lateinit var binding: FragmentAddIncomeBinding
    private val viewModel: TransactionViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_income, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = CategoryArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            Category.values()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.adapter = adapter

        binding.submitIncomeButton.setOnClickListener {
            saveIncomeToDatabase()
        }

        return binding.root
    }

    private fun saveIncomeToDatabase() {
        val description = binding.descriptionEditText.text.toString()
        val category = binding.categorySpinner.selectedItem as Category
        val amount = binding.amountEditText.text.toString().toDoubleOrNull()

        if (amount != null) {
            val newIncome = Transaction(
                id = 0,
                amount = amount,
                category = category,
                date = Date(System.currentTimeMillis()),
                description = description,
                transactionType = TransactionType.INCOME
            )
            viewModel.insert(newIncome)
            Toast.makeText(requireContext(), "Income was successfully added", Toast.LENGTH_SHORT).show()
            try {
                (parentFragment as AddTransactionBottomSheetFragment).dismiss()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                requireActivity().onBackPressed()
            }
        } else {
            Toast.makeText(requireContext(), "Enter a valid amount", Toast.LENGTH_SHORT).show()
        }
    }
}
