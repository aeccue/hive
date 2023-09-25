/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

object Dependencies {

    object Android {

        const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.Android.APPCOMPAT}"
        const val CORE = "androidx.core:core-ktx:${Versions.Android.CORE}"
        const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLIN}"
        const val MATERIAL = "com.google.android.material:material:${Versions.Android.MATERIAL}"

        object Compose {

            const val MATERIAL = "androidx.compose.material:material:${Versions.Android.COMPOSE}"
            const val ICONS =
                "androidx.compose.material:material-icons-extended:${Versions.Android.COMPOSE}"
            const val NAVIGATION =
                "androidx.navigation:navigation-compose:${Versions.Android.NAVIGATION}"
            const val VIEWMODEL =
                "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Android.COMPOSE_VIEWMODEL}"
        }

        object Room {

            const val COMPILER = "androidx.room:room-compiler:${Versions.Android.ROOM}"
            const val CORE = "androidx.room:room-ktx:${Versions.Android.ROOM}"
        }
    }

    object Dagger {

        const val COMPILER = "com.google.dagger:dagger-compiler:${Versions.DAGGER}"
        const val CORE = "com.google.dagger:dagger:${Versions.DAGGER}"
    }
}
