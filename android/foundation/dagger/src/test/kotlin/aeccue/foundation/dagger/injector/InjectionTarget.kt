/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.dagger.injector

open class InjectionTarget {

    fun inject() {}
}

class InjectAwareTarget : InjectionTarget(), InjectAware

class DaggerInjectorProviderTarget(private val injector: DaggerInjector) : InjectionTarget(),
    DaggerInjectorProvider {

    override fun provideInjector() = injector
}
