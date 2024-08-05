package akash.rana.financeapp.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "category")
    val category: Category,

    @ColumnInfo(name = "date")
    val date: Date,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "transaction_type")
    val transactionType: TransactionType
) : Parcelable