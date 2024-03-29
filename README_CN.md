# glide-okcronet
一个基于 Cronet 的 Glide 网络请求库。其中网络部分由 [okcronet](https://github.com/limuyang2/okcronet) 提供。

# 目的
`Glide` 官方提供的 `Cronet` 库中，未开启 `HTTP3/QUIC` 的支持，丧失了其优势。[源码查看 CronetEngineSingleton](https://github.com/bumptech/glide/blob/master/integration/cronet/src/main/java/com/bumptech/glide/integration/cronet/CronetEngineSingleton.java)。

并且不支持对 `CronetEngine` 的定制。本库目的用于提供完整的支持。

# 使用
## 方式一，手动注册，使用自定义 `CronetEngine`（推荐此方式）
推荐优先使用此方式，因为可以使用项目全局的 `CronetEngine`。
### 引入
```kotlin
implementation("io.github.limuyang2:glide-okcronet:1.0.1")
```

### 注册
在你的项目 `AppGlideModule` 中注册。
```kotlin
@GlideModule
class DemoGlideModule : AppGlideModule() {
    
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
```

## 方式二，自动注册（不优先考虑）
此方式会自动生成一个 `CronetEngine`，与项目中的不复用。
### 引入
```kotlin
implementation("io.github.limuyang2:glide-okcronet-auto:1.0.0")
```
无需手动注册。


