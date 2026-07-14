plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.example.talkiesocial.domain"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
}
