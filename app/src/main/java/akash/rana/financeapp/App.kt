package akash.rana.financeapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.BuildConfig
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import akash.rana.financeapp.di.dataModule
import akash.rana.financeapp.di.uiModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(uiModule, dataModule))
            androidContext(applicationContext)
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.NONE)
        }
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val name = getString(R.string.notification_channel_name)
        val descriptionText = getString(R.string.notification_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(GeneralNotificationChannelId, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
    companion object {
        const val GeneralNotificationChannelId = "general"
    }


}