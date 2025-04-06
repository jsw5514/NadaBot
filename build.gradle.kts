plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("net.dv8tion:JDA:5.0.0-beta.13")
    implementation("org.slf4j:slf4j-api:1.7.36") // SLF4J API
    implementation("ch.qos.logback:logback-classic:1.2.10") // Logback 바인딩
}
tasks.test {
    useJUnitPlatform()
}