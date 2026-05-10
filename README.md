# o2g-sdk

A Java SDK for the ALE International O2G (OmniPCX OpenTouch Gateway) platform, providing a comprehensive API for telephony, call control, management and contact center services.

## Requirements

- JDK version >= 16
- An OmniPCX Enterprise node connected to an O2G server
- An O2G API license appropriate for the services you intend to use

## Getting Started

New to Java or O2G? Follow the [Getting Started guide](GETTING_STARTED.md)
for a complete step-by-step walkthrough from installing the tools to your first login.

## Installation

The o2g Java SDK is available on the Maven Central repository.
Add the following dependency to your project `pom.xml`:

```xml
<dependency>
  <groupId>io.github.ale-openness.o2g</groupId>
  <artifactId>o2g-sdk</artifactId>
  <version>3.5.4</version>
</dependency>
```

## Quick Start
```java
import com.ale.o2g.*;
import com.ale.o2g.events.telephony.*;
import com.ale.o2g.types.*;

// 1. Configure the O2G server
ServiceEndPoint endPoint = O2G.connect(O2GServers.newBuilder()
    .primaryHost(new Host("123.25.112.119"))
    .build());

// 2. Optionally set a custom monitoring policy
endPoint.setSessionMonitoringPolicy(new MyMonitoringPolicy());

// 3. Open a session — retries automatically if server is not yet reachable
Session session = endPoint.openSession(
    new Credential("loginName", "password"), "MyApplication");

// 4. Subscribe to events
session.listenEvents(Subscription.newBuilder()
    .addTelephonyEventListener(new TelephonyEventListener() {
        @Override
        public void onCallCreated(OnCallCreatedEvent event) {
            System.out.println("New call: " + event.getCallRef());
        }
    }, new String[] {"*"})
    .build());

// 5. Use a service
session.getTelephonyService().makeCall("1234", "5678");

// 6. Close the session when done
session.close();
```

## Server Configuration

Use `O2GServers` to configure the O2G server topology. Three deployment
configurations are supported:

### Standalone server
```java
ServiceEndPoint endPoint = O2G.connect(O2GServers.newBuilder()
    .primaryHost(new Host("10.0.0.1"))
    .build());
```

### Local HA (virtual IP)

Two O2G server instances sharing the same virtual IP address or URL.
Configure it exactly like a standalone server — the virtual IP routes
transparently to whichever node is active:

```java
ServiceEndPoint endPoint = O2G.connect(O2GServers.newBuilder()
    .primaryHost(new Host("vip.example.com"))
    .build());
```

### Geographic HA (two distinct hosts)

Two O2G server instances at different locations with distinct IP addresses.
On primary failure, the SDK switches immediately to the secondary and stays
there permanently:

```java
ServiceEndPoint endPoint = O2G.connect(O2GServers.newBuilder()
    .primaryHost(new Host("10.0.0.1"))
    .secondaryHost(new Host("10.0.0.2"))
    .build());
```

A `Host` can also be configured with both a private and a public address:

```java
new Host("10.0.0.1", "93.12.1.1")
```

The SDK tries the private address first, then falls back to the public address.

## Session Monitoring and Recovery

The SDK automatically handles session failures and recovery. When the O2G
server crashes or becomes unreachable, the SDK:

1. Detects the failure via the chunk stream or keep-alive
2. Notifies the application via `onSessionLost`
3. Retries the connection with exponential backoff
4. Switches to the secondary server if geographic HA is configured
5. Re-subscribes to events after recovery
6. Notifies the application via `onSessionRecovered`

### Custom monitoring policy

Extend `SessionMonitoringPolicy` to control SDK behaviour and receive
notifications:

```java
public class MyMonitoringPolicy extends DefaultSessionMonitoringPolicy {

    @Override
    public void onSessionLost(String reason) {
        System.out.println("Session lost: " + reason + " — recovering...");
        // Update your UI to show a reconnecting indicator
    }

    @Override
    public void onSessionRecovered() {
        System.out.println("Session recovered — back online.");
        // Resume application activity
    }

    @Override
    public Behavior getBehaviorOnConnectFailure(Exception e) {
        // Retry every 10 seconds instead of the default 5
        return new RetryAfter(10, TimeUnit.SECONDS);
    }

    @Override
    public Behavior getBehaviorOnChunkChannelFailure(Session session, Exception e) {
        // Abort immediately to trigger session recovery
        return new Abort();
    }
}
```

