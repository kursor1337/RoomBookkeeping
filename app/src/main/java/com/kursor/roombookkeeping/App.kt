package com.kursor.roombookkeeping

import android.app.Application
import com.kursor.roombookkeeping.di.appModule
import com.kursor.roombookkeeping.di.dataModule
import com.kursor.roombookkeeping.di.domainModule
import com.kursor.roombookkeeping.domain.repositories.ReceiptRepository
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    lateinit var loaded: Job

    override fun onCreate() {
        super.onCreate()

        val receiptRepository by inject<ReceiptRepository>()
        loaded = CoroutineScope(Dispatchers.IO).async {
            val current = System.currentTimeMillis()
            val receipts = receiptRepository.getAll()
            for (receipt in receipts) {
                if (receipt.id + 604_800_000 < current) {
                    receiptRepository.delete(receipt)
                }
            }
        }

        startKoin {
            androidContext(this@App)
            modules(appModule, dataModule, domainModule)
        }

    }

}