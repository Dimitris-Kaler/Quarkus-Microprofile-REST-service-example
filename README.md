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

