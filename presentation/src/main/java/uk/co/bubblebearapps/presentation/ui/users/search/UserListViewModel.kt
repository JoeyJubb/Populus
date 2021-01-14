package uk.co.bubblebearapps.presentation.ui.users.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber
import uk.co.bubblebearapps.domain.UserId
import uk.co.bubblebearapps.domain.UserSummary
import uk.co.bubblebearapps.domain.usecase.SearchUsersUseCase
import uk.co.bubblebearapps.domain.util.Consumable
import uk.co.bubblebearapps.presentation.R
import uk.co.bubblebearapps.presentation.base.BaseViewModel
import uk.co.bubblebearapps.presentation.ui.Action
import uk.co.bubblebearapps.presentation.ui.ViewState
import uk.co.bubblebearapps.presentation.util.ErrorMessageFactory
import uk.co.bubblebearapps.presentation.util.ResourcesWrapper
import javax.inject.Inject

class UserListViewModel @Inject constructor(
        private val searchUsersUseCase: SearchUsersUseCase,
        private val errorMessageFactory: ErrorMessageFactory,
        private val resourcesWrapper: ResourcesWrapper
) : BaseViewModel(), OnUserListItemClickHandler {

    private val _viewStateLiveData =
            MutableLiveData<ViewState<UserListViewState>>(ViewState.Ready(UserListViewState.Idle))
    private val _navigationEventsLiveData = MutableLiveData<Consumable<UserListNavAction>>()

    fun getViewStateLiveData(): LiveData<ViewState<UserListViewState>> = _viewStateLiveData
    fun getNavActionLiveData(): LiveData<Consumable<UserListNavAction>> = _navigationEventsLiveData

    fun onQueryTextChange(query: String) {
        clear()
        if (query.isNotBlank()) {
            async {
                searchUsersUseCase.invoke(query)
                        .doOnSubscribe { _viewStateLiveData.postValue(ViewState.Busy) }
                        .subscribe(
                                {/* success */
                                    _viewStateLiveData.postValue(toReadyState(query, it.results)
                                    )
                                },
                                {/* error */
                                    Timber.e(it)
                                    _viewStateLiveData.postValue(
                                            ViewState.Error(
                                                    message = errorMessageFactory.getUserMessage(it),
                                                    action = Action(
                                                            label = resourcesWrapper.getString(R.string.retry),
                                                            invokable = { onQueryTextChange(query) }
                                                    )
                                            )
                                    )
                                }
                        )
            }
        } else {
            _viewStateLiveData.postValue(ViewState.Ready(UserListViewState.Idle))
        }
    }

    private fun toReadyState(query: String, results: List<UserSummary>) =
            ViewState.Ready(if (results.isEmpty()) {
                UserListViewState.Empty(query)
            } else {
                UserListViewState.HasResults(results)
            })

    override fun onItemClicked(id: UserId) {
        _navigationEventsLiveData.postValue(Consumable { actionHandler ->
            actionHandler.goToDetailScreen(id)
        })
    }
}

typealias UserListNavAction = (UserListNavActionHandler) -> Unit
