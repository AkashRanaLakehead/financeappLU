package akash.rana.financeapp

import akash.rana.financeapp.viewmodels.TransactionViewModel
import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class RefreshDatabaseService : Service() {

    private val viewModel: TransactionViewModel by inject()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CoroutineScope(Dispatchers.IO).launch {
            refreshDatabase()

            val broadcastIntent = Intent("com.akash.rana.financeapp.ACTION_REFRESH")
            sendBroadcast(broadcastIntent)
        }
        return START_NOT_STICKY
    }

    private suspend fun refreshDatabase() {
        // Replace this with actual logic to refresh your database
        viewModel.refreshData()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}