plugins {
    kotlin("jvm") version "1.9.22"
    application
}

group = "com.benchmark"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
}

application {
    mainClass.set("com.benchmark.MainKt")
}
