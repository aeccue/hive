package aeccue.hub.dagger

import aeccue.hub.led.dagger.LedViewModelProvidersDaggerModule
import aeccue.hub.profile.dagger.ProfileViewModelProvidersDaggerModule
import dagger.Module

@Module(
    includes = [
        LedViewModelProvidersDaggerModule::class,
        ProfileViewModelProvidersDaggerModule::class
    ]
)
interface HubViewModelProviderDaggerModule
