package com.ansar.autoPartsApp.domain.manager

import com.russhwolf.settings.Settings
import com.gntit.capperru.di.modules.Env

interface EnvManager {

    val env: Env
    fun set(env: Env)
}

class EnvManagerImpl(private val settings: Settings) : EnvManager {
    override val env: Env
        get() = when (settings.getString(ENV, "")) {
            Env.DEV.value -> Env.DEV
            Env.PROD.value -> Env.PROD
            Env.STAGE.value -> Env.STAGE
            else -> Env.PROD
        }

    override fun set(env: Env) {
        settings.putString(ENV, env.value)
    }

    companion object {
        private const val BASE = "EnvManager"
        private const val ENV = BASE + "SET_ENV"
    }
}