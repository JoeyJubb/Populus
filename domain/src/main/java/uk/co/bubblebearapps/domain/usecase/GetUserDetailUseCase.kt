package uk.co.bubblebearapps.domain.usecase

import io.reactivex.rxjava3.core.Single
import uk.co.bubblebearapps.domain.User
import uk.co.bubblebearapps.domain.UserId
import uk.co.bubblebearapps.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(userId: UserId): Single<User> =
            userRepository.getUser(
                    id = userId
            )
}
