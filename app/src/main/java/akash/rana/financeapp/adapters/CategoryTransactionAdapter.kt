package akash.rana.financeapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import akash.rana.financeapp.databinding.CategoryHeaderBinding
import akash.rana.financeapp.databinding.TransactionItemBinding
import akash.rana.financeapp.models.Transaction
import akash.rana.financeapp.models.categoryitems.CategoryHeader
import akash.rana.financeapp.models.categoryitems.CategoryItem
import akash.rana.financeapp.models.categoryitems.TransactionItem
import java.text.DateFormat

class CategoryTransactionAdapter(
    private val onTransactionItemClickListener: (Transaction) -> Unit
) : ListAdapter<CategoryItem, RecyclerView.ViewHolder>(CategoryItemDiffCallback()) {

    companion object {
        private const val ITEM_VIEW_TYPE_HEADER = 0
        private const val ITEM_VIEW_TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> CategoryHeaderViewHolder(
                CategoryHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            ITEM_VIEW_TYPE_ITEM -> TransactionViewHolder(
                TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                onTransactionItemClickListener
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoryHeaderViewHolder -> {
                val headerItem = getItem(position) as CategoryHeader
                holder.bind(headerItem)
            }
            is TransactionViewHolder -> {
                val transactionItem = getItem(position) as TransactionItem
                holder.bind(transactionItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CategoryHeader -> ITEM_VIEW_TYPE_HEADER
            else -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class CategoryHeaderViewHolder(private val binding: CategoryHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(header: CategoryHeader) {
            binding.category = header.category
            binding.executePendingBindings()
        }
    }

    class TransactionViewHolder(
        private val binding: TransactionItemBinding,
        private val onTransactionItemClickListener: (Transaction) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transactionItem: TransactionItem) {
            binding.transaction = transactionItem.transaction

            binding.transactionTitle.text = transactionItem.transaction.description
            binding.transactionAmount.text = String.format("%.2f", transactionItem.transaction.amount)
            binding.transactionDate.text = DateFormat.getDateInstance(DateFormat.DEFAULT).format(transactionItem.transaction.date)
            binding.transactionCategory.text = transactionItem.transaction.category.description.toString()

            binding.root.setOnClickListener { onTransactionItemClickListener(transactionItem.transaction) }
            binding.executePendingBindings()
        }
    }

    class CategoryItemDiffCallback : DiffUtil.ItemCallback<CategoryItem>() {
        override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem == newItem
        }
    }
}