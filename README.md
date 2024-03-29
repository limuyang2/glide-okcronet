[中文](https://github.com/limuyang2/glide-okcronet/blob/main/README_CN.md)
# glide-okcronet
A Glide network request library based on Cronet.

The network part is provided by [okcronet](https://github.com/limuyang2/okcronet).

# Purpose
* The official Cronet library provided by Glide does not enable HTTP3/QUIC support, which loses its advantages. Source code view [CronetEngineSingleton](https://github.com/bumptech/glide/blob/master/integration/cronet/src/main/java/com/bumptech/glide/integration/cronet/CronetEngineSingleton.java)
* And it does not support customization of CronetEngine. The purpose of this library is to provide complete support.

# Usage
First introduce the Cronet library:
```groovy
//Import Cronet, this method is recommended in mainland China. You can also use other Cronet versions.
implementation("org.chromium.net:cronet-api:119.6045.31")
implementation("org.chromium.net:cronet-common:119.6045.31")
implementation("org.chromium.net:cronet-embedded:119.6045.31")

// Google Play uses this method
implementation("com.google.android.gms:play-services-cronet:18.0.1")
```


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
