/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.dagger.component

import aeccue.foundation.dagger.injector.*
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifyOrder
import org.amshove.kluent.`should be true`
import org.junit.jupiter.api.Test

class BaseDaggerComponentTest {

    private val component: BaseDaggerComponent<InjectionTarget> =
        BaseDaggerComponentImplementation()

    @Test
    fun `InjectAware target should have preInject, inject and postInject called only once`() {
        val target: InjectAwareTarget = spyk()
        component.init(target)

        verify(exactly = 1) {
            target.preInject()
            target.inject()
            target.postInject()
        }
    }

    @Test
    fun `InjectAware target should have preInject, inject and postInject called in the correct order`() {
        val target: InjectAwareTarget = spyk()
        component.init(target)

        verifyOrder {
            target.preInject()
            target.inject()
            target.postInject()
        }
    }

    @Test
    fun `no name`() {
        val injector = DaggerInjector(setOf())
        val target = DaggerInjectorProviderTarget(injector)
        component.init(target)

        DaggerInjection.unregister(injector).`should be true`()
    }
}

private class BaseDaggerComponentImplementation : BaseDaggerComponent<InjectionTarget>() {

    override fun inject(target: InjectionTarget) {
        target.inject()
    }
}
