# o2g-sdk

A java SDK for the ALE International O2G (OmniPCX OpenTouch Gateway) platform, providing a comprehensive API for telephony, call control, management and contact center services.

## Requirements

- jdk version >= 16
- An OmniPCX Enterprise node connected to an O2G server
- An O2G API license appropriate for the services you intend to use

## Getting Started

New to java or O2G? Follow the [Getting Started guide](GETTING_STARTED.md)
for a complete step-by-step walkthrough from installing the tools to your first login.

## Installation

o2g java SDK is available on maven central repository.
Add the following dependency to your project pom.

```xml
<dependency>
  <groupId>io.github.ale-openness.o2g</groupId>
  <artifactId>o2g-sdk</artifactId>
  <version>3.5.0</version>
</dependency>
```

## Quick Start

```java
import com.ale.o2g.TelephonyService;
import com.ale.o2g.O2G;
import com.ale.o2g.O2GException;
import com.ale.o2g.ServiceEndPoint;
import com.ale.o2g.Session;
import com.ale.o2g.Subscription;
import com.ale.o2g.events.telephony.TelephonyEventListener;
import com.ale.o2g.events.telephony.OnCallCreatedEvent;
import com.ale.o2g.types.Host;

try {
    // 1. Create a new O2G Service endpoint, configure the O2G IP address or FQDN
    ServiceEndPoint o2gServer = O2G.Connect(new Host("123.25.112.119"));

    // 2. Open a session
    Session session = o2gServer.openSession(new Credential(your-loginName, your-password), "MyApplication");

    // 3. Subscribe to events
    Subscription subscription = Subscription.newBuilder()
        .addTelephonyEventListener(new TelephonyEventListener() {
            @Override
            public void onCallCreated(OnCallCreatedEvent event) {
                System.out.println("New call:" + event.getCallRef());
            }
            ...
        }, new String[] {"*"})
        .build();

    // 4. Listen for events
    session.listenEvents(subscription);

    // 5. Use a service
    TelephonyService telephony = session.getTelephonyService();
    telephony.makeCall("1234", "5678");

    // 6. Close the session when done
    session.close();
}
catch (O2GException e) {
    // Manage exception
}
```

## Sessions

Three types of sessions are supported:

### User Session

Opened with user credentials. Allows the user to access all user-oriented services.
Most service methods that accept a `loginName` parameter will ignore it in a user session —
the session user is used automatically.

```java
Session session = o2gServer.openSession(new Credential(your-loginName, your-password), "MyApplication");

// loginName is ignored — operates on the session user
session.getTelephonyService().getCalls();
session.getRoutingService().activateDnd();
```

### Administrator Session

Opened with O2G administrator credentials. Allows access to all services for any user, and some services restricted to administrator (management).
Methods that accept a `loginName` parameter require it to be specified in an administrator session.

```java
Session session = o2gServer.openSession(new Credential(your-admin-loginName, your-password), "MyApplication");

// using user loginName is mandatory in an administrator session to perform action on behalf of this user
session.getTelephonyService().getCalls("oxe1000");
session.getCenterAgentService.getState("oxe1000");
```

### Supervised Session

Opened with administrator credentials combined with a supervised user identity.
Behaves exactly like a user session for the supervised user — `loginName` is ignored.
This allows an application to use user-oriented services on behalf of a specific user.

```java
import com.ale.o2g.SupervisedAccount;

Session session = o2gServer.openSession(
    new Credential(your-admin-loginName, your-password),
    "MyApplication",
    SupervisedAccount.withLoginName("oxe1000"));

// Now operates as oxe1000 — loginName is ignored
session.getTelephonyService().getCalls();
session.getRoutingService().activateDnd();
```

## Services
| Service | Description | License Required |
|---|---|---|
| `TelephonyService` | Call control, transfer, conference, recording | `TELEPHONY_ADVANCED` |
| `RoutingService` | Forward, overflow, Do Not Disturb | `TELEPHONY_ADVANCED` |
| `CommunicationlogService` | Communication history records | `TELEPHONY_ADVANCED` |
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
import com.ale.o2g.types.routing.Forward.Condition;

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
import com.ale.o2g.Subscription;
import com.ale.o2g.events.ccp.CallCenterPilotEventListener;
import com.ale.o2g.events.ccp.OnCallCreatedEvent;

Subscription subscription = Subscription.newBuilder()
    .addCallCenterPilotEventListener(new CallCenterPilotEventListener() {
        @Override
        public void onPilotCallCreated(OnPilotCallCreatedEvent event) {
            System.out.println("New call on pilot:" + event.getCallRef());
        }
        ...
    }, new String[] {"60141"})
    .build();

session.listenEvents(subscription);

session.getCallCenterPilotService().monitorStart("60141");
```

### Search the directory

```java
import com.ale.o2g.types.directory.Criteria;
import com.ale.o2g.types.directory.Criteria.AttributeFilter;
import com.ale.o2g.types.directory.Criteria.OperationFilter;
import com.ale.o2g.types.directory.SearchResult;
import com.ale.o2g.types.directory.SearchResult.Results;

// Create a search criteria
Criteria criteria = Criteria.create(AttributeFilter.LASTNAME, OperationFilter.BEGINS_WITH, "doe");

session.getDirectoryService().search(criteria);

// Get results
boolean finished = false;
while (!finished) {
    SearchResult result = session.getDirectoryService().getResults();
    if (result.getResultCode() == SearchResult.Code.OK) {
        Collection<Results> results = result.getResultElements();
        results.forEach((item) -> {
            Collection<PartyInfo> contacts = item.getContacts();
            ...
        });
    }
    else if (result.getResultCode() == SearchResult.Code.FINISH) {
        finished = true;
    }
    else {
        Thread.sleep(1000);
    }
}
```

### Query communication log records

```java
import com.ale.o2g.types.comlog.Option;
import com.ale.o2g.types.comlog.QueryFilter;
import com.ale.o2g.types.comlog.QueryResult;

QueryFilter filter = QueryFilter.newBuilder()
    .setBeforeDate(new GregorianCalendar(2026, Calendar.MARCH, 1).getTime())
    .setAfterDate(new GregorianCalendar(2026, Calendar.MARCH, 31).getTime())
    .setOptions(EnumSet.of(Option.UNANSWERED))
    .build();

QueryResult results = session.getCommunicationLogService().getComRecords(filter);
System.out.println("Total records: " + results.size());
```

### Manage a CCD agent

```java
// Log the agent in a processing group
session.getCallCenterAgentService().logon("oxe12000", "30000");

session.getCallCenterAgentService().setReady();
```

## API Reference

- [O2G REST API Reference](https://api.dspp.al-enterprise.com/o2g/)

## Versioning

This SDK follows the O2G API version it targets:

- **Major**: O2G API major version (currently 2.7 => SDK 3)
- **Minor**: O2G API patch version (currently 5 → 5)
- **Patch**: SDK release number

For example, `3.5.0` targets O2G API version 2.7.5.

## License

Copyright 2026 ALE International

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.