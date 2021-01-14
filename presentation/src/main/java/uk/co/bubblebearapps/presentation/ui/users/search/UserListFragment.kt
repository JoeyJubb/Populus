package uk.co.bubblebearapps.presentation.ui.users.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import uk.co.bubblebearapps.domain.UserId
import uk.co.bubblebearapps.presentation.R
import uk.co.bubblebearapps.presentation.base.BaseFragment
import uk.co.bubblebearapps.presentation.databinding.UserListFragmentBinding
import uk.co.bubblebearapps.presentation.ext.addDivider
import uk.co.bubblebearapps.presentation.ext.observe
import uk.co.bubblebearapps.presentation.ext.onImeActionSearch
import uk.co.bubblebearapps.presentation.ui.ViewState

class UserListFragment : BaseFragment(), UserListNavActionHandler {

    private lateinit var viewModel: UserListViewModel
    private lateinit var binding: UserListFragmentBinding
    private lateinit var adapter: UserListRecyclerAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = UserListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.addDivider()

        binding.etUserQuery.onImeActionSearch { query ->
            viewModel.onQueryTextChange(query.toString())
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserListViewModel::class.java)

        adapter = UserListRecyclerAdapter(viewModel)
        binding.recyclerView.adapter = adapter

        observe(viewModel.getViewStateLiveData()) { onViewStateChanged(it) }
        observe(viewModel.getNavActionLiveData()) { navAction -> navAction.get()?.invoke(this) }
    }

    private fun onViewStateChanged(viewState: ViewState<UserListViewState>) {
        binding.progress.root.hide()

        binding.error.root.isVisible = false

        binding.idle.isVisible = false
        binding.empty.isVisible = false
        binding.recyclerView.isVisible = false

        when (viewState) {
            is ViewState.Ready -> {
                onReadyStateChanged(viewState.data)
            }
            is ViewState.Busy -> {
                binding.progress.root.show()
            }
            is ViewState.Error -> {

                binding.error.errorText.text = viewState.message

                viewState.action?.let { action ->
                    binding.error.errorBtn.text = action.label
                    binding.error.errorBtn.setOnClickListener { action.invokable() }
                    binding.error.errorBtn.isVisible = true
                } ?: kotlin.run { binding.error.errorBtn.isVisible = false }

                binding.error.root.isVisible = true
            }
            is ViewState.Alert -> showAlert(viewState)
        }
    }

    private fun onReadyStateChanged(userListViewState: UserListViewState) {

        when (userListViewState) {
            is UserListViewState.HasResults -> {
                adapter.items = userListViewState.results
                binding.recyclerView.isVisible = true
            }
            is UserListViewState.Empty -> {
                binding.empty.text =
                        getString(R.string.user_search_empty_message, userListViewState.query)
                binding.empty.isVisible = true

            }
            UserListViewState.Idle -> {
                binding.idle.isVisible = true
            }
        }
    }

    override fun goToDetailScreen(userId: UserId) {
        findNavController().navigate(
                UserListFragmentDirections.actionUserListToUserDetail(userId)
        )
    }
}
