/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.dagger.component

import aeccue.foundation.dagger.injector.DaggerInjection
import aeccue.foundation.dagger.injector.DaggerInjector
import aeccue.foundation.dagger.scope.ComponentScope
import aeccue.foundation.dagger.scope.SubcomponentScope
import dagger.Binds
import dagger.BindsInstance
import dagger.multibindings.IntoSet

/**
 * This class can be extended to add the Dagger subcomponent into the [DaggerInjection] process. Tne
 * [FactoryMapping] must be extended and added as a module to the main component that has already
 * been registered to the DaggerInjection. Subclasses must also have the Dagger Subcomponent
 * annotation.
 *
 * @param [T] The type of target to inject with this subcomponent.
 */
interface BaseDaggerSubcomponent<in T> {

    /**
     * This class can be extended to create a Factory for the [BaseDaggerSubcomponent] and the
     * corresponding FactoryMapping. The [create] method has already been provided, and the [key]
     * must be provided for the FactoryMapping.
     *
     * @param [T] The type of the injection target of the subcomponent to create.
     */
    interface Factory<T> {

        /**
         * The key for the FactoryMapping. Must be implemented.
         */
        val key: Class<out T>

        /**
         * This method will be implemented automatically by Dagger to create an instance of the
         * [BaseDaggerSubcomponent]. The instance of the target will also be bound so that it can be
         * injected within the subcomponent.
         *
         * @param [target] The target to be injected by the subcomponent.
         * @return The instance of the subcomponent created.
         */
        fun create(@BindsInstance target: T): BaseDaggerSubcomponent<T>
    }

    /**
     * This class provides a mapping of a [Factory] to its key to the [DaggerInjector], which can
     * then be used to automatically inject subcomponents through the [DaggerInjection]. The mapping
     * is scoped with [ComponentScope].
     *
     * @param [T] The type of the injection target of the subcomponent created by the factory.
     * @param [F] The type of factory to create subcomponents.
     */
    interface ComponentFactoryMapping<T, in F : Factory<T>> {

        @ComponentScope
        @Binds
        @IntoSet
        fun mapping(factory: F): Factory<Any>
    }

    /**
     * This class provides a mapping of a [Factory] to its key to the [DaggerInjector], which can
     * then be used to automatically inject subcomponents through the [DaggerInjection]. The mapping
     * is scoped with [SubcomponentScope]
     *
     * @param [T] The type of the injection target of the subcomponent created by the factory.
     * @param [F] The type of factory to create subcomponents.
     */
    interface SubcomponentFactoryMapping<T, in F : Factory<T>> {

        @SubcomponentScope
        @Binds
        @IntoSet
        fun mapping(factory: F): Factory<Any>
    }

    /**
     * Injects the target of this subcomponent with all the required dependencies created by Dagger.
     *
     * @param [target] The target to inject.
     */
    fun inject(target: T)
}
