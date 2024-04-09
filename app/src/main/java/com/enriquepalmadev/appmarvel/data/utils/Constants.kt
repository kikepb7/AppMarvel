package com.enriquepalmadev.appmarvel.data.utils

import java.math.BigInteger
import java.security.MessageDigest

class Constants {

    companion object{
        const val BASE_URL = "https://gateway.marvel.com"
        const val TIMESTAMP = "1"
        const val API_KEY = "69b46fe35ca74b0316851989315b6e77"
        const val PRIVATE_KEY = "16f72fcd18e348b4014e6d979d2c10e39d8ef90e"
        const val LIMIT = "20"

        fun hash(): String {
            val input = "$TIMESTAMP$PRIVATE_KEY$API_KEY"
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1,md.digest(input.toByteArray())).toString(16)
        }
    }
}