package com.gntit.capperru.di.modules


import com.ansar.autoPartsApp.base.utils.Failure
import com.ansar.autoPartsApp.domain.manager.EnvManager
import com.ansar.autoPartsApp.domain.manager.SessionManager
import com.ansar.autoPartsApp.domain.manager.store.TokenStore
import com.ansar.autoPartsApp.platform.httpLogger
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
//https://capper-api.gnt-it.ru/
//https://api.cappers.space/
//
enum class Env(val value: String, val url: String) {
    DEV("dev", "https://capper-api.gnt-it.ru/"),
    STAGE("stage", "https://capper-api.gnt-it.ru/"),
    PROD("prod", "https://capper-api.gnt-it.ru/")
}
//enum class Env(val value: String, val url: String) {
//    DEV("dev", "https://api.cappers.space/"),
//    STAGE("stage", "https://api.cappers.space/"),
//    PROD("prod", "https://api.cappers.space/")
//}


internal val networkModule = module {


    factoryOf(::provideJson)
    factoryOf(::provideHttpClient)
    factory {
        val manager = get<EnvManager>()
        provideKtorHttpClient(
            get(),
            manager.env.url,
            get()
        )
    }

}

@OptIn(ExperimentalSerializationApi::class)
private fun provideJson(): Json {
    return Json {
        isLenient = true
        ignoreUnknownKeys = true
        explicitNulls = false
        encodeDefaults = true
        prettyPrint = true
    }
}

private const val TIME_OUT = 60000L

private fun provideHttpClient(
    json: Json,
    tokenStore: TokenStore,
    sessionManager: SessionManager
) = HttpClient {


    install(HttpRedirect) {
        checkHttpMethod = false
        allowHttpsDowngrade = false
    }

    defaultRequest {
        header("Content-Type", "application/json")
    }


    install(ContentNegotiation) {
        json(json)
    }


    httpLogger()


    HttpResponseValidator {


        handleResponseExceptionWithRequest { cause, request ->
            println(cause)
            println(request)
        }

        validateResponse { response ->

            val error =
                response.status != HttpStatusCode.OK
                        && response.status != HttpStatusCode.Created
                        && response.status != HttpStatusCode.NoContent
            if (response.status == HttpStatusCode.Unauthorized || response.status == HttpStatusCode.Forbidden) {
                sessionManager.exit()
                throw Failure.NotAuth

            }

            if (error) {
                // global error handle
                val body = response.bodyAsText()

                tryCatch {
                    val item = json.decodeFromString<ErrorMessage>(body)
                    if (!item.errors?.values?.first()?.first().isNullOrBlank()) {
                        val error = item.errors?.values?.map {
                            it.map {
                                it
                            }
                        }.orEmpty().flatten().joinToString("\n")
                        throw Failure.Message(error ?: "Попробуйте позже")
                    }
                    if (!item.message.isNullOrBlank()) {
                        throw Failure.Message(item.message ?: "Попробуйте позже")
                    }

                }


                throw Failure.Message("Попробуйте позже")


            }
        }
    }

    install(HttpTimeout) {
        connectTimeoutMillis = TIME_OUT
        socketTimeoutMillis = TIME_OUT
        requestTimeoutMillis = TIME_OUT
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println("json:\n ${message}")
            }

        }
        level = LogLevel.ALL
    }

    install(Auth) {
        bearer {
//            loadTokens {
//                BearerTokens(tokenStore.token, tokenStore.refresh)
//            }
//            refreshTokens {
//                BearerTokens(authManager.token.orEmpty(), authManager.refreshToken.orEmpty())
//            }
        }

    }


}

private fun provideKtorHttpClient(
    httpClient: HttpClient,
    baseUrl: String,
    tokenStore: TokenStore,
): Ktorfit {

    httpClient.sendPipeline.intercept(HttpSendPipeline.State) {
        if (tokenStore.token.isNotBlank()) {
            context.headers["Authorization"] = "Bearer ${tokenStore.token}"
        }
    }

    return ktorfit {
        baseUrl(baseUrl)
        httpClient(httpClient)
    }
}

private suspend fun tryCatch(body: suspend () -> Unit) {
    try {
        body()
    } catch (e: Exception) {
    }
}

//{"code":400,"message":"Ошибка валидации","errors":{"password":["Пароль должен содержать как минимум одну заглавную букву, одну строчную букву, одну цифру и один специальный символ"]}}

@Serializable
private class ErrorMessage(
    val message: String?,
    val errors: Map<String, List<String>>?
)