package com.kursor.roombookkeeping.di

import com.kursor.roombookkeeping.viewModels.price.PriceViewModel
import com.kursor.roombookkeeping.viewModels.receipt.ReceiptListViewModel
import com.kursor.roombookkeeping.viewModels.receipt.ReceiptViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        ReceiptListViewModel(getReceiptListUseCase = get())
    }

    viewModel {
        ReceiptViewModel(
            createReceiptUseCase = get(),
            updateReceiptUseCase = get(),
            getReceiptUseCase = get()
        )
    }

    viewModel {
        PriceViewModel(
            addPriceToReceiptUseCase = get(),
            editPriceUseCase = get(),
            addPersonToPriceUseCase = get(),
            deletePersonFromPriceUseCase = get(),
            getReceiptUseCase = get()
        )
    }

}