package dog.snow.androidrecruittest.repository.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dog.snow.androidrecruittest.repository.model.RawPhoto
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.logging.Logger


const val limits = "5";

interface PhotoService {


    @GET("photos")
    fun getPhotos(
        //@Query("_limit") limit: String
    ): Deferred<List<RawPhoto>>

    companion object{
        operator fun invoke(): PhotoService{
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("_limit", limits)

                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
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
                    .create(PhotoService::class.java)
            }
        }
    }
