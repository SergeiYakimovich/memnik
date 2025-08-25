val mapstructVersion: String by project
val shedLockVersion: String by project

plugins {
	java
	id("org.springframework.boot") version "3.1.0"
	id("io.spring.dependency-management") version "1.1.0"
	id("jacoco")
}

group = "com.memnik"
version = "0.0.1-SNAPSHOT"

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-mail")

	implementation("org.liquibase:liquibase-core")
	implementation("org.postgresql:postgresql")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	implementation("org.mapstruct:mapstruct:$mapstructVersion")
	annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

	implementation("net.javacrumbs.shedlock:shedlock-spring:$shedLockVersion")
	implementation("net.javacrumbs.shedlock:shedlock-provider-jdbc-template:$shedLockVersion")

	implementation("org.telegram:telegrambots:6.5.0")

	implementation("org.hibernate.orm:hibernate-core")

	implementation("org.apache.commons:commons-lang3:3.17.0")


// test only dependencies

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootJar {
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
	mainClass.set("com.memnik.Application")
	launchScript()
}

jacoco {
	toolVersion = "0.8.8"
}

tasks.jacocoTestReport {
	reports {
		xml.required.set(false)
		csv.required.set(false)
		html.required.set(true) }
}

tasks.test {
	finalizedBy(tasks.jacocoTestReport)
	doLast {
		println("View code coverage at:")
		println(".../reports/jacoco/test/html/index.html")
	}
}

tasks.jacocoTestCoverageVerification {
	violationRules {
		rule {
			limit {
				minimum = BigDecimal.valueOf(0.5)
			}
		}
	}
}
