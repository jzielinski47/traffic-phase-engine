plugins {
    id("java")
}

group = "jzielinski"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.0")
}

tasks {
    test {
        useJUnitPlatform()
    }

    jar {
        manifest {
            attributes["Main-Class"] = "com.jzielinski.Main"
        }
    }
}



