import dev.polariscore.tasks.CreateTextFile
import dev.polariscore.tasks.Utils

plugins {
    java
    application
}

group = "dev.tonimatas"
version = "1.0.0"

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

tasks.register<CreateTextFile>("createLibrariesFile") {
    fileName = "libraries.txt"
    content = Utils.getDependencies(configurations.implementation.get().copy())
}

tasks.register<CreateTextFile>("createRepositoriesFile") {
    fileName = "repositories.txt"
    content = Utils.getRepositories(repositories)
}

tasks.processResources {
    val replaceProperties = mapOf("version" to version)
    inputs.properties(replaceProperties)

    filesMatching("fixer.properties") {
        expand(replaceProperties)
    }
    
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

application {
    mainClass = "dev.tonimatas.fixermc.Main"
}

tasks.jar {
    from(tasks.getByName("createLibrariesFile"))
    from(tasks.getByName("createRepositoriesFile"))

    manifest {
        attributes(
            "Launcher-Agent-Class" to "dev.tonimatas.fixermc.Agent",
            "Agent-Class" to "dev.tonimatas.fixermc.Agent",
            "Premain-Class" to "dev.tonimatas.fixermc.Agent",
            "Main-Class" to "dev.tonimatas.fixermc.Main",
            "Multi-Release" to true
        )
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.named<JavaExec>("run") {
    args = listOf("--developer")
}