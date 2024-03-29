package io.github.limuyang2.glide.integration

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoader.LoadData
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import okcronet.Call
import okcronet.CronetClient
import org.chromium.net.CronetEngine
import java.io.InputStream


/** A simple model loader for fetching media over http/https using okcronet. */
class OkCronetUrlLoader(private val client: Call.Factory) : ModelLoader<GlideUrl, InputStream> {

    override fun buildLoadData(
        model: GlideUrl,
        width: Int,
        height: Int,
        options: Options
    ): LoadData<InputStream> {
        return LoadData(model, OkCronetStreamFetcher(client, model))
    }

    override fun handles(model: GlideUrl): Boolean = true


    /** Constructor for a new Factory that runs requests using a static singleton client.  */
    class Factory(
        private val client: Call.Factory
    ) : ModelLoaderFactory<GlideUrl, InputStream> {

        constructor(cronetEngine: CronetEngine) : this(CronetClient.Builder(cronetEngine).build())

        /**
         * Constructor for a new Factory that runs requests using given client.
         */
        override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<GlideUrl, InputStream> {
            return OkCronetUrlLoader(client)
        }

        override fun teardown() {
            // Do nothing, this instance doesn't own the client.
        }
    }

}