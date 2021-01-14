package uk.co.bubblebearapps.domain

import org.threeten.bp.ZonedDateTime

typealias UserId = String
typealias UrlString = String

data class BadgeCounts(
        val bronze: Int,
        val silver: Int,
        val gold: Int
)

data class User(
        val id: UserId,
        val userName: String,
        val photo: UrlString?,
        val badges: BadgeCounts,
        val location: String?,
        val age: Int?,
        val reputation: Int,
        val creationDate: ZonedDateTime
)

data class UserSummary(
        val id: UserId,
        val userName: String,
        val reputation: Int
)

data class UserQueryResult(
        val results: List<UserSummary>
)

enum class UserSortOrder {
    NAME
}