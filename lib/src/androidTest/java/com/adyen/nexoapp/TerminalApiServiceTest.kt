package com.adyen.nexoapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.adyen.nexoapp.lib.TerminalApiService
import com.adyen.nexoapp.lib.model.api.SaleToPoiRequest
import com.adyen.nexoapp.lib.model.api.TerminalRequest
import com.adyen.nexoapp.lib.model.api.body.payment.AmountsReq
import com.adyen.nexoapp.lib.model.api.body.payment.PaymentRequest
import com.adyen.nexoapp.lib.model.api.body.payment.PaymentTransaction
import com.adyen.nexoapp.lib.model.api.body.payment.SaleData
import com.adyen.nexoapp.lib.model.api.body.payment.SaleTransactionId
import com.adyen.nexoapp.lib.model.api.header.MessageCategory
import com.adyen.nexoapp.lib.model.api.header.MessageHeader
import com.adyen.nexoapp.lib.util.toUtcTimeStamp
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class TerminalApiServiceTest {

    @Test
    fun testPaymentRequest() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val nexoService = TerminalApiService.forIpAddress(context, "<TODO>")

        val saleId = UUID.randomUUID().toString()
        val serviceId = "Test"
        val header = MessageHeader(MessageCategory.PAYMENT, saleId, serviceId, "<TODO>")

        val amountsReq = AmountsReq("EUR", 10.99)
        val paymentTransaction = PaymentTransaction(amountsReq)

        val timeStamp = Date().toUtcTimeStamp()
        val transactionId = UUID.randomUUID().toString()
        val saleTransactionId = SaleTransactionId(timeStamp, transactionId)
        val saleData = SaleData(saleTransactionId)
        val paymentRequest = PaymentRequest(paymentTransaction, saleData)

        val saleToPoiRequest = SaleToPoiRequest(header, paymentRequest)
        val terminalRequest = TerminalRequest(saleToPoiRequest)
        val response = nexoService
            .startPayment(terminalRequest)
            .execute()
        println(response)
    }
}
