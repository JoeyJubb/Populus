package uk.co.bubblebearapps.presentation.ui.users.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.co.bubblebearapps.domain.UserId
import uk.co.bubblebearapps.domain.UserQueryResult
import uk.co.bubblebearapps.domain.usecase.SearchUsersUseCase
import uk.co.bubblebearapps.getOrAwaitValue
import uk.co.bubblebearapps.presentation.R
import uk.co.bubblebearapps.presentation.ui.ViewState
import uk.co.bubblebearapps.presentation.util.ErrorMessageFactory
import uk.co.bubblebearapps.presentation.util.ResourcesWrapper

class UserListViewModelTest {

    private val searchUsersUseCase: SearchUsersUseCase = mock()
    private val errorMessageFactory: ErrorMessageFactory = mock()
    private val resourcesWrapper: ResourcesWrapper = mock()

    lateinit var viewmodel: UserListViewModel

    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewmodel = UserListViewModel(searchUsersUseCase, errorMessageFactory, resourcesWrapper)
    }

    @Test
    fun `onItemClicked will emit a navigation action that takes the user to the detail screen`() {

        // given the view model is notified of an item click
        val navActionHandler = mock<UserListNavActionHandler>()
        val userId: UserId = "user_id"

        // when the view model is notified of an item click
        viewmodel.onItemClicked(userId)

        // then the view model shall emit a navigation action
        val navigationAction = viewmodel.getNavActionLiveData().getOrAwaitValue()

        // and when the navigation action is invoked
        navigationAction.get()?.invoke(navActionHandler)

        // then the detail screen function is invoked
        verify(navActionHandler).goToDetailScreen(userId)
    }

    @Test
    fun `onQueryTextChange with empty string sets the ready state to idle`() {
        // given the query is empty
        val query = ""

        // when onQueryTextChange is called
        viewmodel.onQueryTextChange(query)

        // then the view state should be idle
        val viewState = viewmodel.getViewStateLiveData().getOrAwaitValue()
        require(viewState is ViewState.Ready)
        require(viewState.data is UserListViewState.Idle)
    }

    @Test
    fun `onQueryTextChange when no results sets the ready state to empty`() {
        // given the query is not empty
        // and search result is empty
        val query = "query"
        whenever(searchUsersUseCase.invoke(query)).thenReturn(Single.just(UserQueryResult(emptyList())))

        // when onQueryTextChange is called
        viewmodel.onQueryTextChange(query)

        // then the view state should be empty
        val viewState = viewmodel.getViewStateLiveData().getOrAwaitValue()
        require(viewState is ViewState.Ready)
        require(viewState.data is UserListViewState.Empty)
    }

    @Test
    fun `onQueryTextChange when error in loading sets the ready state to error with a retry action`() {
        // given the query is not empty
        // and search result throws an error the first time, and an empty list the second time
        val query = "query"
        val throwable = mock<Throwable>()
        whenever(searchUsersUseCase.invoke(query)).thenReturn(
                Single.error(throwable),
                Single.just(UserQueryResult(emptyList())),
        )

        whenever(errorMessageFactory.getUserMessage(throwable)).thenReturn("Error")
        whenever(resourcesWrapper.getString(R.string.retry)).thenReturn("Retry")
        
        // when onQueryTextChange is called
        viewmodel.onQueryTextChange(query)

        // then the view state should be an error
        val errorState = viewmodel.getViewStateLiveData().getOrAwaitValue()
        require(errorState is ViewState.Error)

        // when the error action is invoked
        requireNotNull(errorState.action).invokable.invoke()

        // then the view state should be empty (based on the emissions defined above)
        val emptyState = viewmodel.getViewStateLiveData().getOrAwaitValue()
        require(emptyState is ViewState.Ready)
        require(emptyState.data is UserListViewState.Empty)
    }

    @Test
    fun `onQueryTextChange when results found sets the ready state to loaded`() {
        // given the query is not empty
        // and search result has 3 items
        val query = "query"
        whenever(searchUsersUseCase.invoke(query)).thenReturn(
                Single.just(UserQueryResult(listOf(
                        mock(),
                        mock(),
                        mock()
                ))),
        )

        // when onQueryTextChange is called
        viewmodel.onQueryTextChange(query)

        // then the view state should be Loaded
        val viewState = viewmodel.getViewStateLiveData().getOrAwaitValue()
        require(viewState is ViewState.Ready)

        val data = viewState.data
        require(data is UserListViewState.HasResults)
        assertEquals(3, data.results.size)
    }
}