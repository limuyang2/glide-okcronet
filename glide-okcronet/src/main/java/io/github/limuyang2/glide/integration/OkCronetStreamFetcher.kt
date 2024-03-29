package io.github.limuyang2.glide.integration

import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.HttpException
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.util.ContentLengthInputStream
import okcronet.Call
import okcronet.http.Request
import okcronet.http.Response
import okcronet.http.ResponseBody
import org.chromium.net.UrlRequest
import java.io.IOException
import java.io.InputStream
import kotlin.concurrent.Volatile


/** Fetches an {@link InputStream} using the okcronet library. */
internal class OkCronetStreamFetcher(private val client: Call.Factory, private val url: GlideUrl) :
    DataFetcher<InputStream>, okcronet.Callback {

    private var callback: DataFetcher.DataCallback<in InputStream>? = null

    private var stream: InputStream? = null
    private var responseBody: ResponseBody? = null

    @Volatile
    private var call: Call? = null

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        val requestBuilder: Request.Builder = Request.Builder()
            .url(url.toStringUrl())

        when(priority) {
            Priority.IMMEDIATE -> requestBuilder.priority(UrlRequest.Builder.REQUEST_PRIORITY_HIGHEST)
            Priority.HIGH -> requestBuilder.priority(UrlRequest.Builder.REQUEST_PRIORITY_HIGHEST)
            Priority.NORMAL -> requestBuilder.priority(UrlRequest.Builder.REQUEST_PRIORITY_MEDIUM)
            Priority.LOW -> requestBuilder.priority(UrlRequest.Builder.REQUEST_PRIORITY_LOW)
        }

        for ((key, value) in url.headers) {
            requestBuilder.addHeader(key, value)
        }
        val request: Request = requestBuilder.build()
        this.callback = callback

        this.call = client.newCall(request).apply {
            enqueue(this@OkCronetStreamFetcher)
        }
    }

    override fun cleanup() {
        try {
            stream?.close()
        } catch (_: IOException) {
            // Ignored
        }
        responseBody?.close()
        callback = null
    }

    override fun cancel() {
        call?.cancel()
    }

    override fun getDataClass(): Class<InputStream> = InputStream::class.java


    override fun getDataSource(): DataSource = DataSource.REMOTE



    // okcronet.Callback


    override fun onFailure(call: Call, e: IOException) {
        callback?.onLoadFailed(e)
    }

    override fun onResponse(call: Call, response: Response) {
        response.body.also {
            responseBody = it

            if (it == null) {
                callback?.onLoadFailed(NullPointerException("response body is null"))
                return
            }

            if (response.isSuccessful) {
                val contentLength: Long = it.contentLength()
                stream = ContentLengthInputStream.obtain(it.byteStream(), contentLength)
                callback?.onDataReady(stream)
            } else {
                callback?.onLoadFailed(HttpException(response.urlResponseInfo.httpStatusText, response.code))
            }
        }
    }
}


