plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.7"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-nop:2.0.7")
    implementation("net.minestom:minestom-snapshots:96dbad809f")
}

tasks.withType<Jar> {
    manifest {
        // Change this to your main class
        attributes["Main-Class"] = "org.example.Main"
    }
}