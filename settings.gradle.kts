rootProject.name = "Quitte"

pluginManagement {
    repositories {
        maven("https://jitpack.io")
        gradlePluginPortal()
    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
            // Workaround until the next stable release
                "com.zyxist.chainsaw" -> useModule("com.github.zyxist:chainsaw:028e7265f0")
            }
        }
    }
}