package com.tbcacademy.shwopapp.di



import com.tbcacademy.shwopapp.repositories.AuthRepository
import com.tbcacademy.shwopapp.repositories.DefaultAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped


@Module
@InstallIn(ActivityComponent::class)
object AuthModule {

    @ActivityScoped
    @Provides
    fun provideAuthRepository() = DefaultAuthRepository() as AuthRepository
}