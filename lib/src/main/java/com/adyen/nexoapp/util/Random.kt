package com.adyen.nexoapp.util

import java.util.Random

object Random {
    private val random by lazy { Random() }
    private val alphaNumeric = ('0'..'9') + ('a'..'z') + ('A'..'Z')

    fun alphaNumeric(length: Int): String {
        val chars = CharArray(length)
        for (i in 0.until(length)) {
            chars[i] = alphaNumeric[random.nextInt(alphaNumeric.size)]
        }
        return String(chars)
    }
}
