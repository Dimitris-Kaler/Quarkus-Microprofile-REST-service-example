# Quarkus Microprofile REST Service Example
This project is a simple RESTful API built with Quarkus using MicroProfile JAX-RS. 
It demonstrates how to create a lightweight REST service that handles various HTTP requests—GET, POST, and query-based—using Quarkus. 
The project is built using Gradle and leverages REST Assured for integration testing with JUnit 5.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.


## Project Overview

**Quarkus Microprofile REST Service Example** is designed to help you learn how to build a RESTful service using Quarkus. The project covers:

- Setting up a Quarkus project with Gradle.
- Configuring the application and dependency injection using Quarkus.
- Defining REST endpoints with path parameters, query parameters, and JSON request bodies.
- Writing integration tests that interact with an embedded Quarkus server using REST Assured.

---

## Project Setup and Creation

Before you begin coding, you need to create your project. There are two main ways to do this:

### Option 1: Using the Online Generator

If you don't have the Quarkus CLI installed, you can use the online generator:

1. Visit [https://code.quarkus.io/](https://code.quarkus.io/).
2. Fill in the project details (for example, set the Group as `dim.kal` and Artifact as `quarkus-example-app`).
3. Choose **Gradle** as the build tool(You can choose other build up tool).
4. Select the extensions you need.
5. Click **Generate your application** to download a ZIP file containing your new project.
6. Extract the ZIP file to your desired project folder.

### Option 2: Manual Setup

1. **Create a New Project Folder:**  
   Create a folder for your project and navigate into it.

2. **Initialize a Gradle Project:**  
   If you have Gradle installed, run:
   ```bash
   gradle init --type java-application
   ```
This will create a basic project structure and a `build.gradle` file.

3. **Add Quarkus Dependencies:**
Manually update your build.gradle with the required Quarkus dependencies (see the Package Configuration section below).

4. **Configure Your Project:**
Add your source code under src/main/java and configuration files under src/main/resources. Create your test files under src/test/java (or src/test/groovy if using Groovy).

Note: If you decide later to install the Quarkus CLI, refer to the [Quarkus CLI guide](https://quarkus.io/guides/cli-tooling).


## Installation and Running the Application
### Build the Application
From the root of your project, run:


```bash
./gradlew clean build
```
This command downloads the dependencies, compiles your code, and runs tests.

### Running in Development Mode
Start the Quarkus application in development mode (with live reload):

```bash
./gradlew quarkusDev
```
By default, the application will be available at http://localhost:8080 (or on the port specified in your configuration).
**_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Running in Production Mode
To run the application in production mode:

```bash
./gradlew run
```


## Endpoints
### Root Endpoint
- **Path:** /
- **Method:** GET
- **Description:** Returns a static greeting message.
- **Response Example:**
```json
{
"msg": "Hello World"
}
```
### Path Parameter Greeting
- **Path:** /greet/{name}
- **Method:** GET
- **Description:** Returns a greeting message that incorporates the name provided in the URL.
- **Response Example:**
```json
{
"msg": "Hello Dimitris!"
}
```
### Query Parameter Greeting
- **Path:** /greeting
- **Method:** GET
- **Description:** Returns a personalized greeting using the name and age query parameters. If either parameter is missing, it returns HTTP 400.
- **Response Example (with valid parameters):**
```json
{
"msg": "Hello my name is John and i'm 30 years old!"
}
```
### Request Body Greeting
- **Path:** /greet
- **Method:** POST
- **Description:** Accepts a JSON request body containing name and age to generate a greeting. Returns HTTP 400 if the request body is invalid.
- **Response Example:**
```json
{
"msg": "Hello my name is Bob Trench and i'm 25 old!"
}
```
## Package Configuration
The `build.gradle` file defines the project’s dependencies, plugins, and source sets. Below is a sample configuration:

```gradle
plugins {
id 'java'
id 'io.quarkus'
id 'groovy'
}

repositories {
mavenCentral()
mavenLocal()
}

dependencies {
implementation enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}")
implementation 'io.quarkiverse.microprofile:quarkus-microprofile:3.4.0'
implementation 'io.quarkus:quarkus-arc'
testImplementation 'io.quarkus:quarkus-junit5'
testImplementation('io.rest-assured:rest-assured:5.3.0')
}

group 'dim.kal'
version '1.0.0-SNAPSHOT'

java {
sourceCompatibility = JavaVersion.VERSION_17
targetCompatibility = JavaVersion.VERSION_17
}

test {
systemProperty "java.util.logging.manager", "org.jboss.logmanager.LogManager"
}

compileJava {
options.encoding = 'UTF-8'
options.compilerArgs << '-parameters'
}

compileTestJava {
options.encoding = 'UTF-8'
}

tasks.withType(Copy) {
duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.named("processResources", Copy) {
duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.named("processTestResources", Copy) {
duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
```

## Testing
The project includes integration tests written with JUnit 5 and REST Assured. These tests run against an embedded Quarkus server (enabled by the @QuarkusTest annotation) and verify that each endpoint returns the expected status codes and responses.

### Example Test Scenarios
- **Hello World Test:**
Verifies that a GET request to `/` returns HTTP 200 with the message `"Hello World"`.

- **Path Parameter Test:**
Iterates over an array of names to test the `/greet/{name}` endpoint and verifies that it returns the correct greeting for each name.

- **Query Parameter Test:**
Uses a two-dimensional array to test valid query parameters for `/greeting` and also tests scenarios where parameters are missing (expecting a 400 status code).

- **POST Request Tests:**
Verifies that sending a valid JSON body to `/greet` returns the expected greeting, while invalid or missing bodies return appropriate error codes.

Run tests with:

```bash
./gradlew clean test
```
## Dependencies
Key dependencies used in this project:

- **Quarkus:** Provides the runtime, dependency injection (CDI), and MicroProfile features.
- **Quarkus Microprofile:** Enables MicroProfile specifications such as RESTEasy, JSON-B, and more.
- **REST Assured:** Used for sending HTTP requests in integration tests.
- **JUnit 5:** For writing and executing tests.
- **Groovy (optional):** For test scripting if needed.

