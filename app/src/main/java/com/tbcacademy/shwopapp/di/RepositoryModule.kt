package com.tbcacademy.shwopapp.di

import com.tbcacademy.shwopapp.repositories.DefaultAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun providesRepo(): DefaultAuthRepository {
        return DefaultAuthRepository()
    }


    @Provides
    @ViewModelScoped
    fun provideMainDispather() = Dispatchers.Main as CoroutineDispatcher
}