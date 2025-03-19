package com.example.pokecat.di

import com.example.pokecat.present.main.MainRepository
import com.example.pokecat.present.main.MainTask
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MainTaskModule {

    @Binds
    abstract fun bindMainTask(
        mainRepository: MainRepository
    ): MainTask
}