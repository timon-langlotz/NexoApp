package com.adyen.nexoapp.util

import android.content.Context
import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager
import java.math.BigInteger
import java.net.InetAddress
import java.net.UnknownHostException
import java.nio.ByteOrder


object WifiUtil {
    fun getIpAddress(context: Context): String? {
        val wifiManager = context.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val ipAddress = wifiManager.connectionInfo?.ipAddress?.reverseBytesIfLittleEndian()
            ?: return null
        val ipByteArray = BigInteger.valueOf(ipAddress.toLong()).toByteArray()

        return try {
            InetAddress.getByAddress(ipByteArray).hostAddress
        } catch (ex: UnknownHostException) {
            null
        }
    }

    fun getNetworkAddress(context: Context): String? {
        val ipAddress = getIpAddress(context) ?: return null

        return ipAddress
            .split(".")
            .joinToString(".", limit = 3, truncated = "") { it }
    }

    private fun Int.reverseBytesIfLittleEndian(): Int {
        return if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            Integer.reverseBytes(this)
        } else {
            this
        }
    }
}
