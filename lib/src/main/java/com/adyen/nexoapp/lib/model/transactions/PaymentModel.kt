package com.adyen.nexoapp.lib.model.transactions

import android.app.Application
import android.content.Context
import com.adyen.nexoapp.lib.model.api.SaleToPoiRequest
import com.adyen.nexoapp.lib.model.api.TerminalRequest
import com.adyen.nexoapp.lib.model.api.body.payment.*
import com.adyen.nexoapp.lib.model.api.header.MessageCategory
import com.adyen.nexoapp.lib.model.api.header.MessageHeader
import com.adyen.nexoapp.lib.util.Random
import com.adyen.nexoapp.lib.util.toUtcTimeStamp
import java.util.*

class PaymentModel(val application: Application,
                   val poiId: String,
                   val currency: String,
                   val amount: Double) : TransactionModel {

    private val saleId by lazy {
        val sharedPrefs = application.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        sharedPrefs.getString(NAME_SALE_ID, null) ?: {
            val newSaleId = UUID.randomUUID().toString()
            sharedPrefs.edit().putString(NAME_SALE_ID, newSaleId).apply()
            newSaleId
        }.invoke()
    }

    override fun createTerminalRequest() : TerminalRequest {
        val transactionId = Random.alphaNumeric(16)
        val serviceId = Random.alphaNumeric(10)
        val header = MessageHeader(MessageCategory.PAYMENT, saleId, serviceId, poiId)
        val amountsReq = AmountsReq(currency, amount)
        val paymentTransaction = PaymentTransaction(amountsReq)
        val timeStamp = Date().toUtcTimeStamp()
        val saleTransactionId = SaleTransactionId(timeStamp, transactionId)
        val saleData = SaleData(saleTransactionId)
        val paymentRequest = PaymentRequest(paymentTransaction, saleData)
        val saleToPoiRequest = SaleToPoiRequest(header, paymentRequest)
        return TerminalRequest(saleToPoiRequest)
    }

    companion object {
        private const val SHARED_PREFS_NAME = "NexoCommunicator"
        private const val NAME_SALE_ID = "SALE_ID"
    }

}
