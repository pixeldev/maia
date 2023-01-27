plugins {
    id("maia.publishing-conventions")
    id("maia.paper-conventions")
}

dependencies {
    api(project(":maia-paper-api"))
    compileOnly(libs.spigot)
}