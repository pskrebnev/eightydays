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

    // https://mvnrepository.com/artifact/org.testng/testng
    testImplementation("org.testng:testng:7.10.2")

    // https://mvnrepository.com/artifact/com.microsoft.playwright/playwright
    implementation("com.microsoft.playwright:playwright:1.45.0")

    // https://mvnrepository.com/artifact/io.qameta.allure/allure-junit5
//    testImplementation("io.qameta.allure:allure-junit5:2.27.0")

    // https://mvnrepository.com/artifact/io.qameta.allure/allure-testng
    implementation("io.qameta.allure:allure-testng:2.27.0")
}

//You can then run the tasks separately using:
//./gradlew test
//and
//./gradlew testNG

tasks.test {
    useJUnitPlatform()
}

tasks.register<Test>("testNG") {
    useTestNG {
        suiteXmlFiles.add(file("src/test/resources/testng.xml"))
    }
}

