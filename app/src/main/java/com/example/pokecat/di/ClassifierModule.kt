package com.example.pokecat.di

import android.content.Context
import com.example.pokecat.machineLearning.FileUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.nio.MappedByteBuffer

@Module
@InstallIn(SingletonComponent::class)
object ClassifierModule {
    @Provides
    fun provideTfLiteModel(@ApplicationContext context: Context): MappedByteBuffer {
        return FileUtils.loadModelFile(context)
    }

    @Provides
    fun provideLabels(@ApplicationContext context: Context): List<String>{
        return FileUtils.loadLabels(context)
    }
}