package com.stebitto.agify.impl.di

import com.stebitto.agify.api.IAgifyRepository
import com.stebitto.agify.impl.source.AgifyRepository
import com.stebitto.agify.impl.source.remote.AgifyRemoteSource
import com.stebitto.agify.impl.source.remote.IAgifyRemoteSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [AgifyRepositoryModule::class, AgifyRemoteSourceModule::class])
@InstallIn(SingletonComponent::class)
class AgifyModule

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AgifyRepositoryModule {

    @Binds
    abstract fun provideAgifyRepository(impl: AgifyRepository): IAgifyRepository
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AgifyRemoteSourceModule {

    @Binds
    abstract fun provideAgifyRemoteSource(impl: AgifyRemoteSource): IAgifyRemoteSource
}