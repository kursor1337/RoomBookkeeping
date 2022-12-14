package com.kursor.roombookkeeping.di

import com.kursor.roombookkeeping.domain.usecases.CalculateOutcomesUseCase
import com.kursor.roombookkeeping.domain.usecases.person.*
import com.kursor.roombookkeeping.domain.usecases.price.AddPersonToPriceUseCase
import com.kursor.roombookkeeping.domain.usecases.price.DeletePersonFromPriceUseCase
import com.kursor.roombookkeeping.domain.usecases.price.EditPriceUseCase
import com.kursor.roombookkeeping.domain.usecases.receipt.AddPriceToReceiptUseCase
import com.kursor.roombookkeeping.domain.usecases.receipt.DeletePriceFromReceiptUseCase
import com.kursor.roombookkeeping.domain.usecases.receipt.crud.*
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetReceiptListUseCase(receiptRepository = get())
    }

    factory {
        GetReceiptUseCase(receiptRepository = get())
    }

    factory {
        EditReceiptUseCase(receiptRepository = get())
    }

    factory {
        DeleteReceiptUseCase(receiptRepository = get())
    }

    factory {
        CreateReceiptUseCase(receiptRepository = get())
    }

    factory {
        AddPriceToReceiptUseCase(receiptRepository = get())
    }

    factory {
        DeletePriceFromReceiptUseCase(receiptRepository = get())
    }

    factory {
        AddPersonToPriceUseCase(
            receiptRepository = get()
        )
    }

    factory {
        DeletePersonFromPriceUseCase(
            receiptRepository = get()
        )
    }

    factory {
        EditPriceUseCase(
            receiptRepository = get()
        )
    }

    factory {
        AddPersonUseCase(personRepository = get())
    }

    factory {
        DeletePersonUseCase(personRepository = get())
    }

    factory {
        EditPersonNameUseCase(personRepository = get())
    }

    factory {
        GetPersonListUseCase(personRepository = get())
    }

    factory {
        GetPersonUseCase(personRepository = get())
    }

    factory {
        CalculateOutcomesUseCase()
    }

}