plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {

    implementation(project(":domain") )

    implementation(libs.androidx.junit.ktx)
    ksp(libs.room.compiler)

    implementation(libs.room)
    testImplementation(libs.room.test)
    implementation(libs.room.pagging)
    implementation(libs.koin)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity.compose)
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation ("com.squareup.okhttp3:okhttp-urlconnection:3.1.0")



    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    androidTestImplementation(libs.room.test)
    androidTestImplementation(libs.robolectric)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")

    implementation(platform(libs.firebase.bom))
    coreLibraryDesugaring(libs.desugar)

    // Add the dependency for the Realtime Database library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation(libs.firebase)

}