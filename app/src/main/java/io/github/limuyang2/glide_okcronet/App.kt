package io.github.limuyang2.glide_okcronet

import android.app.Application
import org.chromium.net.CronetEngine

/**
 * @author 李沐阳
 * @date 2024/3/29
 * @description
 */
class App : Application() {


    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        lateinit var application: Application

        val cronetEngine = CronetEngine.Builder(application)
            .enableHttpCache(CronetEngine.Builder.HTTP_CACHE_DISK_NO_HTTP, 1048576)
            .enableHttp2(true)
            .enableQuic(true)
            .enableBrotli(true)
            .build()
    }
}