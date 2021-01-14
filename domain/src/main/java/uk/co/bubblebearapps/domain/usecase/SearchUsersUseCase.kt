package uk.co.bubblebearapps.domain.usecase

import io.reactivex.rxjava3.core.Single
import uk.co.bubblebearapps.domain.UserQueryResult
import uk.co.bubblebearapps.domain.UserSortOrder
import uk.co.bubblebearapps.domain.repository.UserRepository
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(query: String): Single<UserQueryResult> =
            userRepository.searchUsers(
                    query = query,
                    sortOrder = UserSortOrder.NAME,
                    maxResults = 20
            )
}
