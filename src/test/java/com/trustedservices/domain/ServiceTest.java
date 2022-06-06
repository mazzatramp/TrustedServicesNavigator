package com.trustedservices.domain;

import org.junit.jupiter.api.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("I create a Service")
public class ServiceTest {
    Service service1;

    @BeforeEach
    @DisplayName("is instantiated thanks to new Service(int,String, String,String,Set<ServiceType>)")
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
    @DisplayName("or alternatively is instantiated thanks to new Service(int,String, String,String)")
    void alternativelyIsInstantiatedWithOtherNewService() {
        Provider provider = new Provider(new Country("Italia", "IT"), 0, "TestProvider", "TTT-000-X00");
        new Service(provider,
                0,
                "TestService",
                "type",
                "status"
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
            argumentService = getService1();

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
            argumentService = getService2();

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

            argumentService = getService1();

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
            argumentService = getService2();
            assertTrue(service1.compareTo(argumentService) < 0);
        }

        @DisplayName("and the argument service is lower, method should return a positive number")
        @Test
        void aServiceCompareToLowerServiceReturnPositive() {
            argumentService = getService2();
            assertTrue(argumentService.compareTo(service1) > 0);

        }

    }

    @DisplayName("and I use the method clone, it returns the same service")
    @Test
    void cloneAServiceReturnsSameService() {

        Service serviceToBeCloned = service1;
        Service clonedService = serviceToBeCloned.clone();
        assertEquals(serviceToBeCloned, clonedService);

    }

    @DisplayName("when I use the method getHumanInformation, it should return a string with information")
    @Test
    void getHumanInformationMethod() {
        String expectedString = "TestService" + "\n" +
                "Of " + "TestProvider" + " (" + "IT" + ")\n" +
                "\n" +
                "Status: " + "status" + "\n" +
                "Service Types: " + Set.of("ServiceType1", "ServiceType2");
        assertEquals(expectedString, service1.getDescription());

    }

    @DisplayName("when I use the method toString, it should return tspId,serviceId, name,type,status, serviceTypes")
    @Test
    void toStringMethod() {
        String expectedString = "Service{" +
                "tspId=" + "0" +
                ", serviceId=" + 0 +
                ", name='" + "TestService" + '\'' +
                ", type='" + "type" + '\'' +
                ", status='" + "status" + '\'' +
                ", serviceTypes=" + Set.of("ServiceType1", "ServiceType2") +
                '}';
        assertEquals(expectedString, service1.toString());

    }

    private Service getService1() {
        Provider provider = new Provider(new Country("Italia", "IT"), 0, "TestProvider", "TTT-000-X00");
        Service service = new Service(provider,
                0,
                "TestService",
                "type",
                "status",
                Set.of("ServiceType1", "ServiceType2")
        );
        return service;

    }

    private Service getService2() {
        Provider provider = new Provider(new Country("Italia", "IT"), 0, "TestProvider", "TTT-000-X00");

        Service service = new Service(provider,
                1,
                "TestService2",
                "type2",
                "status2",
                Set.of("ServiceType1")
        );
        return service;
    }
}

