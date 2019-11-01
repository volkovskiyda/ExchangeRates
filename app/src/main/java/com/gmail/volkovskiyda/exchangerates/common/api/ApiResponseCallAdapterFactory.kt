package com.gmail.volkovskiyda.exchangerates.common.api

import com.gmail.volkovskiyda.exchangerates.R
import com.gmail.volkovskiyda.exchangerates.common.ResourceProvider
import okhttp3.Request
import retrofit2.*
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiResponseCallAdapterFactory
@Inject constructor(
    private val resourceProvider: ResourceProvider
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val responseType = getParameterUpperBound(0, returnType as ParameterizedType)
        if (getRawType(responseType) != ApiResponse::class.java) return null
        val resultType = getParameterUpperBound(0, responseType as ParameterizedType)
        return ApiResponseCallAdapter<Any>(
            resourceProvider,
            resultType
        )
    }
}

class ApiResponseCallAdapter<T>(
    private val resourceProvider: ResourceProvider, private val responseType: Type
) : CallAdapter<T, Call<ApiResponse<T>>> {

    override fun adapt(call: Call<T>): Call<ApiResponse<T>> =
        ApiResponseCall(resourceProvider, call)

    override fun responseType(): Type = responseType
}

class ApiResponseCall<T>(
    private val resourceProvider: ResourceProvider,
    private val delegate: Call<T>
) : Call<ApiResponse<T>> {

    override fun execute(): Response<ApiResponse<T>> = try {
        convertResponse(delegate.execute())
    } catch (e: IOException) {
        convertThrowable(e)
    }

    override fun enqueue(callback: Callback<ApiResponse<T>>) =
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                callback.onResponse(this@ApiResponseCall, convertResponse(response))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onResponse(this@ApiResponseCall, convertThrowable(t))
            }
        })

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun clone(): Call<ApiResponse<T>> =
        ApiResponseCall(
            resourceProvider,
            delegate.clone()
        )

    override fun request(): Request = delegate.request()

    private fun convertResponse(response: Response<T>): Response<ApiResponse<T>> {
        val apiResponse: ApiResponse<T> = if (response.isSuccessful) {
            ApiResponse.Success(data = response.body()!!)
        } else {
            ApiResponse.Error(HttpException(response).toApiException())
        }
        return Response.success(apiResponse)
    }

    private fun convertThrowable(t: Throwable): Response<ApiResponse<T>> {
        val apiException = when (t) {
            is HttpException -> t.toApiException()
            is UnknownHostException ->
                ApiException(t, resourceProvider.getString(R.string.network_error))
            is SocketTimeoutException ->
                ApiException(t, resourceProvider.getString(R.string.timeout_error))
            else -> ApiException(t, resourceProvider.getString(R.string.unknown_error))
        }
        val apiResponse =
            ApiResponse.Error<T>(apiException)
        return Response.success(apiResponse)
    }

    private fun HttpException.toApiException(): ApiException =
        ApiException(this, response()?.errorBody()?.string().orEmpty())
}