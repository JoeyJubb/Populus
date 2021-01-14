package uk.co.bubblebearapps.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import uk.co.bubblebearapps.presentation.ui.users.detail.UserDetailFragment
import uk.co.bubblebearapps.presentation.ui.users.search.UserListFragment

@Module
interface FragmentBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector
    fun userListFragment(): UserListFragment

    @FragmentScope
    @ContributesAndroidInjector
    fun userDetailFragment(): UserDetailFragment
}
