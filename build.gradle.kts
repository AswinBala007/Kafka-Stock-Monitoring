plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    // https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients
    implementation("org.apache.kafka:kafka-clients:3.3.1")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:1.7.36")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-simple
    implementation("org.slf4j:slf4j-simple:1.7.36")
    // HTTP Client
    implementation("org.apache.httpcomponents.client5:httpclient5:5.2")
    implementation("org.apache.httpcomponents.client5:httpclient5-fluent:5.2")

    implementation("org.apache.kafka:kafka-clients:3.6.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

//tasks.register('runProducer', JavaExec) {
//    classpath = sourceSets.main.runtimeClasspath
//    mainClass = 'com.stocks.producer.StockProducer'
//}
//
//tasks.register('runConsumer', JavaExec) {
//    classpath = sourceSets.main.runtimeClasspath
//    mainClass = "com.stocks.consumer.StockConsumer"
//}

tasks.test {
    useJUnitPlatform()
}