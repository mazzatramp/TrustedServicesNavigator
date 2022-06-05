package com.trustedservices.domain;

import com.trustedservices.Help;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("I create a Service")
public class ServiceTest {
    Service service1;

    @BeforeEach
    @Test
    @DisplayName("is instantiated thanks to new Service(int,String, String,String,List<ServiceType>)")
    void isInstantiatedWithNewService() {
        Provider provider = new Provider(new Country("Italia", "IT"), 0, "TestProvider", "TTT-000-X00");
        service1 = new Service(provider,
                0,
                "TestService",
                "type",
                "status",
                Set.of("ServiceType1", "ServiceType2")
        );
    }

    @Test
    @DisplayName("or alternatively is instantiated thanks to new Service(int,String, String,String,List<ServiceType>)")
    void alternativelyIsInstantiatedWithNewService() {
        Provider provider = Help.getCountryN(0).getProviders().stream().toList().get(0);
        new Service(provider,
                0,
                "TestService",
                "type",
                "status",
                Set.of("ServiceType1", "ServiceType2")
        );
    }

    @DisplayName("and I use the method equals(Object)")
    @Nested
    class Equals {
        Service argumentService;

        @DisplayName("and the two services are the same object, method should return true")
        @Test
        void SameServiceObjectAsArgument() {

            argumentService = service1;

            boolean areServicesTheSame = service1.equals(argumentService);

            assertTrue(areServicesTheSame);
        }

        @DisplayName("and the two services are the same, method should return true")
        @Test
        void SameServiceAsArgument() {
            Provider provider = new Provider(new Country("Italia", "IT"), 0, "TestProvider", "TTT-000-X00");
            argumentService = new Service(provider,
                    0,
                    "TestService",
                    "type",
                    "status",
                    Set.of("ServiceType1", "ServiceType2")
            );

            boolean areServicesTheSame = service1.equals(argumentService);

            assertTrue(areServicesTheSame);
        }

        @DisplayName("with a service null as argument, method should return false")
        @Test
        void NullAsArgument() {

            argumentService = null;

            boolean areServicesTheSame = service1.equals(argumentService);

            assertFalse(areServicesTheSame);
        }

        @DisplayName("and the two services are not the same, method should return false")
        @Test
        void NotSameServiceAsArgument() {
            Provider provider = new Provider(new Country("Italia", "IT"), 0, "TestProvider", "TTT-000-X00");

            argumentService = new Service(provider,
                    1,
                    "TestService2",
                    "type2",
                    "status2",
                    Set.of("ServiceType1")
            );
            boolean areServicesTheSame = service1.equals(argumentService);

            assertFalse(areServicesTheSame);
            ;
        }
    }


    @DisplayName("and I use the method compareTo(Service)")
    @Nested
    class CompareTo {

        Service argumentService;

        @DisplayName("and the two services are the same, method should return 0")
        @Test
        void SameServiceAsArgument() {

            argumentService = service1;

            int comparison = service1.compareTo(argumentService);
            int expectedReturn = 0;

            assertEquals(expectedReturn, comparison);
        }

        @DisplayName("and the argument service is null, method should return a error")
        @Test
        void aServiceCompareToNull() {

            Service argumentService = null;

            assertThrows(NullPointerException.class, () -> service1.compareTo(argumentService));


        }


        @DisplayName("and the argument service is greater, method should return a negative number")
        @Test
        void aServiceCompareToBiggerServiceReturnNegative() {
            Provider provider = new Provider(new Country("Italia", "IT"), 0, "TestProvider", "TTT-000-X00");
            Service service2 = new Service(
                    provider,
                    1,
                    "TestService2",
                    "type2",
                    "status2",
                    Set.of("ServiceType1")
            );
            assertTrue(service1.compareTo(service2) < 0);
        }

        @DisplayName("and the argument service is lower, method should return a positive number")
        @Test
        void aServiceCompareToLowerServiceReturnPositive() {
            Provider provider = new Provider(new Country("Italia", "IT"), 0, "TestProvider", "TTT-000-X00");

            Service service2 = new Service(
                    provider,
                    1,
                    "TestService2",
                    "type2",
                    "status2",
                    Set.of("ServiceType1")
            );
            assertTrue(service2.compareTo(service1) > 0);

        }

    }

    @DisplayName("and I use the method clone, it returns the same service")
    @Test
    void cloneAServiceReturnsSameService() {

        Service serviceToBeCloned = service1;
        Service clonedService = serviceToBeCloned.clone();
        assertEquals(serviceToBeCloned, clonedService);

    }
}

