package com.klinovvlad.task3klinov.di.modules

import android.content.Context
import com.klinovvlad.task3klinov.db.UserDao
import com.klinovvlad.task3klinov.db.UserDatabase
import com.klinovvlad.task3klinov.model.UserDatabaseRepository
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(context: Context): UserDatabase = UserDatabase.getInstance(context)

    @Provides
    fun provideUserDao(userDatabase: UserDatabase): UserDao = userDatabase.mainDao()

    @Provides
    fun provideUserDatabaseRepository(userDao: UserDao): UserDatabaseRepository =
        UserDatabaseRepository(userDao)

}