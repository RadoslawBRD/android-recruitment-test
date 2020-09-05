package dog.snow.androidrecruittest.repository.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dog.snow.androidrecruittest.repository.model.RawUser
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url


interface UserService {

    @GET("users/{id}")
    fun getUsers(
        @Path("id") id: String
    ): Deferred<RawUser>

    companion object{
        operator fun invoke(): UserService{
            val requestInterceptor = Interceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserService::class.java)
        }
    }
}