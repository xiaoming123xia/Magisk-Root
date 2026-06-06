plugins {
    alias(libs.plugins.android.application)
}

setupCommon()

android {
    namespace = "com.mobai.magisk"
    enableKotlin = false

    buildTypes {
        release {
            isShrinkResources = false
        }
    }
}
