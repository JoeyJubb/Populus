package uk.co.bubblebearapps.presentation.ui.users.detail

import uk.co.bubblebearapps.domain.BadgeCounts
import uk.co.bubblebearapps.domain.UrlString
import uk.co.bubblebearapps.domain.UserId


sealed class UserDetailViewState {
    data class Loaded(val presentableUser: PresentableUser) : UserDetailViewState()

    object Closed : UserDetailViewState()
}

data class PresentableUser(
        val id: UserId,
        val userName: String,
        val photo: UrlString?,
        val badges: BadgeCounts,
        val location: String,
        val age: String,
        val reputation: Int,
        val creationDate: String
)

