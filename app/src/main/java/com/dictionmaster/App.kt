package com.dictionmaster

import android.app.Application
import com.dictionmaster.search.ApiCallManager
import com.dictionmaster.search.DictionaryApiService
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.File

class App : Application() {
    private val apiCallManager by lazy { ApiCallManager(this) }

    override fun onCreate() {
        super.onCreate()
        initOkHttpWithCaching()
    }

    private fun initOkHttpWithCaching() {
        val cacheSize = (5 * 1024 * 1024).toLong() // 5 MB
        val cacheDirectory = File(cacheDir, "http-cache")
        val cache = Cache(cacheDirectory, cacheSize)

        val okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(CacheInterceptor(apiCallManager))
            .build()

        // Set the OkHttpClient instance globally for Retrofit
        DictionaryApiService.setOkHttpClient(okHttpClient)
    }
    class CacheInterceptor(private val apiCallManager: ApiCallManager) : Interceptor {


        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()

            // Check if it's allowed to make an API call
            if (apiCallManager.canMakeApiCall()) {
                // If allowed, proceed with the network request
                return chain.proceed(request)
            }

            // If not allowed, force the use of cache
            val cacheRequest = request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=${Int.MAX_VALUE}")
                .build()

            return chain.proceed(cacheRequest)
        }
    }
}