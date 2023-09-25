/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.dagger.injector

/**
 * DaggerInjection will be used to attempt to inject any targets with the [DaggerInjector]
 * registered to it. This object serves as a main entry point to dependency injection, so that the
 * target itself does not know how it is injected.
 */
object DaggerInjection {

    private val injectors = mutableListOf<DaggerInjector>()

    /**
     *
     * Registers the [DaggerInjector], to be used for injection. Registration will only succeed if
     * the DaggerInjector is not already registered.
     *
     * @param [injector] The injector to register.
     * @return True if injector has been successfully registered, false otherwise.
     */
    @Synchronized
    fun register(injector: DaggerInjector): Boolean {
        if (injectors.contains(injector)) return false

        injectors.add(injector)
        return true
    }

    /**
     * Attempts to unregisters a previously registered [DaggerInjector].
     *
     * @param [injector] The injector to unregister.
     * @return True if DaggerInjector is removed, or false if it was not previously registered.
     */
    @Synchronized
    fun unregister(injector: DaggerInjector): Boolean = injectors.remove(injector)

    /**
     * Attempts to inject the target. All registered injectors are iterated through in an attempt to
     * inject the target.
     *
     * @param [target] The target to inject.
     * @return True if the target is successfully injected, false otherwise.
     */
    @Synchronized
    fun inject(target: Any): Boolean {
        for (injector in injectors) {
            if (injector.inject(target)) return true
        }

        return false
    }

    /**
     * Unregisters all injectors currently registered.
     */
    @Synchronized
    internal fun clearInjectors() {
        injectors.clear()
    }
}
