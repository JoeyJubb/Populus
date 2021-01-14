package uk.co.bubblebearapps.di

import dagger.Binds
import dagger.Module
import uk.co.bubblebearapps.data.users.UserCache
import uk.co.bubblebearapps.data.users.UserCacheImpl
import uk.co.bubblebearapps.data.users.UserDataSource
import uk.co.bubblebearapps.data.users.UserRepositoryImpl
import uk.co.bubblebearapps.data.users.stackexchange.StackExchangeUserDataSource
import uk.co.bubblebearapps.domain.repository.UserRepository

@Module(
        includes = [
            NetModule::class
        ]
)
interface DataModule {

    @Binds
    fun userRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    fun userDataSource(impl: StackExchangeUserDataSource): UserDataSource

    @Binds
    fun userCache(impl: UserCacheImpl): UserCache

}
