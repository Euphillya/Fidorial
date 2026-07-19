plugins {
    id("java")
    id("java-gradle-plugin")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.13.1")
    implementation("com.squareup:javapoet:1.13.0")

    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

gradlePlugin {
    plugins {
        register("fidorialRegistryGenerator") {
            id = "fr.fidorial.registry-generator"
            implementationClass = "fr.fidorial.registrygen.FidorialRegistryGeneratorPlugin"
            displayName = "Fidorial Registry Generator"
            description = "Generates Java registry constants from Mojang registry reports."
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}