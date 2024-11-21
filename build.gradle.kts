plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "8.0.0"
}

group = "dev.tonimatas"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

sourceSets.main.get().resources.srcDirs("src/main/resources")

dependencies {
    implementation("fr.flowarg:flowupdater:1.9.2")
    implementation("fr.flowarg:openlauncherlib:3.2.11")
    implementation("net.raphimc:MinecraftAuth:4.1.1")
    implementation("com.formdev:flatlaf:3.5.2")
}

application {
    mainClass = "dev.tonimatas.fixermc.Main"
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "dev.tonimatas.fixermc.Main"
    }
}
