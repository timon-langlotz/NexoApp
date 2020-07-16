package com.adyen.nexoapp.lib.model.transactions

import com.adyen.nexoapp.lib.model.api.TerminalRequest

interface TransactionModel {
    fun createTerminalRequest() : TerminalRequest
}

