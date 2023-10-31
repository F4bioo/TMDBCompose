package com.fappslab.libraries.arch.testing.rules

import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@VisibleForTesting
class RemoteTestRule : TestWatcher() {

    private val mockWebServer = MockWebServer()

    override fun finished(description: Description) {
        super.finished(description)
        mockWebServer.shutdown()
    }

    fun mockWebServerResponse(body: String, code: Int) {
        mockWebServer.enqueue(MockResponse().setBody(body).setResponseCode(code))
    }

    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
    inline fun <reified Service> createTestService(): Service = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(Service::class.java)
}
