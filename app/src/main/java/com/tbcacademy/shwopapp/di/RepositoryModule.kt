package com.tbcacademy.shwopapp.di

import com.tbcacademy.shwopapp.repositories.AuthRepository
import com.tbcacademy.shwopapp.repositories.MainRepositoryImpl
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
    fun providesRepo(): AuthRepository {
        return AuthRepository()
    }

    @Provides
    @ViewModelScoped
    fun providesMainRepo(): MainRepositoryImpl {
        return MainRepositoryImpl()
    }

    @Provides
    @ViewModelScoped
    fun provideMainDispather() = Dispatchers.Main as CoroutineDispatcher
}