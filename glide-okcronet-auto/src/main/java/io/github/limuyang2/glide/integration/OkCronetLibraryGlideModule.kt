package io.github.limuyang2.glide.integration

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.LibraryGlideModule
import org.chromium.net.CronetEngine
import java.io.File
import java.io.InputStream

/**
 * @author 李沐阳
 * @date 2024/3/28
 * @description
 */
@GlideModule
class OkCronetLibraryGlideModule : LibraryGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {

        val httpCacheDir = File(
            context.applicationContext.externalCacheDir ?: context.applicationContext.cacheDir,
            "glide_okcronet"
        )

        if (!httpCacheDir.exists()) {
            httpCacheDir.mkdir()
        }

        val cronetEngine = CronetEngine.Builder(context.applicationContext)
            .setStoragePath(httpCacheDir.absolutePath)
            .enableHttpCache(CronetEngine.Builder.HTTP_CACHE_DISK_NO_HTTP, 1048576)
            .enableHttp2(true)
            .enableQuic(true)
            .enableBrotli(true)
            .build()

        registry.replace(
            GlideUrl::class.java, InputStream::class.java, OkCronetUrlLoader.Factory(cronetEngine)
        )
    }
}