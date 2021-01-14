package uk.co.bubblebearapps.presentation.ui.users.search

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uk.co.bubblebearapps.domain.UserId
import uk.co.bubblebearapps.domain.UserSummary
import uk.co.bubblebearapps.presentation.R
import uk.co.bubblebearapps.presentation.databinding.UserListFragmentItemBinding

class UserListRecyclerAdapter(private val onUserListItemClickHandler: OnUserListItemClickHandler) :
        RecyclerView.Adapter<UserListViewHolder>() {

    var items: List<UserSummary> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder =
            UserListViewHolder(
                    binding = UserListFragmentItemBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                    ),
                    onUserListItemClickHandler = onUserListItemClickHandler
            )

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}

class UserListViewHolder(
        private val binding: UserListFragmentItemBinding,
        onUserListItemClickHandler: OnUserListItemClickHandler
) :
        RecyclerView.ViewHolder(binding.root) {

    private var id: UserId? = null
    private val resources: Resources

    init {
        binding.root.setOnClickListener {
            id?.let {
                onUserListItemClickHandler.onItemClicked(it)
            }
        }
        resources = binding.root.context.resources
    }

    fun bind(userSummary: UserSummary) {
        id = userSummary.id
        binding.textUserName.text = userSummary.userName
        binding.textReputation.text =
                resources.getString(R.string.reputation_x, userSummary.reputation)
    }
}

