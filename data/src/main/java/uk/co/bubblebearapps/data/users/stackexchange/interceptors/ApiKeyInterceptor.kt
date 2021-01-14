package uk.co.bubblebearapps.data.users.stackexchange.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/*
 * Does nothing, but here's where the API key would go if I had registered one!
 */
class ApiKeyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
            chain.request()
    )
}
