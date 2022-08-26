package com.kursor.roombookkeeping.presentation.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.kursor.roombookkeeping.model.Receipt
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ReceiptLayout(receipt: com.kursor.roombookkeeping.model.Receipt) {
    Column {
        Text(
            text = receipt.name,
            style = TextStyle(
                fontSize = 22.sp
            )
        )
        Text(
            text = SimpleDateFormat(
                "yyyy.MM.dd hh:mm:ss",
                Locale.getDefault()
            ).format(receipt.dateTime),
            style = TextStyle(
                fontSize = 14.sp
            )
        )
    }
}

@Preview
@Composable
fun PreviewReceiptLayout() {
    ReceiptLayout(receipt = com.kursor.roombookkeeping.model.Receipt(
        name = "Default",
        dateTime = Date(System.currentTimeMillis()),
        priceList = emptyList(),
        persons = emptyList()
    )
    )
}

