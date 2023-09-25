plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = AndroidConfig.COMPILE_SDK
    buildToolsVersion = AndroidConfig.BUILD_TOOLS_VERSION

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK
        targetSdk = AndroidConfig.TARGET_SDK
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        allWarningsAsErrors = true
        jvmTarget = "11"
        freeCompilerArgs = freeCompilerArgs.plus("-Xopt-in=kotlin.RequiresOptIn")
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Android.COMPOSE
    }
}

dependencies {
    api(project(":foundation:dagger"))

    api(Dependencies.Android.CORE)
    api(Dependencies.Android.APPCOMPAT)
    api(Dependencies.Android.COROUTINES)
    api(Dependencies.Android.Compose.MATERIAL)
    api(Dependencies.Android.Compose.ICONS)
    api(Dependencies.Android.Compose.NAVIGATION)
    api(Dependencies.Android.Compose.VIEWMODEL)

    kapt(Dependencies.Dagger.COMPILER)
}
