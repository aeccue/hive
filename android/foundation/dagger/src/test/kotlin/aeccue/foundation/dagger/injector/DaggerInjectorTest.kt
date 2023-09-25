/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.dagger.injector

import aeccue.foundation.dagger.component.BaseDaggerSubcomponent
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifyOrder
import org.amshove.kluent.`should be false`
import org.amshove.kluent.`should be true`
import org.junit.jupiter.api.Test

class DaggerInjectorTest {

    private val subcomponent: BaseDaggerSubcomponent<InjectionTarget> =
        BaseDaggerSubcomponentImplementation()

    @Test
    fun `injecting target should call inject once`() {
        @Suppress("UNCHECKED_CAST")
        val factory = BaseDaggerSubcomponentFactory(InjectionTarget::class.java, subcomponent)
                as BaseDaggerSubcomponent.Factory<Any>

        val target: InjectionTarget = spyk()
        val injector = DaggerInjector(setOf(factory))

        injector.inject(target).`should be true`()

        verify(exactly = 1) {
            target.inject()
        }
    }

    @Test
    fun `injecting InjectAware target should call preInject, inject and postInject once`() {
        @Suppress("UNCHECKED_CAST")
        val factory = BaseDaggerSubcomponentFactory(InjectAwareTarget::class.java, subcomponent)
                as BaseDaggerSubcomponent.Factory<Any>

        val target: InjectAwareTarget = spyk()
        val injector = DaggerInjector(setOf(factory))

        injector.inject(target).`should be true`()

        verify(exactly = 1) {
            target.preInject()
            target.inject()
            target.postInject()
        }
    }

    @Test
    fun `injecting InjectAware target should call preInject, inject and postInject in the correct order`() {
        @Suppress("UNCHECKED_CAST")
        val factory = BaseDaggerSubcomponentFactory(InjectAwareTarget::class.java, subcomponent)
                as BaseDaggerSubcomponent.Factory<Any>

        val target: InjectAwareTarget = spyk()
        val injector = DaggerInjector(setOf(factory))

        injector.inject(target).`should be true`()

        verifyOrder {
            target.preInject()
            target.inject()
            target.postInject()
        }
    }

    @Test
    fun `trying to inject a target with no factory mapping should return false`() {
        val target = InjectionTarget()
        val injector = DaggerInjector(setOf())
        injector.inject(target).`should be false`()
    }
}

private class BaseDaggerSubcomponentImplementation : BaseDaggerSubcomponent<InjectionTarget> {

    override fun inject(target: InjectionTarget) {
        target.inject()
    }
}

private class BaseDaggerSubcomponentFactory<T : InjectionTarget>(
    override val key: Class<T>,
    private val subcomponent: BaseDaggerSubcomponent<InjectionTarget>
) : BaseDaggerSubcomponent.Factory<T>() {

    override fun create(target: T) = subcomponent
}
