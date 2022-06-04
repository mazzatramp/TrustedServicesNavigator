package com.trustedservices.domain;

import com.trustedservices.Help;
import org.junit.jupiter.api.*;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A Service")
public class ServiceTest {
    Service service1;

    @Test
    @DisplayName("is instantiated with new Service(int,String, String,String,List<ServiceType>)")
    void isInstantiatedWithNewService() {
        new Service(
                0,
                "TestService",
                "type",
                "status",
                Set.of("ServiceType1", "ServiceType2")
        );
    }

    @Nested
    @DisplayName("when new")
    class WhenNew {

        @BeforeEach
        void createAService() {
            service1 = new Service(
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

            @DisplayName("and the two services are the same, method should return true")
            @Test
            void SameServiceAsArgument() {

                argumentService = service1;

                boolean areCountriesTheSame = service1.equals(argumentService);

                assertTrue(areCountriesTheSame);
            }
            /*
            @DisplayName("and the two services are not the same, method should return false")
            @Test
            void NotSameServiceAsArgument() {

                //DEVO METTERE UN Service VERO E PROPRIO
                argumentService = Help.getWholeList().getCountries().stream().findFirst().get()
                        .getProviders().stream().findFirst().get()
                        .getServices().stream().findFirst().get();


                boolean areCountriesTHeSame = service1.equals(argumentService);

                assertFalse(areCountriesTHeSame);
            }
            */
            @DisplayName("with a service null as argument, method should return false")
            @Test
            void NullAsArgument() {

                argumentService = null;

                boolean areCountriesTHeSame = service1.equals(argumentService);

                assertFalse(areCountriesTHeSame);
            }

            @DisplayName("and the two services are not the same, method should return false")
            @Test
            void NotSameServiceAsArgument() {
                Service service2 = new Service(
                        1,
                        "TestService2",
                        "type2",
                        "status2",
                        Set.of("ServiceType1")
                );
                assertNotEquals(service1, service2);
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
/*
            //COUNTRY MAGGIORE
            //DEVO METTERE ANCHE DIFFERENZA O BASTA POS E NEG?
            @DisplayName("and the argument service is greater, method should return a negative number")
            @Test
            void aCountry_CompareTo_BiggerCountry_ReturnNegative() {

                //METTERNE UNO CON PIU'SENSO
                argumentService = Help.getWholeList().getCountries().stream().findFirst().get()
                        .getProviders().stream().findFirst().get()
                        .getServices().stream().findFirst().get();

                int comparison = service1.compareTo(argumentService);

                assertTrue(comparison < 0);

            }

            @DisplayName("and the argument service is lower, method should return a positive number")
            @Test
            void aCountry_CompareTo_LowerCountry_ReturnNegative() {

                argumentService =service1;
                argumentService = Help.getWholeList().getCountries().stream().findFirst().get()
                        .getProviders().stream().findFirst().get()
                        .getServices().stream().findFirst().get();

                int comparison = service1.compareTo(argumentService);

                assertTrue(comparison > 0);

            }
            */

            @DisplayName("and the argument service is null, method should return a error")
            @Test
            void aServiceCompareToNull() {

                Service argumentService = null;

                assertThrows(NullPointerException.class, () -> service1.compareTo(argumentService));


            }


            @DisplayName("and the argument service is greater, method should return a negative number")
            @Test
            void aServiceCompareToBiggerServiceReturnNegative() {
                Service service2 = new Service(
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
            void aServiceCompareToLowerServiceReturnNegative() {
                Service service2 = new Service(
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
/*
            void cloneACountryReturnsSameService() {
                Service serviceToBeCloned = Help.getCountryN(0).getProviders().get(0).getServices().get(0);
*/
            void cloneAServiceReturnsSameService() {

                Service serviceToBeCloned = Help.getWholeList().getCountries().stream().findFirst().get()
                        .getProviders().stream().findFirst().get()
                        .getServices().stream().findFirst().get();


                Service clonedService = serviceToBeCloned.clone();
                assertEquals(serviceToBeCloned, clonedService);

        }
    }
}

