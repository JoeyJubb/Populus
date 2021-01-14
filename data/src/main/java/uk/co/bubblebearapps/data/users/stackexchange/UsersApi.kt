package uk.co.bubblebearapps.data.users.stackexchange

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uk.co.bubblebearapps.domain.UserId

interface UsersApi {

    @Suppress("SpellCheckingInspection")
    @GET("users?site=stackoverflow")
    fun searchByQuery(
            @Query("inname") query: String,
            @Query("max") maxResults: Int,
            @Query("sort") sortOrder: String
    ): Single<SearchResponse>

    @GET("users/{user_id}")
    fun getUserById(@Path("user_id") userId: UserId): Single<SearchResponse>

}