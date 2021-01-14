package uk.co.bubblebearapps.presentation.ui.users.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import uk.co.bubblebearapps.presentation.R
import uk.co.bubblebearapps.presentation.base.BaseFragment
import uk.co.bubblebearapps.presentation.databinding.UserDetailFragmentBinding
import uk.co.bubblebearapps.presentation.ext.loadUrl
import uk.co.bubblebearapps.presentation.ext.observe
import uk.co.bubblebearapps.presentation.ui.ViewState

class UserDetailFragment : BaseFragment() {

    private val navArgs: UserDetailFragmentArgs by navArgs()

    private lateinit var viewModel: UserDetailViewModel
    private lateinit var binding: UserDetailFragmentBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = UserDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserDetailViewModel::class.java)

        with(viewModel) {
            viewModel.loadUser(navArgs.userId)

            observe(getViewStateLiveData()) { onViewStateChanged(it) }
        }
    }

    private fun onViewStateChanged(viewState: ViewState<UserDetailViewState>) {
        when (viewState) {
            is ViewState.Ready -> {
                when (viewState.data) {
                    is UserDetailViewState.Loaded -> {
                        bindUser(viewState.data.presentableUser)
                    }
                    is UserDetailViewState.Closed -> close()
                }
            }
            is ViewState.Busy -> {
                // no op
            }
            is ViewState.Error -> showError(viewState)
            is ViewState.Alert -> showAlert(viewState)
        }
    }

    private fun bindUser(presentableUser: PresentableUser) {
        with(binding.content) {
            textUserName.text = presentableUser.userName
            textAge.text = presentableUser.age

            textBronze.text = getString(R.string.bronze_x, presentableUser.badges.bronze)

            textSilver.text = getString(R.string.silver_x, presentableUser.badges.silver)

            textGold.text = getString(R.string.gold_x, presentableUser.badges.gold)

            textCreationDate.text = presentableUser.creationDate
            textReputation.text = presentableUser.reputation.toString()
            textLocation.text = presentableUser.location

            presentableUser.photo?.let {
                imageUser.loadUrl(it)
            } ?: kotlin.run { imageUser.isVisible = false }

        }
    }

    private fun close() {
        findNavController().popBackStack()
    }
}