Set the policy before opening a session:

```java
endPoint.setSessionMonitoringPolicy(new MyMonitoringPolicy());
Session session = endPoint.openSession(credential, "MyApplication");
```

### Default behaviours

| Situation | Default behaviour |
|---|---|
| Initial connection fails | Retry after 5 seconds |
| Chunk network error | Abort → trigger recovery |
| Keep-alive network error | Abort → trigger recovery |
| Keep-alive rejected by server | Trigger recovery |
| Server switched to secondary | Permanent — never switches back |

## Sessions

Three types of sessions are supported:

### User Session

Opened with user credentials. Allows the user to access all user-oriented
services. Most service methods that accept a `loginName` parameter will
ignore it in a user session — the session user is used automatically.

```java
Session session = endPoint.openSession(
    new Credential("loginName", "password"), "MyApplication");

// loginName is ignored — operates on the session user
session.getTelephonyService().getCalls();
session.getRoutingService().activateDnd();
```

### Administrator Session

Opened with O2G administrator credentials. Allows access to all services
for any user, and some services restricted to administrators.
Methods that accept a `loginName` parameter require it to be specified.

```java
Session session = endPoint.openSession(
    new Credential("adminLogin", "password"), "MyApplication");

// loginName is mandatory in an administrator session
session.getTelephonyService().getCalls("oxe1000");
session.getCallCenterAgentService().getState("oxe1000");
```

### Supervised Session

Opened with administrator credentials combined with a supervised user
identity. Behaves exactly like a user session for the supervised user.

```java
Session session = endPoint.openSession(
    new Credential("adminLogin", "password"),
    "MyApplication",
    SupervisedAccount.withLoginName("oxe1000"));

// Operates as oxe1000 — loginName is ignored
session.getTelephonyService().getCalls();
session.getRoutingService().activateDnd();
```

## Event Subscription

### Chunk eventing (default)

The SDK opens an outgoing HTTPS connection to the O2G server and receives
events as a stream. No server-side endpoint is required from the application.

```java
session.listenEvents(Subscription.newBuilder()
    .addTelephonyEventListener(myTelephonyListener)
    .addRoutingEventListener(myRoutingListener)
    .setTimeout(10)
    .build());
```

### Webhook eventing

The O2G server sends events via HTTP POST to a URL provided by the
application. The application must expose an HTTPS endpoint and implement
the `WebHook` interface.

```java
public class MyWebHook implements WebHook {
    private EventProcessor processor;

    @Override
    public URI getURI() {
        return URI.create("https://myapp.example.com/o2g/events");
    }

    @Override
    public void connectProcessor(EventProcessor processor) {
        this.processor = processor;
    }

    // Call this from your HTTP endpoint handler
    public void onHttpPost(String rawBody) throws InterruptedException {
        if (processor != null) {
            processor.process(rawBody);
        }
    }
}

session.listenEvents(Subscription.newBuilder()
    .addMaintenanceEventListener(myMaintenanceListener)
    .setWebHook(new MyWebHook())
    .build());
```

The SDK provides no built-in HTTP server — the application is responsible
for exposing the endpoint using any framework (Spring Boot, Jetty, Undertow,
etc.). See the [webhook examples](examples/webhook) for complete working
examples with common frameworks.

## Services

| Service | Description | License Required |
|---|---|---|
| `TelephonyService` | Call control, transfer, conference, recording | `TELEPHONY_ADVANCED` |
| `RoutingService` | Forward, overflow, Do Not Disturb | `TELEPHONY_ADVANCED` |
| `CommunicationLogService` | Communication history records | `TELEPHONY_ADVANCED` |
| `MessagingService` | Voicemail and mailbox management | `TELEPHONY_ADVANCED` |
| `DirectoryService` | Enterprise directory search | `TELEPHONY_ADVANCED` |
| `EventSummaryService` | Missed calls, voicemail counters | `TELEPHONY_ADVANCED` |
| `UsersService` | User profile and preferences | — |
| `CallCenterAgentService` | CCD agent state and skills | `CONTACTCENTER_AGENT` |
| `CallCenterPilotService` | CCD pilot monitoring | `CONTACTCENTER_SVCS` |
| `CallCenterRealtimeService` | Real-time ACD statistics | `CONTACTCENTER_SVCS` |
| `CallCenterStatisticsService` | Historical ACD statistics | `CONTACTCENTER_SVCS` |
| `CallCenterManagementService` | CCD pilot and calendar management | `CONTACTCENTER_SVCS` |
| `MaintenanceService` | System status and PBX health | — |
| `PbxManagementService` | PBX object model management | `MANAGEMENT` |
| `UsersManagementService` | Administrator user management | — |
| `AnalyticsService` | Charging and incident data | — |

