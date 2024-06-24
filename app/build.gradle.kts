plugins {
	alias(libs.plugins.androidApplication)
	alias(libs.plugins.jetbrainsKotlinAndroid)
	id("kotlin-kapt")
	id("com.google.dagger.hilt.android")
}

android {
	namespace = "com.nandaiqbalh.pokedex"
	compileSdk = 34

	defaultConfig {
		applicationId = "com.nandaiqbalh.pokedex"
		minSdk = 24
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
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
		sourceCompatibility = JavaVersion.VERSION_1_8
		targetCompatibility = JavaVersion.VERSION_1_8
	}
	kotlinOptions {
		jvmTarget = "1.8"
	}
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.1"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
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
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.ui.test.junit4)
	debugImplementation(libs.androidx.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)

	implementation("androidx.compose.material:material:1.6.8")
	implementation("androidx.compose.material3:material3:1.2.0-rc01")
	implementation("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha04")
	implementation("androidx.navigation:navigation-compose:1.0.0-alpha09")
	implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-alpha05")

	// Retrofit
	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	implementation("com.squareup.retrofit2:converter-gson:2.9.0")
	implementation("com.squareup.okhttp3:okhttp:4.9.0")
	implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

	// Timber
	implementation("com.jakewharton.timber:timber:4.7.1")

	// Coroutines
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3")

	// Coroutine Lifecycle Scopes
	implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")

	// Coil
	implementation("io.coil-kt:coil:1.1.1")
	implementation("com.google.accompanist:accompanist-coil:0.7.0")

	// Dagger - Hilt
	implementation("com.google.dagger:hilt-android:2.39.1")
	kapt("com.google.dagger:hilt-compiler:2.39.1")
	androidTestImplementation("com.google.dagger:hilt-android-testing:2.39.1")
	kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.39.1")
	implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
	implementation("androidx.hilt:hilt-navigation-compose:1.0.0-alpha01")

	implementation("androidx.palette:palette-ktx:1.0.0")
}