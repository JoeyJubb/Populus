package uk.co.bubblebearapps.data.users

import dagger.Reusable
import io.reactivex.rxjava3.core.Single
import uk.co.bubblebearapps.data.users.stackexchange.ResponseMapper
import uk.co.bubblebearapps.domain.User
import uk.co.bubblebearapps.domain.UserId
import uk.co.bubblebearapps.domain.UserQueryResult
import uk.co.bubblebearapps.domain.UserSortOrder
import uk.co.bubblebearapps.domain.ext.forEachCompletable
import uk.co.bubblebearapps.domain.ext.mapValues
import uk.co.bubblebearapps.domain.repository.UserRepository
import javax.inject.Inject

@Reusable
class UserRepositoryImpl @Inject constructor(
        private val userDataSource: UserDataSource,
        private val userCache: UserCache,
        private val responseMapper: ResponseMapper
) : UserRepository {


    override fun searchUsers(
            query: String,
            sortOrder: UserSortOrder,
            maxResults: Int
    ): Single<UserQueryResult> = userDataSource.searchByQuery(query, sortOrder, maxResults)
            .map { response -> response.users }
            .forEachCompletable { users -> userCache.putAll(users) }
            .mapValues { user -> responseMapper.summary(user) }
            .map { UserQueryResult(it) }


    override fun getUser(id: UserId): Single<User> =
            userCache.get(id)
                    .switchIfEmpty(
                            userDataSource.getUserById(id)
                                    .map { response -> response.users }
                                    .forEachCompletable { users -> userCache.putAll(users) }
                                    .map { users -> users.first() }
                    )
                    .map { user -> responseMapper.detail(user) }
}
