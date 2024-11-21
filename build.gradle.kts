plugins {
    java
    application
    //id("org.openjfx.javafxplugin") version "0.1.0"
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
    //implementation("org.openjfx:javafx-controls:23")
    //implementation("org.openjfx:javafx-fxml:23")
    //implementation("org.openjfx:javafx-web:23")
}

application {
    mainClass = "dev.tonimatas.fixermc.Main"
}


//javafx {
//    version = "23"
//    modules = listOf("javafx.controls", "javafx.fxml", "javafx.web")
//}