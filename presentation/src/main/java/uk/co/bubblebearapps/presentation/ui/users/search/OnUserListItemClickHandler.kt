package uk.co.bubblebearapps.presentation.ui.users.search

import uk.co.bubblebearapps.domain.UserId

interface OnUserListItemClickHandler {
    fun onItemClicked(id: UserId)

}