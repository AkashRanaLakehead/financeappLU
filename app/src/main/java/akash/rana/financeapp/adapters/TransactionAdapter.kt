package akash.rana.financeapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import akash.rana.financeapp.databinding.TransactionItemBinding
import akash.rana.financeapp.models.Transaction

class TransactionAdapter(private val onTransactionItemClickListener: OnTransactionItemClickListener) :
    ListAdapter<Transaction, TransactionAdapter.TransactionViewHolder>(TransactionDiffCallback()) {

    interface OnTransactionItemClickListener {
        fun onTransactionItemClick(transaction: Transaction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = TransactionItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(getItem(position), onTransactionItemClickListener)
    }

    class TransactionViewHolder(private val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: Transaction, onTransactionItemClickListener: OnTransactionItemClickListener) {
            binding.apply {
                // Here you can bind transaction data to views
                transactionTitle.text = transaction.description
                transactionAmount.text = transaction.amount.toString()
                transactionCategory.text = transaction.category.description
                transactionType.text = transaction.transactionType.toString()

                root.setOnClickListener {
                    onTransactionItemClickListener.onTransactionItemClick(transaction)
                }
            }
        }
    }

    class TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }
    }
}