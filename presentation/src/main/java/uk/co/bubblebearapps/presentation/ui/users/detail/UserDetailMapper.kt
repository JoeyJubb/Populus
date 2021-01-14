package uk.co.bubblebearapps.presentation.ui.users.detail

import uk.co.bubblebearapps.domain.User
import uk.co.bubblebearapps.presentation.R
import uk.co.bubblebearapps.presentation.util.DateTimeFormatter
import uk.co.bubblebearapps.presentation.util.ResourcesWrapper
import javax.inject.Inject

class UserDetailMapper @Inject constructor(
        private val resourcesWrapper: ResourcesWrapper,
        private val dateTimeFormatter: DateTimeFormatter
) :
        Function1<User, PresentableUser> {

    override operator fun invoke(user: User) = PresentableUser(
            id = user.id,
            userName = user.userName,
            photo = user.photo,
            age = user.age?.toString() ?: resourcesWrapper.getString(R.string.age_unknown),
            location = user.location.orEmpty()
                    .ifBlank { resourcesWrapper.getString(R.string.location_unknown) },
            reputation = user.reputation,
            badges = user.badges,
            creationDate = dateTimeFormatter.formatMediumDate(user.creationDate)
    )
}
