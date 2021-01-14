package uk.co.bubblebearapps.data.users.stackexchange

import uk.co.bubblebearapps.domain.BadgeCounts
import javax.inject.Inject

class BadgeMapper @Inject constructor() {

    fun map(apiBadge: ApiBadge) = BadgeCounts(
            bronze = apiBadge.bronze,
            silver = apiBadge.silver,
            gold = apiBadge.gold
    )
}
