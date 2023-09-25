/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.android.app

import aeccue.foundation.dagger.injector.DaggerInjector
import aeccue.foundation.dagger.injector.DaggerInjectorProvider
import android.app.Application
import javax.inject.Inject

abstract class InjectedApplication : Application(), DaggerInjectorProvider {

    @Inject
    internal lateinit var injector: DaggerInjector

    override fun provideInjector() = injector

    override fun onCreate() {
        inject()
        super.onCreate()
    }

    protected abstract fun inject()
}
