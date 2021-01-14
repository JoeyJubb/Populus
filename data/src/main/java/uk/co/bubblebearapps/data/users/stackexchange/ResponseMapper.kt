package uk.co.bubblebearapps.data.users.stackexchange

import uk.co.bubblebearapps.domain.User
import uk.co.bubblebearapps.domain.UserSummary
import javax.inject.Inject

class ResponseMapper @Inject constructor(private val badgeMapper: BadgeMapper) {

    fun summary(result: SearchResultItem) = UserSummary(
            id = result.id,
            userName = result.name,
            reputation = result.reputation
    )

    fun detail(result: SearchResultItem) = User(
            id = result.id,
            userName = result.name,
            age = result.age,
            location = result.location,
            photo = result.photoUrl,
            reputation = result.reputation,
            badges = badgeMapper.map(result.badgeCounts),
            creationDate = result.created
    )
}
