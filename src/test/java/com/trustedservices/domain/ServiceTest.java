package com.trustedservices.domain;

import com.trustedservices.Help;
import com.trustedservices.navigator.filters.ServiceTypeFilter;
import org.junit.jupiter.api.*;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A Service")
public class ServiceTest {
    Service service1;

    @Nested
    @DisplayName("when null")
    class WhenNull {
        @BeforeEach
        void setServiceNull() {
            service1 = null;
        }

        @DisplayName("cloning the object should return an error.")
        @Test
        void clone_NullService_returnsError() {
            assertThrows(NullPointerException.class, service1::clone);
        }
    }

    @Nested
    @DisplayName("when new")
    class WhenNew{

        @BeforeEach
        void createAService() {
            service1 = new Service(
                    0,
                    "TestService",
                    "type",
                    "status",
                    List.of("ServiceType1", "ServiceType2")
            );
        }

        @DisplayName("and I use the method equals(Object)")
        @Nested
        class Equals {

            @DisplayName("and the two services are the same, method should return true")
            @Test
            void SameCountryAsArgument() {
                assertEquals(service1, service1);
            }

            @DisplayName("and the two services are not the same, method should return false")
            @Test
            void NotSameCountryAsArgument()  {
                Service service2 = new Service(
                        1,
                        "TestService2",
                        "type2",
                        "status2",
                        List.of("ServiceType1")
                );
                assertNotEquals(service1, service2);
            }

            @DisplayName("with a service null as argument, method should return false")
            @Test
            void NullAsArgument() {
                assertNotEquals(service1, null);
            }
        }

        @DisplayName("and I use the method compareTo(Service)")
        @Nested
        class CompareTo {
            @DisplayName("and the two services are the same, method should return 0")
            @Test
            void SameCountryAsArgument() {
                assertEquals(0, service1.compareTo(service1));
            }

            @DisplayName("and the argument service is greater, method should return a negative number")
            @Test
            void aCountry_CompareTo_BiggerCountry_ReturnNegative() {
                Service service2 = new Service(
                        1,
                        "TestService2",
                        "type2",
                        "status2",
                        List.of("ServiceType1")
                );
                assertTrue(service1.compareTo(service2) > 0);
            }

            @DisplayName("and the argument service is lower, method should return a positive number")
            @Test
            void aCountry_CompareTo_LowerCountry_ReturnNegative() {
                Service service2 = new Service(
                        1,
                        "TestService2",
                        "type2",
                        "status2",
                        List.of("ServiceType1")
                );
                assertTrue(service2.compareTo(service1) < 0);

            }
            @DisplayName("and the argument service is null, method should return a error")
            @Test
            void aCountry_CompareTo_null() {
                assertNotEquals(0, service1.compareTo(null));
            }
        }

        @DisplayName("and I use the method clone()")
        @Nested
        class Clone {
            @DisplayName("It returns the same service")
            @Test
            void cloneACountryReturnsSameService() {
                Service serviceToBeCloned = Help.getCountryN(0).getProviders().get(0).getServices().get(0);
                Service clonedService = serviceToBeCloned.clone();
                assertEquals(serviceToBeCloned, clonedService);
            }
        }
    }
}
