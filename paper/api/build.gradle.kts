plugins {
    id("maia.publishing-conventions")
    id("maia.paper-conventions")
}

dependencies {
    api(project(":maia-api"))
    api(project(":maia-inject"))

    api(libs.storage.mongo)
    api(libs.storage.gson)
    api(libs.storage.redis) {
        exclude("org.slf4j", "slf4j-api")
        exclude("com.google.code.gson", "gson")
    }

    api("org.spongepowered:configurate-yaml:4.1.2")
    api("ml.stargirls:command-paper:1.0.1")
    api("ml.stargirls:message-sourcetype-bukkit-yml:1.0.0-SNAPSHOT")
    api("io.papermc:paperlib:1.0.7")

    compileOnly(libs.spigot)
}