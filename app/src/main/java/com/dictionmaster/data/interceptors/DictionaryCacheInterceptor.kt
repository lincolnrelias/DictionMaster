package com.dictionmaster.data.interceptors

import com.dictionmaster.utils.ApiCallManager
import okhttp3.Interceptor
import okhttp3.Response

class DictionaryCacheInterceptor(private val apiCallManager: ApiCallManager) : Interceptor {


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