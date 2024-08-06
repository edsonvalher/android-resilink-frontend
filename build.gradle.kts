// build.gradle (nivel de proyecto)
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.android.library) apply false // Añadir plugin de Hilt
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
