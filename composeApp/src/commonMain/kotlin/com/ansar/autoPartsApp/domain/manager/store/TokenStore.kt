package com.ansar.autoPartsApp.domain.manager.store

import com.russhwolf.settings.Settings
import kotlinx.serialization.json.Json

interface TokenStore {
    val token: String
    val refresh: String

    var fcmToken: String

    fun save(
        token: String,
        refresh: String
    )

    fun clear()
}

class TokenStoreImpl(private val settings: Settings, private val json: Json) : TokenStore {
    override val token: String
        get() = settings.getString(TOKEN, "")
    override var fcmToken: String
        get() = settings.getString(FCM_TOKEN, "")
        set(value) {
            settings.putString(FCM_TOKEN, value)
        }
    override val refresh: String
        get() = settings.getString(REFRESH, "")

    override fun save(token: String, refresh: String) {
        settings.putString(TOKEN, token)
        settings.putString(REFRESH, refresh)
    }

    override fun clear() {
        settings.remove(TOKEN)
        settings.remove(REFRESH)
    }


    companion object {
        private const val BASE = "TokenManager"
        private const val TOKEN = BASE + "TOKEN"
        private const val FCM_TOKEN = BASE + "FCM_TOKEN"
        private const val REFRESH = BASE + "REFRESH"
    }
}