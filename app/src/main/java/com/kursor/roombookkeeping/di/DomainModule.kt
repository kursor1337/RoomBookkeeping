package com.kursor.roombookkeeping.di

import com.kursor.roombookkeeping.domain.usecases.receipt.*
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetReceiptListUseCase(receiptRepository = get())
    }

    factory {
        GetReceiptUseCase(receiptRepository = get())
    }

    factory {
        UpdateReceiptUseCase(receiptRepository = get())
    }

    factory {
        DeleteReceiptUseCase(receiptRepository = get())
    }

    factory {
        CreateReceiptUseCase(receiptRepository = get())
    }

}