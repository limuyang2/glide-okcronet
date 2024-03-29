# glide-okcronet
A Glide network request library based on Cronet.

The network part is provided by [okcronet](https://github.com/limuyang2/okcronet).

# Purpose
* The official Cronet library provided by Glide does not enable HTTP3/QUIC support, which loses its advantages. Source code view [CronetEngineSingleton](https://github.com/bumptech/glide/blob/master/integration/cronet/src/main/java/com/bumptech/glide/integration/cronet/CronetEngineSingleton.java)
* And it does not support customization of CronetEngine. The purpose of this library is to provide complete support.

# Usage
## Method 1: Manual registration, using a custom CronetEngine (recommended)
It is recommended to use this method first, because you can reuse the global CronetEngine of the project.
### Import
```kotlin
implementation("io.github.limuyang2:glide-okcronet:1.0.1")
```

### Registration
Register in your project AppGlideModule.
```kotlin
@GlideModule
class DemoGlideModule : AppGlideModule() {
    
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        /*
        Register the component using your own global CronetEngine.
         */
        registry.replace(
            GlideUrl::class.java, InputStream::class.java, OkCronetUrlLoader.Factory(App.cronetEngine)
        )
    }
}
```

## Method 2: Automatic registration (not recommended)
This method will automatically generate a CronetEngine, which will not be reused with the one in the project.
### Import
```kotlin
implementation("io.github.limuyang2:glide-okcronet-auto:1.0.0")
```
No manual registration is required.