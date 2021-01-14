package uk.co.bubblebearapps.presentation.ui.users.search

import uk.co.bubblebearapps.domain.UserId

interface UserListNavActionHandler {

    fun goToDetailScreen(userId: UserId)

}
