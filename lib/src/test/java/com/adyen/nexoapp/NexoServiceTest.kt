package com.adyen.nexoapp

import com.adyen.nexoapp.model.RequestWrapper
import com.adyen.nexoapp.model.SaleToPoiRequest
import com.adyen.nexoapp.model.body.payment.AmountsReq
import com.adyen.nexoapp.model.body.payment.PaymentRequest
import com.adyen.nexoapp.model.body.payment.PaymentTransaction
import com.adyen.nexoapp.model.body.payment.SaleData
import com.adyen.nexoapp.model.body.payment.SaleTransactionId
import com.adyen.nexoapp.model.header.MessageCategory
import com.adyen.nexoapp.model.header.MessageHeader
import com.adyen.nexoapp.util.toUtcTimeStamp
import org.junit.Test
import java.util.Date
import java.util.UUID

class NexoServiceTest {

    @Test
    fun testPaymentRequest() {
        val nexoService = NexoService.forIpAddress("<TODO>")

        val saleId = UUID.randomUUID().toString()
        val serviceId = "Test"
        val header = MessageHeader(MessageCategory.PAYMENT, saleId, serviceId, "<TODO>")

        val amountsReq = AmountsReq("EUR", "10.99")
        val paymentTransaction = PaymentTransaction(amountsReq)

        val timeStamp = Date().toUtcTimeStamp()
        val transactionId = UUID.randomUUID().toString()
        val saleTransactionId = SaleTransactionId(timeStamp, transactionId)
        val saleData = SaleData(saleTransactionId)
        val paymentRequest = PaymentRequest(paymentTransaction, saleData)

        val saleToPoiRequest = SaleToPoiRequest(header, paymentRequest)
        val requestWrapper = RequestWrapper(saleToPoiRequest)
        val response = nexoService
            .sendPayment(requestWrapper)
            .execute()
        println(response)
    }
}
