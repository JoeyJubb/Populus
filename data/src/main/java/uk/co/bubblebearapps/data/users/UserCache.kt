package uk.co.bubblebearapps.data.users

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import uk.co.bubblebearapps.data.users.stackexchange.SearchResultItem
import uk.co.bubblebearapps.domain.UserId

interface UserCache {

    fun putAll(users: List<SearchResultItem>): Completable

    fun get(userId: UserId): Maybe<SearchResultItem>

}
