package com.enriquepalmadev.appmarvel.di.feature.character


import com.enriquepalmadev.appmarvel.data.feature.character.datasource.CharacterService
import com.enriquepalmadev.appmarvel.data.feature.character.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.net.UnknownHostException
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    //private const val CHARACTER_DATABASE_NAME = "character_database"
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logginInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(logginInterceptor)
            .addInterceptor(NetworkErrorInterceptor())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit) : CharacterService{
        return retrofit.create(CharacterService::class.java)
    }

    /*
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CharacterDatabase::class.java, CHARACTER_DATABASE_NAME).build()


    @Singleton
    @Provides
    fun provideCharacterDao(db: CharacterDatabase) = db.getCharacterDao()

     */
}

class ApiKeyInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("apikey", Constants.API_KEY)
            .build()
        val newRequest = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }
}

class NetworkErrorInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        try {
            return chain.proceed(request)
        }catch (e: UnknownHostException){
            throw e
        }
    }

}