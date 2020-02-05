package com.adyen.nexoapp.model.terminal

enum class TerminalModel(val model: String, val serialLength: Int) {
    P400("P400", 9);

    fun createPoiId(serialNumber: String): String {
        val trimmedSerialNumber = serialNumber.trim()

        if (trimmedSerialNumber.length != serialLength) {
            throw IllegalArgumentException("Invalid serial number length.")
        }

        return "$model-$trimmedSerialNumber"
    }
}
