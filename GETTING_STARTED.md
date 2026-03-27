# Getting Started with java o2g-sdk

This guide walks you through everything you need — from installing the required tools to successfully logging in to an O2G server — with no prior programming experience required.

---

## What you will need

- A computer running **Windows**, **macOS**, or **Linux**
- Access to an **O2G server** (hostname or IP address, login name and password)
- An internet connection

---

## Step 1 — Install Java

Java is the runtime that allows you to run Java programs on your computer.

1. Go to [https://adoptium.net](https://adoptium.net)
2. Download the **LTS** version (recommended for most users) — Java 16 (minimum) or later
3. Run the installer and follow the on-screen instructions
4. Verify the installation by opening a terminal and running:

```bash
java --version
```

The command should print a version number (e.g. `openjdk 21.0.2`).

> **Opening a terminal:**
> - **Windows**: press `Win + R`, type `cmd`, press Enter
> - **macOS**: press `Cmd + Space`, type `Terminal`, press Enter
> - **Linux**: press `Ctrl + Alt + T`

---

## Step 2 — Install an IDE (optional but recommended)

An IDE makes writing Java much easier with code completion, error highlighting and project management.

**Option A — IntelliJ IDEA (recommended)**
1. Go to [https://www.jetbrains.com/idea](https://www.jetbrains.com/idea)
2. Download the free **Community Edition**
3. Install and launch it

**Option B — Eclipse**
1. Go to [https://www.eclipse.org/downloads](https://www.eclipse.org/downloads)
2. Download **Eclipse IDE for Java Developers**
3. Install and launch it

---

## Step 3 — Install a build tool

You can use either **Maven** or **Gradle** — both are widely used in the Java ecosystem. Pick one.

### Option A — Maven

1. Go to [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)
2. Download the latest binary zip archive
3. Unzip it to a folder of your choice (e.g. `C:\maven` on Windows)
4. Add the `bin` folder to your system `PATH`
5. Verify the installation:

```bash
mvn --version
```

> **Note:** IntelliJ IDEA includes a bundled Maven — you can skip this step if you use IntelliJ.

### Option B — Gradle

1. Go to [https://gradle.org/install](https://gradle.org/install)
2. Download the latest binary zip archive
3. Unzip it to a folder of your choice (e.g. `C:\gradle` on Windows)
4. Add the `bin` folder to your system `PATH`
5. Verify the installation:

```bash
gradle --version
```

> **Note:** IntelliJ IDEA also includes a bundled Gradle — you can skip this step if you use IntelliJ.

---

## Step 4 — Create your project

### With Maven

Open a terminal and run:

```bash
mvn archetype:generate \
  -DgroupId=com.mycompany \
  -DartifactId=my-o2g-app \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DarchetypeVersion=1.4 \
  -DinteractiveMode=false

cd my-o2g-app
```

### With Gradle

Open a terminal and run:

```bash
mkdir my-o2g-app
cd my-o2g-app
gradle init --type java-application --dsl groovy
```

Follow the prompts — accept the defaults when asked.

---

## Step 5 — Add the O2G SDK dependency

### With Maven

Open the `pom.xml` file in your project folder and add the following inside the `<dependencies>` section:

```xml
<dependency>
    <groupId>io.github.ale-openness.o2g</groupId>
    <artifactId>o2g-sdk</artifactId>
    <version>3.5.0</version>
</dependency>
```

Your `pom.xml` should look like this:

```xml
<project>
    ...
    <dependencies>
        <dependency>
            <groupId>io.github.ale-openness.o2g</groupId>
            <artifactId>o2g-sdk</artifactId>
            <version>3.5.0</version>
        </dependency>
    </dependencies>
    ...
</project>
```

Then download the dependency by running:

```bash
mvn dependency:resolve
```

### With Gradle

Open the `app/build.gradle` file and add the SDK inside the `dependencies` block:

```groovy
dependencies {
    implementation 'io.github.ale-openness.o2g:o2g-sdk:3.5.0'
}
```

Then download the dependency by running:

```bash
gradle dependencies
```

---

## Step 6 — Write your first program

### With Maven

Open the file `src/main/java/com/mycompany/App.java` and replace its content with the following:

```java
package com.mycompany;

import com.ale.o2g.O2G;
import com.ale.o2g.O2GException;
import com.ale.o2g.ServiceEndPoint;
import com.ale.o2g.Session;
import com.ale.o2g.types.common.Credential;
import com.ale.o2g.types.common.Host;

public class App {

    public static void main(String[] args) throws Exception {

        System.out.println("Logging in...");

        try {
            // 1. Create a new O2G service endpoint, configure the O2G IP address or FQDN
            ServiceEndPoint o2gServer = O2G.Connect(new Host("YOUR_O2G_SERVER_ADDRESS"));

            // 2. Open a session with your credentials
            Session session = o2gServer.openSession(
                new Credential("YOUR_LOGIN", "YOUR_PASSWORD"),
                "MyApplication"
            );

            System.out.println("Login successful!");

            // 3. The SDK is now ready — you can call any service here

            // 4. Always close the session when done
            session.close();
            System.out.println("Logged out.");

        } catch (O2GException e) {
            System.out.println("Login failed. Please check your credentials and server address.");
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

### With Gradle

Open the file `app/src/main/java/com/mycompany/App.java` and replace its content with the same code as above.

Replace the following placeholders in both cases:
- `YOUR_O2G_SERVER_ADDRESS` — the hostname or IP address of your O2G server (e.g. `192.168.1.100`)
- `YOUR_LOGIN` — your O2G user login name
- `YOUR_PASSWORD` — your O2G user password

---

## Step 7 — Run your program

### With Maven

```bash
mvn compile exec:java -Dexec.mainClass="com.mycompany.App"
```

### With Gradle

```bash
gradle run
```

If everything is configured correctly, you should see:

```
Logging in...
Login successful!
Logged out.
```

---

## Troubleshooting

**`Login failed`**
- Double-check the server address, login name and password
- Make sure the O2G server is reachable from your computer (try opening the address in a browser)
- If the server uses a self-signed SSL certificate, you may need to disable certificate verification for testing. Add the following JVM argument when running your program:

  With Maven:
  ```bash
  mvn compile exec:java -Dexec.mainClass="com.mycompany.App" -Do2g.disable.ssl=true
  ```

  With Gradle, add this to your `app/build.gradle`:
  ```groovy
  run {
      jvmArgs '-Do2g.disable.ssl=true'
  }
  ```

  Remove this option in production.

**`Could not resolve dependency`**
- Make sure you have an internet connection
- Maven: try running `mvn dependency:resolve` again
- Gradle: try running `gradle dependencies` again
- Check that the version in your build file matches the latest published version

**`ClassNotFoundException` or `NoClassDefFoundError`**
- Maven: try `mvn clean compile`
- Gradle: try `gradle clean build`

---

## What's next?

Once logged in, you can start using the SDK services through the `session` object. Here are a few examples:

### Make a phone call

```java
session.getTelephonyService().makeCall("myDeviceId", "1234");
```

### Get your active calls

```java
var calls = session.getTelephonyService().getCalls();
System.out.println(calls);
```

### Subscribe to telephony events and listen for incoming calls

```java
Subscription subscription = Subscription.newBuilder()
    .addTelephonyEventListener(new TelephonyEventListener() {
        @Override
        public void onCallCreated(OnCallCreatedEvent event) {
            System.out.println("New call:" + event.getCallRef());
        }
        ...
    }, new String[] {"*"})
    .build();
session.listenEvents(subscription);

```

For a full list of available services and methods, see the [O2G REST API Reference](https://api.dspp.al-enterprise.com/o2g/).