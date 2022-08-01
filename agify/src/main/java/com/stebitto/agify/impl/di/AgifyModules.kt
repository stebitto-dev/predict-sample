package com.stebitto.agify.impl.di

import com.stebitto.agify.api.IAgifyRepository
import com.stebitto.agify.impl.source.AgifyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [AgifyInternalModule::class])
@InstallIn(SingletonComponent::class)
class AgifyModule

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AgifyInternalModule {

    @Binds
    abstract fun provideAgifyRepository(impl: AgifyRepository): IAgifyRepository
}