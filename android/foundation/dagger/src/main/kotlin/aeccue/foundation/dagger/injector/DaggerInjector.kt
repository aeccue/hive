/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.dagger.injector

import aeccue.foundation.dagger.component.BaseDaggerSubcomponent
import javax.inject.Inject

/**
 * An injector that contains a set of [BaseDaggerSubcomponent.Factory] mappings and will attempt to
 * inject a target using its mappings. Only one can exist per Dagger component.
 *
 * @param [factories] The factories to use for injection, placed into a map using its class key.
 */
class DaggerInjector @Inject constructor(factories: Set<@JvmSuppressWildcards BaseDaggerSubcomponent.Factory<Any>>) {

    private val factoryMappings = factories.associateBy { it.key }

    /**
     * Attempts to inject the target by finding its factory mapping using its class. If the target
     * is [InjectAware], its preInject and postInject will be called accordingly.
     *
     * @param [target] The target to attempt to inject.
     * @return True if successfully injected, false otherwise.
     */
    fun inject(target: Any): Boolean {
        var factory = factoryMappings[target::class.java]
        if (factory == null) {
            for ((k, f) in factoryMappings) {
                if (target::class.java.isAssignableFrom(k)) {
                    factory = f
                    break
                }
            }
        }

        if (factory == null) return false

        (target as? InjectAware)?.preInject()
        factory.create(target).inject(target)

        (target as? DaggerInjectorProvider)?.provideInjector()?.let {
            DaggerInjection.register(it)
        }

        (target as? InjectAware)?.postInject()
        return true
    }
}
