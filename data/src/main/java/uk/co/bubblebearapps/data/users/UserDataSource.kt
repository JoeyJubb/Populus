package uk.co.bubblebearapps.data.users

import io.reactivex.rxjava3.core.Single
import uk.co.bubblebearapps.data.users.stackexchange.SearchResponse
import uk.co.bubblebearapps.domain.UserId
import uk.co.bubblebearapps.domain.UserSortOrder

interface UserDataSource {

    fun searchByQuery(
            query: String,
            sortOrder: UserSortOrder,
            maxResults: Int
    ): Single<SearchResponse>

    fun getUserById(userId: UserId): Single<SearchResponse>

}
