package uk.co.bubblebearapps.domain.ext

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

fun <T, R> Single<List<T>>.mapValues(transform: (T) -> R): Single<List<R>> =
        flattenAsFlowable { it }
                .map(transform)
                .toList()

/**
 * @param selector if true, the action will be run on the item
 * @param action completable to subscribe to for each item
 */
fun <T> Single<T>.forEachCompletable(
        selector: (T) -> Boolean = { true },
        action: (T) -> Completable
): Single<T> =
        flatMap { item ->
            Single.just(item)
                    .filter(selector)
                    .flatMapCompletable(action)
                    .andThen(Single.just(item))
        }
