package uk.co.bubblebearapps.data.users.stackexchange

import com.google.gson.annotations.SerializedName
import org.threeten.bp.ZonedDateTime
import uk.co.bubblebearapps.domain.UrlString
import uk.co.bubblebearapps.domain.UserId

data class SearchResponse(
        @SerializedName("items") val users: List<SearchResultItem>,
        @SerializedName("has_more") val hasMore: Boolean,
        @SerializedName("quota_max") val quotaMax: Int,
        @SerializedName("quota_remaining") val quotaRemaining: Int
)

/**
 * @see <a href="https://api.stackexchange.com/docs/types/user">Api Doc</a>
 */
data class SearchResultItem(
        @SerializedName("age") val age: Int?,
        @SerializedName("badge_counts") val badgeCounts: ApiBadge,
        @SerializedName("creation_date") val created: ZonedDateTime,
        @SerializedName("display_name") val name: String,
        @SerializedName("location") val location: String?,
        @SerializedName("profile_image") val photoUrl: UrlString?,
        @SerializedName("reputation") val reputation: Int,
        @SerializedName("user_id") val id: UserId
)

data class ApiBadge(
        @SerializedName("bronze") val bronze: Int,
        @SerializedName("silver") val silver: Int,
        @SerializedName("gold") val gold: Int
)
