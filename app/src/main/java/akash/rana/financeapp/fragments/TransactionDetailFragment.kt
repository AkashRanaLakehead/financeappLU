package akash.rana.financeapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import akash.rana.financeapp.R
import akash.rana.financeapp.models.Transaction
import akash.rana.financeapp.viewmodels.TransactionViewModel
import java.text.DateFormat

class TransactionDetailFragment : BottomSheetDialogFragment() {

    private lateinit var transaction: Transaction

    private lateinit var amountTextView: AppCompatTextView
    private lateinit var categoryTextView: AppCompatTextView
    private lateinit var dateTextView: AppCompatTextView
    private lateinit var descriptionTextView: AppCompatTextView
    private lateinit var transactionTypeTextView: AppCompatTextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_transaction_detail, container, false)

        amountTextView = view.findViewById(R.id.transaction_amount)
        categoryTextView = view.findViewById(R.id.transaction_category)
        dateTextView = view.findViewById(R.id.transaction_date)
        descriptionTextView = view.findViewById(R.id.transaction_description)
        transactionTypeTextView = view.findViewById(R.id.transaction_type)

        transaction = arguments?.getParcelable(ARG_TRANSACTION) ?: return view
        setTransactionData()

        val deleteTransactionButton: ImageButton = view.findViewById(R.id.delete_transaction_button)
        deleteTransactionButton.setOnClickListener {
            deleteTransaction()
        }

        return view
    }

    private fun setTransactionData() {
        if (::transaction.isInitialized) {
            amountTextView.text = transaction.amount.toString()
            categoryTextView.text = transaction.category.description
            dateTextView.text = DateFormat.getDateInstance().format(transaction.date)
            descriptionTextView.text = transaction.description
            transactionTypeTextView.text = transaction.transactionType.toString()
        }
    }

    companion object {
        const val TAG = "TransactionDetailFragment"

        private const val ARG_TRANSACTION = "arg_transaction"

        fun newInstance(transaction: Transaction): TransactionDetailFragment {
            val args = Bundle().apply {
                putParcelable(ARG_TRANSACTION, transaction)
            }
            return TransactionDetailFragment().apply {
                arguments = args
            }
        }
    }

    private fun deleteTransaction() {
        val viewModel: TransactionViewModel by sharedViewModel()
        viewModel.delete(transaction)
        dismiss()
    }
}