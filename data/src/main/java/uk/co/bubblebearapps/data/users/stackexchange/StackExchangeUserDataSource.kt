package uk.co.bubblebearapps.data.users.stackexchange

import dagger.Reusable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import uk.co.bubblebearapps.data.users.UserDataSource
import uk.co.bubblebearapps.domain.UserId
import uk.co.bubblebearapps.domain.UserSortOrder
import javax.inject.Inject

@Reusable
class StackExchangeUserDataSource @Inject constructor(
        private val usersApi: UsersApi
) : UserDataSource {

    override fun searchByQuery(
            query: String,
            sortOrder: UserSortOrder,
            maxResults: Int
    ): Single<SearchResponse> =
            usersApi.searchByQuery(
                    query = query,
                    maxResults = maxResults,
                    sortOrder = sortOrder.toApiString()
            ).subscribeOn(Schedulers.io())

    override fun getUserById(userId: UserId): Single<SearchResponse> =
            usersApi.getUserById(userId = userId)
                .subscribeOn(Schedulers.io())
}

private fun UserSortOrder.toApiString(): String = when (this) {
    UserSortOrder.NAME -> "name"
}
