package uk.co.bubblebearapps.data.users

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import uk.co.bubblebearapps.data.users.stackexchange.SearchResultItem
import uk.co.bubblebearapps.domain.UserId
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserCacheImpl @Inject constructor() : UserCache {

    private val scheduler: Scheduler = Schedulers.single()
    private val userCache: TreeMap<UserId, SearchResultItem> = TreeMap()


    override fun putAll(users: List<SearchResultItem>): Completable =
            Observable.fromIterable(users)
                    .flatMapCompletable { searchResultItem ->
                        Completable.fromAction {
                            userCache[searchResultItem.id] = searchResultItem
                        }
                    }
                    .subscribeOn(scheduler)

    override fun get(userId: UserId): Maybe<SearchResultItem> =
            Maybe.fromCallable<SearchResultItem> { userCache[userId] }
                    .subscribeOn(scheduler)
}