plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.example.talkiesocial.domain"
    compileSdk = 37

    defaultConfig {
        minSdk = 24
    }
}

dependencies {
    implementation(libs.javax.inject)
    implementation(libs.androidx.core.ktx)
}
