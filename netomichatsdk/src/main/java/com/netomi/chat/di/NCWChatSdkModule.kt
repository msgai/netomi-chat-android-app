package com.netomi.chat.di

import com.netomi.chat.data.NCWChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NCWChatSdkModule {

    @Provides
    @Singleton
    fun provideNCWChatRepository(): NCWChatRepository{
        return NCWChatRepository()
    }
}