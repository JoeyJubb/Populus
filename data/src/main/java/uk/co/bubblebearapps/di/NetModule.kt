package uk.co.bubblebearapps.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoSet
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.ZonedDateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.co.bubblebearapps.data.users.stackexchange.UsersApi
import uk.co.bubblebearapps.data.users.stackexchange.interceptors.ApiKeyInterceptor
import uk.co.bubblebearapps.data.users.stackexchange.interceptors.ErrorWrapperInterceptor
import uk.co.bubblebearapps.data.users.stackexchange.json.ZonedDateTimeDeserializer
import javax.inject.Singleton

@Module(includes = [BindsNetModule::class])
class NetModule {

    @Provides
    @Reusable
    fun okHttpClient(
            apiKeyInterceptor: ApiKeyInterceptor,
            errorWrapperInterceptor: ErrorWrapperInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(apiKeyInterceptor)
                .addInterceptor(errorWrapperInterceptor)
                .build()
    }

    @Provides
    @Reusable
    fun gson(
            zonedDateTimeDeserializer: ZonedDateTimeDeserializer
    ): Gson =
            GsonBuilder()
                    .registerTypeAdapter(ZonedDateTime::class.java, zonedDateTimeDeserializer)
                    .create()

    @Provides
    @Reusable
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory =
            GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun retrofit(
            okHttpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.stackexchange.com/2.2/")
                .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(gsonConverterFactory)
                .build()
    }

    @Provides
    @Reusable
    fun usersApi(retrofit: Retrofit): UsersApi {
        return retrofit.create(UsersApi::class.java)
    }
}

@Module
interface BindsNetModule {

    @Binds
    @IntoSet
    fun apiKeyInterceptor(apiKeyInterceptor: ApiKeyInterceptor): Interceptor

}
