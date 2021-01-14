package uk.co.bubblebearapps.presentation.ui.users.search

import uk.co.bubblebearapps.domain.UserSummary

sealed class UserListViewState {

    /**
     * Indicates no search has been made yet
     */
    object Idle : UserListViewState()

    /**
     * Indicates a search was made but it yielded zero results
     */
    data class Empty(val query: String) : UserListViewState()

    /**
     * Indicates a search was made and results were found
     */
    data class HasResults(val results: List<UserSummary>) : UserListViewState()

}