## Examples

### Forward calls to voicemail when busy
```java
session.getRoutingService().forwardOnVoiceMail(ForwardCondition.BUSY);
```

### Transfer a call
```java
// Supervised transfer
session.getTelephonyService().transfer(activeCallRef, heldCallRef);

// Blind transfer
session.getTelephonyService().blindTransfer(callRef, "12002");
```

### Monitor a CCD pilot
```java
Subscription subscription = Subscription.newBuilder()
    .addCallCenterPilotEventListener(new CallCenterPilotEventListener() {
        @Override
        public void onPilotCallCreated(OnPilotCallCreatedEvent event) {
            System.out.println("New call on pilot: " + event.getCallRef());
        }
    }, new String[] {"60141"})
    .build();

session.listenEvents(subscription);
session.getCallCenterPilotService().monitorStart("60141");
```

### Search the directory
```java
Criteria criteria = Criteria.create(
    AttributeFilter.LASTNAME, OperationFilter.BEGINS_WITH, "doe");

session.getDirectoryService().search(criteria);

boolean finished = false;
while (!finished) {
    SearchResult result = session.getDirectoryService().getResults();
    if (result.getResultCode() == SearchResult.Code.OK) {
        result.getResultElements().forEach(item -> {
            item.getContacts().forEach(contact -> {
                System.out.println(contact);
            });
        });
    } else if (result.getResultCode() == SearchResult.Code.FINISH) {
        finished = true;
    } else {
        Thread.sleep(1000);
    }
}
```

### Query communication log records
```java
QueryFilter filter = QueryFilter.newBuilder()
    .setAfterDate(new GregorianCalendar(2026, Calendar.MARCH, 1).getTime())
    .setBeforeDate(new GregorianCalendar(2026, Calendar.MARCH, 31).getTime())
    .setOptions(EnumSet.of(Option.UNANSWERED))
    .build();

QueryResult results = session.getCommunicationLogService().getComRecords(filter);
System.out.println("Total records: " + results.size());
```

### Manage a CCD agent
```java
session.getCallCenterAgentService().logon("oxe12000", "30000");
session.getCallCenterAgentService().setReady();
```

## Migration from previous versions

If you were using `O2G.connect(Host)` from a previous version, update
your code as follows:

```java
// Before
ServiceEndPoint endPoint = O2G.connect(new Host("10.0.0.1"));

// After
ServiceEndPoint endPoint = O2G.connect(O2GServers.newBuilder()
    .primaryHost(new Host("10.0.0.1"))
    .build());
```

The old `O2G.connect(Host)` method is still available but deprecated.

## Logging

The SDK uses SLF4J for logging. Add your preferred SLF4J implementation
to your project (Logback, Log4j2, etc.) and configure log levels as needed.

Key logger categories:

| Logger | Controls |
|---|---|
| `com.ale.o2g.internal` | All internal SDK logging |
| `com.ale.o2g.internal.ServiceEndPointImpl` | Connection, recovery, failover |
| `com.ale.o2g.internal.SessionImpl` | Session lifecycle |
| `com.ale.o2g.internal.KeepAlive` | Keep-alive |
| `com.ale.o2g.internal.events.ChunkEventListener` | Chunk eventing |
| `com.ale.o2g.internal.events.EventDispatcher` | Event dispatching |

Example Logback configuration:

```xml
<configuration>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- SDK internal logging -->
    <logger name="com.ale.o2g.internal" level="INFO"/>

    <!-- Uncomment for detailed recovery and failover traces -->
    <!-- <logger name="com.ale.o2g.internal.ServiceEndPointImpl" level="DEBUG"/> -->

    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
    </root>
</configuration>
```

## API Reference

- [O2G REST API Reference](https://api.dspp.al-enterprise.com/o2g/)

## Versioning

This SDK follows the O2G API version it targets:

- **Major**: O2G API major version (currently 2.7 → SDK 3)
- **Minor**: O2G API patch version (currently 5 → 5)
- **Patch**: SDK release number

For example, `3.5.0` targets O2G API version 2.7.5.

## License

Copyright 2026 ALE International

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.