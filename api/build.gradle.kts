plugins {
    id("maia.publishing-conventions")
}

dependencies {
    api(libs.storage.caffeine)
    compileOnly("com.google.code.gson:gson:2.9.0")
}