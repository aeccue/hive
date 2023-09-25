/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.android.app

import aeccue.foundation.android.module.ProcessModule
import javax.inject.Inject

abstract class BaseApplication : InjectedApplication() {

    @Inject
    protected lateinit var modules: Set<@JvmSuppressWildcards ProcessModule>

    override fun onCreate() {
        super.onCreate()
        modules.forEach { module ->
            module.init(this)
        }
    }
}
