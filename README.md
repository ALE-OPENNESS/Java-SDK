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
    <version>1.0.0</version>
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
