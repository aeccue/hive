/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.dagger.injector

/**
 * A helper interface to add basic dependency injection using [DaggerInjection] to any component.
 */
interface DaggerInjected {

    /**
     * Injects the dependency into this component using [DaggerInjection].
     */
    fun inject(): Boolean = DaggerInjection.inject(this)
}
