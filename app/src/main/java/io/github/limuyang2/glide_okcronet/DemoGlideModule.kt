package io.github.limuyang2.glide_okcronet

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import io.github.limuyang2.glide.integration.OkCronetUrlLoader
import java.io.InputStream

/**
 * @author 李沐阳
 * @date 2024/3/29
 * @description
 */
@GlideModule
class DemoGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setLogLevel(Log.DEBUG)
    }


    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        /*
        注册组件，使用你自己的全局 CronetEngine.

        Register the component using your own global CronetEngine.
         */
        registry.replace(
            GlideUrl::class.java, InputStream::class.java, OkCronetUrlLoader.Factory(App.cronetEngine)
        )
    }

}