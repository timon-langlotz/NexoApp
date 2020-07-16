package com.adyen.nexoapp.terminal

import java.util.*

// todo object can eventually retain some previous selected terminals to quickly select one on UI

object SelectedTerminalInfo {

    private var _terminalModelName: String = ""
    private var _serialNumber: String = ""
    private val _serialNumberLength: Int = 9
    private var _ipAddress: String = ""

    val poiId: String
        get() {
            val trimmedSerialNumber = _serialNumber.trim()

            if (trimmedSerialNumber.length != _serialNumberLength) {
                throw IllegalArgumentException("Invalid serial number length")
            }

            if (_terminalModelName.isEmpty()) {
                throw IllegalArgumentException("No terminal model specified")
            }

            return "$_terminalModelName-$trimmedSerialNumber"
        }

    var terminalModelName: String
        get() = this._terminalModelName
        set(newModelName) {
            if (newModelName.isEmpty()) {
                throw IllegalArgumentException("No terminal model specified")
            }
            this._terminalModelName = newModelName
        }

    var ipAddress: String
        get() = this._ipAddress
        set(newIpAddress) {
            if (newIpAddress.isEmpty()) {
                throw IllegalArgumentException("No IP address specified")
            }
            this._ipAddress = newIpAddress
        }

    var serialNumber: String
        get() = this._serialNumber
        set(newSerialNumber) {
            if (newSerialNumber.isEmpty()) {
                throw IllegalArgumentException("No terminal serial number specified")
            }
            this._serialNumber = newSerialNumber
        }
}