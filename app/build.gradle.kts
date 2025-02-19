plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
    id("jacoco")
    alias(libs.plugins.kotlin.parcelize)  // Add this line
}

android {
    namespace = "com.example.getupdated"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.getupdated"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Add NY Times API configuration
        buildConfigField("String", "NY_TIMES_BASE_URL", "\"http://api.nytimes.com/\"")
        buildConfigField("String", "NY_TIMES_API_KEY", "\"R8M6M3f3YdcAkPkknyqSEY68A6jRKTfZ\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            enableUnitTestCoverage= true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true  // Enable BuildConfig generation
    }
    lintOptions {
        isAbortOnError= false  // Set to true if you want the build to fail on lint error
        xmlReport =true      // For CI servers
        htmlReport =true     // For human-readable reports
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Lifecycle & ViewModel
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Retrofit, OkHttp & Gson/Moshi (for network requests)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Coroutine support for retrofit calls
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Logging Interceptor (OkHttp)
    implementation(libs.logging.interceptor)
    // Dagger Hilt
    implementation (libs.hilt.dep )
    kapt(libs.hilt.complier)
    implementation(libs.androidx.hilt.navigation.compose)


    // coil
    implementation(libs.accompanist.coil)
    implementation(libs.coil.compose)
    testImplementation(libs.mockk)

    testImplementation(libs.mockk.agent.jvm)
    testImplementation(libs.kotlinx.coroutines.test)

    // browser
   implementation(libs.androidx.browser)



}
// JaCoCo configuration for code coverage reports
jacoco {
    toolVersion = "0.8.8"
}

tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest", "createDebugCoverageReport")

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val fileFilter = listOf(
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*"
    )

    val debugTree = fileTree("${project.buildDir}/intermediates/classes/debug") {
        exclude(fileFilter)
    }

    val mainSrc = "${project.projectDir}/src/main/java"

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(fileTree(project.buildDir) {
        include(
            "jacoco/testDebugUnitTest.exec",
            "outputs/code-coverage/connected/*coverage.ec"
        )
    })}