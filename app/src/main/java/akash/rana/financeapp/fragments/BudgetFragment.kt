package akash.rana.financeapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import akash.rana.financeapp.adapters.TransactionAdapter
import akash.rana.financeapp.databinding.FragmentBudgetBinding
import akash.rana.financeapp.viewmodels.TransactionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import akash.rana.financeapp.models.Transaction

class BudgetFragment : Fragment(), TransactionAdapter.OnTransactionItemClickListener {

    private var _binding: FragmentBudgetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val transactionRecyclerView = binding.transactionRecyclerView
        val transactionAdapter = TransactionAdapter(this)
        transactionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        transactionRecyclerView.adapter = transactionAdapter
        viewModel.allTransactions.observe(viewLifecycleOwner) { transactions ->
            transactionAdapter.submitList(transactions)
        }
    }

    override fun onTransactionItemClick(transaction: Transaction) {
        val transactionDetailFragment = TransactionDetailFragment.newInstance(transaction)
        transactionDetailFragment.show(childFragmentManager, TransactionDetailFragment.TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}