package uk.co.bubblebearapps.presentation.ui.users.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber
import uk.co.bubblebearapps.domain.UserId
import uk.co.bubblebearapps.domain.usecase.GetUserDetailUseCase
import uk.co.bubblebearapps.presentation.R
import uk.co.bubblebearapps.presentation.base.BaseViewModel
import uk.co.bubblebearapps.presentation.ui.Action
import uk.co.bubblebearapps.presentation.ui.ViewState
import uk.co.bubblebearapps.presentation.util.ErrorMessageFactory
import uk.co.bubblebearapps.presentation.util.ResourcesWrapper
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(
        private val userDetailUseCase: GetUserDetailUseCase,
        private val userDetailMapper: UserDetailMapper,
        private val errorMessageFactory: ErrorMessageFactory,
        private val resourcesWrapper: ResourcesWrapper
) : BaseViewModel() {

    private val _viewStateLiveData = MutableLiveData<ViewState<UserDetailViewState>>(ViewState.Busy)

    fun getViewStateLiveData(): LiveData<ViewState<UserDetailViewState>> = _viewStateLiveData

    fun loadUser(userId: UserId) {
        async {
            userDetailUseCase(userId)
                    .map(userDetailMapper)
                    .map { ViewState.Ready(UserDetailViewState.Loaded(it)) }
                    .subscribe(
                            { readyState ->
                                _viewStateLiveData.postValue(readyState)
                            },
                            {
                                /* error */
                                Timber.e(it)
                                _viewStateLiveData.postValue(ViewState.Alert(
                                        message = errorMessageFactory.getUserMessage(it),
                                        positiveAction = Action(
                                                label = resourcesWrapper.getString(R.string.retry),
                                                invokable = { loadUser(userId) }
                                        ),
                                        negativeAction = Action(
                                                label = resourcesWrapper.getString(R.string.go_back),
                                                invokable = {
                                                    _viewStateLiveData.postValue(
                                                            ViewState.Ready(
                                                                    UserDetailViewState.Closed
                                                            )
                                                    )
                                                }
                                        )
                                )
                                )
                            }
                    )
        }
    }
}
