package io.github.limuyang2.glide_okcronet

import android.app.Application

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
    }
}