/*
* Copyright 2026 ALE International
*
* Permission is hereby granted, free of charge, to any person obtaining a copy of this 
* software and associated documentation files (the "Software"), to deal in the Software 
* without restriction, including without limitation the rights to use, copy, modify, merge, 
* publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons 
* to whom the Software is furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all copies or 
* substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
* BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
* DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.ale.o2g.internal.types;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Service}.
 */
@DisplayName("Service")
class ServiceTest {

    // ── get() ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("get() — returns a non-null Service")
    void get_returnsNonNull() {
        assertNotNull(Service.get("telephony"));
    }

    @Test
    @DisplayName("get() — converts name to lowercase")
    void get_convertsToLowercase() {
        Service lower = Service.get("telephony");
        Service upper = Service.get("TELEPHONY");
        Service mixed = Service.get("TeLePHoNy");

        assertEquals(lower, upper);
        assertEquals(lower, mixed);
    }

    @Test
    @DisplayName("get() — returns Service equal to matching constant")
    void get_returnsServiceEqualToConstant() {
        assertEquals(Service.Telephony, Service.get("telephony"));
        assertEquals(Service.Routing, Service.get("routing"));
        assertEquals(Service.Users, Service.get("users"));
        assertEquals(Service.Maintenance, Service.get("system"));
        assertEquals(Service.Messaging, Service.get("voicemail"));
    }

    @Test
    @DisplayName("get() — returns Service equal to constant regardless of case")
    void get_caseInsensitiveMatchesConstant() {
        assertEquals(Service.Telephony, Service.get("TELEPHONY"));
        assertEquals(Service.Routing, Service.get("ROUTING"));
        assertEquals(Service.Users, Service.get("USERS"));
    }

    @Test
    @DisplayName("get() — two calls with same name return equal Services")
    void get_twoCallsReturnEqualServices() {
        Service s1 = Service.get("telephony");
        Service s2 = Service.get("telephony");

        assertEquals(s1, s2);
    }

    @Test
    @DisplayName("get() — two calls with same name return different instances")
    void get_twoCallsReturnDifferentInstances() {
        Service s1 = Service.get("telephony");
        Service s2 = Service.get("telephony");

        assertNotSame(s1, s2);
    }

    @Test
    @DisplayName("get() — unknown name returns a non-null Service")
    void get_unknownNameReturnsService() {
        Service unknown = Service.get("unknown-service");

        assertNotNull(unknown);
    }

    @Test
    @DisplayName("get() — unknown name does not equal any known constant")
    void get_unknownNameDoesNotEqualConstant() {
        Service unknown = Service.get("unknown-service");

        assertNotEquals(Service.Telephony, unknown);
        assertNotEquals(Service.Routing, unknown);
        assertNotEquals(Service.Users, unknown);
    }

    // ── equals() ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("equals() — same instance returns true")
    void equals_sameInstanceReturnsTrue() {
        Service s = Service.get("telephony");

        assertEquals(s, s);
    }

    @Test
    @DisplayName("equals() — equal values return true")
    void equals_equalValuesReturnTrue() {
        Service s1 = Service.get("telephony");
        Service s2 = Service.get("telephony");

        assertEquals(s1, s2);
    }

    @Test
    @DisplayName("equals() — different values return false")
    void equals_differentValuesReturnFalse() {
        Service s1 = Service.get("telephony");
        Service s2 = Service.get("routing");

        assertNotEquals(s1, s2);
    }

    @Test
    @DisplayName("equals() — null returns false")
    void equals_nullReturnsFalse() {
        Service s = Service.get("telephony");

        assertNotEquals(null, s);
    }

    @Test
    @DisplayName("equals() — different type returns false")
    void equals_differentTypeReturnsFalse() {
        Service s = Service.get("telephony");

        assertNotEquals("telephony", s);
        assertNotEquals(42, s);
    }

