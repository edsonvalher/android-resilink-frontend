import java.util.Properties

plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

// Leer propiedades desde local.properties
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { stream ->
        localProperties.load(stream)
    }
}

val baseUrl: String = localProperties["BASE_URL"] as? String ?: ""
val username: String = localProperties["USERNAME"] as? String ?: ""
val password: String = localProperties["PASSWORD"] as? String ?: ""

// Crear tarea para generar BuildConfig
tasks.register("generateBuildConfig") {
    doLast {
        val outputDir = file("$buildDir/generated/source/buildConfig/com/valher/resilink/core")
        outputDir.mkdirs()
        val buildConfigFile = file("$outputDir/BuildConfig.kt")
        buildConfigFile.writeText("""
            package com.valher.resilink.core

            object BuildConfig {
                const val BASE_URL = "$baseUrl"
                const val USERNAME = "$username"
                const val PASSWORD = "$password"
            }
        """.trimIndent())
    }
}

// Asegurarse de que la tarea generateBuildConfig se ejecute antes de la compilación
tasks.withType<JavaCompile> {
    dependsOn(tasks.named("generateBuildConfig"))
}

sourceSets {
    main {
        java {
            srcDirs("$buildDir/generated/source/buildConfig")
        }
    }
}

dependencies {
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    // Retrofit
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter.gson)

    // OkHttp (opcional, para logging)
    implementation(libs.okhttp.logging.interceptor)

    // Dependencias de prueba
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
}
