package com.kursor.roombookkeeping.di

import com.kursor.roombookkeeping.viewModels.ReceiptListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        ReceiptListViewModel(getReceiptListUseCase = get())
    }

}