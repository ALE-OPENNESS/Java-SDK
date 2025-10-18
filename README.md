# o2g-sdk

This java SDK allows to create applications using [OpenTouch Open Gateway API](https://api.dspp.al-enterprise.com/omnipcx-open-gateway-02g/).
It provides access to all the services available in O2G.

To connect to an O2G server, you create an O2G application, use your credential to connect and use services.

## Installation
o2g java SDK is available on maven central repository.
Add the following dependancy to your project pom.
```xml
  <dependency>
    <groupId>io.github.ale-openness.o2g</groupId>
    <artifactId>o2g-sdk</artifactId>
    <version>3.4.1</version>
  </dependency>
```

## Usage
```java
// Create a new O2G Service endpoint
try {
    ServiceEndPoint o2gServer = O2G.Connect(new Host("123.25.112.119"));
    // Create a session
    Session session = o2gServer.openSession(new Credential(your-loginName, your-password), "MyApplication");

    // Use a service
    AnalyticsService analyticService = session.getAnalyticsService();
    List<Incident> incidents = analyticService.getIncidentsAsync(7);
}
catch (O2GException e) {
    // Manage exception
}
```

## License
[MIT](https://choosealicense.com/licenses/mit/)

# Requirements
The O2G java SDK uses jdk16 version.

# Building o2g java SDK
To build the O2G java SDK with Git, clone the repository by running `git clone https://github.com/ALE-OPENNESS/Java-SDK.git` from a command prompt. You can then navigate to the directory and open the project file with eclipse or any other java ide. Build can be done using maven or gradle.

# Contributions
We welcome your input on issues and suggestions. We encourage you to [file a new issue](https://github.com/ALE-OPENNESS/java-SDK/issues/new) for any feedback or questions!

