package com.ansar.autoPartsApp.domain.manager

import com.ansar.autoPartsApp.domain.manager.store.TokenStore
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

enum class Role(val id: Int) {
    USER(10), CAPPER(20)
}

interface SessionManager {

    val isAuth: Boolean

    fun auth(token: String, refresh: String)
    val role: StateFlow<Role>
    fun setRole(role: Role)

    //    var profile: ProfileUI?
    fun setFcmToken(token: String)

    fun exit()
    fun clear()


}

class SessionManagerImpl(
    private val tokenStore: TokenStore,
    private val settings: Settings,
    private val json: Json
) : SessionManager {


    override val isAuth: Boolean
        get() = tokenStore.token.isNotBlank()

    override fun auth(token: String, refresh: String) {
        tokenStore.save(token, refresh)
    }


    private val _role = MutableStateFlow(Role.USER)
    override val role: StateFlow<Role> = _role.asStateFlow()


    override fun setRole(role: Role) {
        settings[ROLE] = role.id
        _role.value = role
    }


    init {
        setRole(Role.entries.find { it.id == settings.getInt(ROLE, 0) } ?: Role.USER)
    }

//    override var profile: ProfileUI?
//        get() = try {
//            val profile = settings.getString(PROFILE, "")
//            if (profile.isBlank()) null
//            else json.decodeFromString(ProfileUI.serializer(), profile)
//        } catch (e: Exception) {
//            null
//        }
//        set(value) {
//            if (value != null) {
//                val profileJson = json.encodeToString(ProfileUI.serializer(), value)
//                settings.putString(PROFILE, profileJson)
//            } else {
//                settings.putString(PROFILE, "")
//            }
//        }

    override fun setFcmToken(token: String) {
        tokenStore.fcmToken = token
    }


    override fun exit() {
        val fcmToken = tokenStore.fcmToken
        tokenStore.clear()
        setFcmToken(fcmToken)
//        profile = null
    }

    override fun clear() {
        val fcmToken = tokenStore.fcmToken
        settings.clear()
        setFcmToken(fcmToken)
    }

    companion object {
        private const val PROFILE = "PROFILE"
        private const val ROLE = "ROLE"
    }


}