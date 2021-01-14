package uk.co.bubblebearapps.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import uk.co.bubblebearapps.presentation.ui.users.detail.UserDetailViewModel
import uk.co.bubblebearapps.presentation.ui.users.search.UserListViewModel

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UserListViewModel::class)
    fun userListViewModel(viewModel: UserListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserDetailViewModel::class)
    fun userDetailViewModel(viewModel: UserDetailViewModel): ViewModel

}