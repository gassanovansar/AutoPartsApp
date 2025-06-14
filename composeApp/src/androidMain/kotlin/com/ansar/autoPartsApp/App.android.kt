package com.ansar.autoPartsApp

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import com.ansar.autoPartsApp.di.KoinInjector
import com.ansar.autoPartsApp.di.platformViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

class AndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInjector.init(listOf(platformViewModel))
        KoinInjector.koinApp.androidContext(this)
        activityInject()
    }

    private fun activityInject() {
        this.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {

            override fun onActivityResumed(activity: Activity) {
                KoinInjector.koin.loadModules(listOf(module {
                    single { activity }
                }))
            }

            override fun onActivityPaused(activity: Activity) {
                KoinInjector.koin.unloadModules(listOf(module {
                    single { activity }
                }))
            }


            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

            override fun onActivityStarted(activity: Activity) {}

            override fun onActivityStopped(activity: Activity) {}

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityDestroyed(activity: Activity) {}


        })
    }
}


class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Remember a SystemUiController
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()

            DisposableEffect(systemUiController, useDarkIcons) {
                // Update all of the system bar colors to be transparent, and use
                // dark icons if we're in light theme
                systemUiController.setSystemBarsColor(
                    color = Color(0xFF263052),
//                    darkIcons = useDarkIcons,
                    isNavigationBarContrastEnforced = true
                )

                // setStatusBarColor() and setNavigationBarColor() also exist

                onDispose {}
            }
            App()
        }
    }
}
