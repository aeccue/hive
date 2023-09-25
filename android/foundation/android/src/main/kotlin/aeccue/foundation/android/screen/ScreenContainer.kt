/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.android.screen

import aeccue.foundation.android.app.InjectedActivity
import aeccue.foundation.android.module.ScreenContainerModule
import aeccue.foundation.android.navigation.BottomNavigation
import aeccue.foundation.android.navigation.BottomNavigationScreen
import aeccue.foundation.android.navigation.NavHostCanvas
import aeccue.foundation.dagger.injector.DaggerInjector
import aeccue.foundation.dagger.injector.DaggerInjectorProvider
import aeccue.foundation.dagger.injector.InjectAware
import aeccue.foundation.dagger.module.ScreenContainerDaggerModule
import aeccue.foundation.util.Initializable
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import javax.inject.Inject
import javax.inject.Named

abstract class ScreenContainer : InjectedActivity(), DaggerInjectorProvider, InjectAware {

    @Inject
    protected lateinit var modules: Set<@JvmSuppressWildcards ScreenContainerModule>

    @Named(ScreenContainerDaggerModule.SCREENS)
    @Inject
    protected lateinit var screens: Set<@JvmSuppressWildcards NavHostScreen>

    @Inject
    protected lateinit var bottomNavScreens: List<@JvmSuppressWildcards BottomNavigationScreen>

    @Inject
    protected lateinit var viewModelProvider: ViewModelProvider

    @Inject
    internal lateinit var injector: DaggerInjector

    override fun postInject() {
        for (screen in screens) {
            (screen as? Initializable)?.init()
        }

        modules.forEach { module ->
            module.init(this)
        }
    }

    override fun provideInjector() = injector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Theme {
                ScreenContainer(rememberNavController())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        modules.forEach { module ->
            module.destroy(this)
        }
    }

    @Composable
    protected abstract fun Theme(content: @Composable () -> Unit)

    @Composable
    protected open fun ScreenContainer(navController: NavHostController) {
        Scaffold(
            topBar = {
                TopBar(navController)
            },
            bottomBar = {
                BottomBar(navController)
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = bottomNavScreens[0].route,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                for (screen in screens) {
                    screen.display(NavHostCanvas(navController, this))
                }
            }
        }
    }

    @Composable
    protected open fun TopBar(navController: NavController) {
    }

    @Composable
    protected open fun BottomBar(navController: NavController) {
        BottomNavigation(
            navController = navController,
            screens = bottomNavScreens
        )
    }
}