    @Test
    @DisplayName("equals() — constant equals itself")
    void equals_constantEqualsItself() {
        assertEquals(Service.Telephony, Service.Telephony);
        assertEquals(Service.Routing, Service.Routing);
        assertEquals(Service.Users, Service.Users);
    }

    @Test
    @DisplayName("equals() — different constants are not equal")
    void equals_differentConstantsAreNotEqual() {
        assertNotEquals(Service.Telephony, Service.Routing);
        assertNotEquals(Service.Users, Service.Messaging);
        assertNotEquals(Service.Authentication, Service.Sessions);
    }

    // ── hashCode() ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("hashCode() — equal Services have equal hash codes")
    void hashCode_equalServicesHaveEqualHashCodes() {
        Service s1 = Service.get("telephony");
        Service s2 = Service.get("telephony");

        assertEquals(s1.hashCode(), s2.hashCode());
    }

    @Test
    @DisplayName("hashCode() — Service from get() has same hash code as matching constant")
    void hashCode_getMatchesConstantHashCode() {
        assertEquals(Service.Telephony.hashCode(), Service.get("telephony").hashCode());
        assertEquals(Service.Routing.hashCode(), Service.get("routing").hashCode());
        assertEquals(Service.Users.hashCode(), Service.get("users").hashCode());
    }

    @Test
    @DisplayName("hashCode() — consistent across multiple calls")
    void hashCode_isConsistent() {
        Service s = Service.get("telephony");
        int first = s.hashCode();
        int second = s.hashCode();

        assertEquals(first, second);
    }

    // ── Constants ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("constants — all well-known constants are non-null")
    void constants_allNonNull() {
        assertNotNull(Service.Authentication);
        assertNotNull(Service.Sessions);
        assertNotNull(Service.O2G);
        assertNotNull(Service.Subscriptions);
        assertNotNull(Service.EventSummary);
        assertNotNull(Service.Telephony);
        assertNotNull(Service.Users);
        assertNotNull(Service.Routing);
        assertNotNull(Service.Messaging);
        assertNotNull(Service.Maintenance);
        assertNotNull(Service.Directory);
        assertNotNull(Service.CommunicationLog);
        assertNotNull(Service.Analytics);
        assertNotNull(Service.CallCenterAgent);
        assertNotNull(Service.CallCenterPilot);
        assertNotNull(Service.CallCenterManagement);
        assertNotNull(Service.Rsi);
        assertNotNull(Service.Management);
        assertNotNull(Service.UserManagement);
        assertNotNull(Service.CallCenterRealtime);
        assertNotNull(Service.CallCenterStatistics);
    }

    @Test
    @DisplayName("constants — all well-known constants are distinct")
    void constants_allDistinct() {
        Service[] all = {
            Service.Authentication, Service.Sessions, Service.O2G,
            Service.Subscriptions, Service.EventSummary, Service.Telephony,
            Service.Users, Service.Routing, Service.Messaging,
            Service.Maintenance, Service.Directory, Service.CommunicationLog,
            Service.Analytics, Service.CallCenterAgent, Service.CallCenterPilot,
            Service.CallCenterManagement, Service.Rsi, Service.Management,
            Service.UserManagement, Service.CallCenterRealtime,
            Service.CallCenterStatistics
        };

        for (int i = 0; i < all.length; i++) {
            for (int j = i + 1; j < all.length; j++) {
                assertNotEquals(all[i], all[j],
                    "Constants at index " + i + " and " + j + " should be distinct");
            }
        }
    }

    @Test
    @DisplayName("constants — can be used as Map keys correctly")
    void constants_canBeUsedAsMapKeys() {
        java.util.Map<Service, String> map = new java.util.HashMap<>();
        map.put(Service.Telephony, "telephony-url");
        map.put(Service.Routing, "routing-url");

        assertEquals("telephony-url", map.get(Service.Telephony));
        assertEquals("telephony-url", map.get(Service.get("telephony")));
        assertEquals("routing-url", map.get(Service.Routing));
        assertNull(map.get(Service.Users));
    }
}