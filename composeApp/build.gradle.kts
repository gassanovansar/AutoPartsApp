plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.libres)
    alias(libs.plugins.ksp)
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.ktorfit)
}

//configure<de.jensklingenberg.ktorfit.gradle.KtorfitGradleConfiguration> {
//    version = libs.versions.ktorfit.asProvider().get()
//}

kotlin {
    androidTarget {
//        compilations.all {
//            kotlinOptions {
//                jvmTarget = "17"
//            }
//        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }



    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)

            /**
             * Mvi
             */
            implementation(libs.orbit.core)

            /**
             * Voyager
             */
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.bottomSheetNavigator)
            implementation(libs.voyager.tabNavigator)
            implementation(libs.voyager.screenModel)

            /**
             * Koin
             */
            implementation(libs.koin.core)

            /**
             * Ktor & Ktorfit
             */
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.jensklingenberg.ktorfit)
            /**
             * Multiplatform Settings
             */
            implementation(libs.multiplatformSettings)


            /**
             * Resources
             */
            implementation(libs.libres)

            /**
             * Coil
             */
            implementation(libs.coil3)
            implementation(libs.coil3.ktor)

            /**
             * Camera
             */
            implementation(libs.camera.camera2)
            implementation(libs.camera.lifecycle)
            implementation(libs.camera.view)

            /**
             * Permission
             */
            implementation("com.guolindev.permissionx:permissionx:1.8.0")

            implementation(libs.kotlinx.datetime)

        }

        androidMain.dependencies {
            implementation("com.google.accompanist:accompanist-systemuicontroller:0.36.0")
            implementation(libs.androidx.activityCompose)

            /**
             * Koin
             */
            implementation(libs.koin.android)

            implementation(libs.square.okhttp)
            implementation(libs.chucker)
            implementation(libs.ktor.client.okhttp)
        }

        iosMain.dependencies {
        }

    }
}
dependencies {
    val ktorfitVersion = libs.versions.ktorfit.asProvider().get()
    add("kspCommonMainMetadata", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
    add("kspAndroid", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
    add("kspIosX64", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
    add("kspIosArm64", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
    add("kspIosSimulatorArm64", "de.jensklingenberg.ktorfit:ktorfit-ksp:$ktorfitVersion")
}

android {
    namespace = "com.ansar.autoparts"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
        targetSdk = 35

        applicationId = "ccom.ansar.autoparts"
        versionCode = 4
        versionName = "0.0.4"
//        setProperty("archivesBaseName", "autoparts-${versionName}")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
}
libres {
    generatedClassName = "AppResource" // "Res" by default
    generateNamedArguments = true // false by default
    baseLocaleLanguageCode = "ru" // "en" by default
    camelCaseNamesForAppleFramework = true // false by default
}

