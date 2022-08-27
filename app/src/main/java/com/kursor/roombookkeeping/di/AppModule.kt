package com.kursor.roombookkeeping.di

import com.kursor.roombookkeeping.domain.usecases.receipt.AddPriceToReceiptUseCase
import com.kursor.roombookkeeping.domain.usecases.receipt.DeletePriceFromReceiptUseCase
import com.kursor.roombookkeeping.domain.usecases.receipt.crud.GetReceiptUseCase
import com.kursor.roombookkeeping.viewModels.PriceViewModel
import com.kursor.roombookkeeping.viewModels.ReceiptListViewModel
import com.kursor.roombookkeeping.viewModels.ReceiptViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        ReceiptListViewModel(getReceiptListUseCase = get())
    }

    viewModel { parameters ->
        ReceiptViewModel(
            createReceiptUseCase = get(),
            updateReceiptUseCase = get(),
            getReceiptUseCase = get()
        )
    }

    viewModel { parameters ->
        PriceViewModel(
            addPriceToReceiptUseCase = get(),
            editPriceUseCase = get(),
            addPersonToPriceUseCase = get(),
            deletePersonFromPriceUseCase = get(),
            receipt = parameters.get(),
            price = parameters.get()
        )
    }

}