package uk.co.bubblebearapps.domain.repository

import io.reactivex.rxjava3.core.Single
import uk.co.bubblebearapps.domain.User
import uk.co.bubblebearapps.domain.UserId
import uk.co.bubblebearapps.domain.UserQueryResult
import uk.co.bubblebearapps.domain.UserSortOrder

interface UserRepository {

    fun searchUsers(
            query: String,
            sortOrder: UserSortOrder = UserSortOrder.NAME,
            maxResults: Int
    ): Single<UserQueryResult>

    fun getUser(id: UserId): Single<User>

}
