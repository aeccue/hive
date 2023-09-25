/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

package aeccue.foundation.dagger.injector

import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.`should be false`
import org.amshove.kluent.`should be true`
import org.junit.jupiter.api.Test

class DaggerInjectionTest {

    private val target = InjectionTarget()

    private val trueInjector: DaggerInjector = mockk {
        every { inject(target) } returns true
    }

    private val falseInjector: DaggerInjector = mockk {
        every { inject(target) } returns false
    }

    @Test
    fun `registering an injector for the first time should return true`() {
        DaggerInjection.register(trueInjector).`should be true`()
        DaggerInjection.register(falseInjector).`should be true`()
        DaggerInjection.clearInjectors()
    }

    @Test
    fun `registering an injector two times should return false on the second time`() {
        DaggerInjection.register(trueInjector).`should be true`()
        DaggerInjection.register(trueInjector).`should be false`()

        DaggerInjection.register(falseInjector).`should be true`()
        DaggerInjection.register(falseInjector).`should be false`()
        DaggerInjection.clearInjectors()
    }

    @Test
    fun `unregistering a registered injector should return true`() {
        DaggerInjection.register(trueInjector).`should be true`()
        DaggerInjection.register(falseInjector).`should be true`()

        DaggerInjection.unregister(trueInjector).`should be true`()
        DaggerInjection.unregister(falseInjector).`should be true`()
    }

    @Test
    fun `unregistering an injector that has not been registered should return false`() {
        DaggerInjection.unregister(trueInjector).`should be false`()
        DaggerInjection.unregister(falseInjector).`should be false`()
    }

    @Test
    fun `attempting to inject a target with its injector registered should return true`() {
        DaggerInjection.register(falseInjector)
        DaggerInjection.register(trueInjector)

        DaggerInjection.inject(target).`should be true`()
        DaggerInjection.clearInjectors()
    }

    @Test
    fun `attempting to inject a target without its injector registered should return false`() {
        DaggerInjection.register(falseInjector)

        DaggerInjection.inject(target).`should be false`()
        DaggerInjection.clearInjectors()
    }
}
