plugins {
    java
    id("java-gradle-plugin")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.kyori:adventure-api:5.2.0")
    implementation("com.google.code.gson:gson:2.13.1")
    implementation("com.palantir.javapoet:javapoet:0.18.0")

    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}


gradlePlugin {
    plugins {
        register("fidorialRegistryGenerator") {
            id = "fr.fidorial.registry-generator"
            implementationClass = "fr.fidorial.registrygen.FidorialRegistryGeneratorPlugin"
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
