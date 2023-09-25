/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.dagger.injector

/**
 * Indicates that the implementing class can provide a [DaggerInjector].
 */
interface DaggerInjectorProvider {

    /**
     * Provides the [DaggerInjector]
     *
     * @return The DaggerInjector.
     */
    fun provideInjector(): DaggerInjector
}
