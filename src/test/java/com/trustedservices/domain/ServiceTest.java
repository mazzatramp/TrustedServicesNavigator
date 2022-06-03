package com.trustedservices.domain;

import com.trustedservices.Help;
import com.trustedservices.navigator.filters.ServiceTypeFilter;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A Service")
public class ServiceTest {
    Service service1;



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
                    Set.of("ServiceType1", "ServiceType2")
            );
        }
            /* void createAService() throws IOException {
            //VA CREATO CON IL COSTRUTTORE NON COSI
            service = Help.getWholeList().getCountries().stream().findFirst().get()
                    .getProviders().stream().findFirst().get()
                    .getServices().stream().findFirst().get();


        }*/

        @DisplayName("and I use the method equals(Object)")
        @Nested
        class Equals {
             Service argumentService;

                @DisplayName("and the two services are the same, method should return true")
                @Test
                void SameCountryAsArgument() {
                    //arrange
                    argumentService = service1;
                    //act
                    boolean areCountriesTHeSame = service1.equals(argumentService);
                    //assert
                    assertTrue(areCountriesTHeSame);
                }

                @DisplayName("and the two services are not the same, method should return false")
                @Test


                void NotSameCountryAsArgument()  {
                    //arrange
                    //DEVO METTERE UN Service VERO E PROPRIO
                    argumentService = Help.getWholeList().getCountries().stream().findFirst().get()
                            .getProviders().stream().findFirst().get()
                            .getServices().stream().findFirst().get();

                    //act
                    boolean areCountriesTHeSame = service1.equals(argumentService);
                    //assert
                    assertFalse(areCountriesTHeSame);
                }
                @DisplayName("with a service null as argument, method should return false")
                @Test
                void NullAsArgument() {
                    //arrange
                    argumentService = null;
                    //act
                    boolean areCountriesTHeSame = service1.equals(argumentService);
                    //assert
                    assertFalse(areCountriesTHeSame);
                }

            }

            @DisplayName("and the two services are not the same, method should return false")
            @Test
            void NotSameCountryAsArgument()  {
                Service service2 = new Service(
                        1,
                        "TestService2",
                        "type2",
                        "status2",
                        Set.of("ServiceType1")
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

            @DisplayName("with a service as argument")
            @Nested
            class CountryAsArgument {
                Service argumentService;

                @DisplayName("and the two services are the same, method should return 0")
                @Test
                void SameCountryAsArgument() {
                    //arrange
                    argumentService = service1;
                    //act
                    int comparison = service1.compareTo(argumentService);
                    int expectedreturn = 0;
                    //assert
                    assertEquals(expectedreturn, comparison);
                }

                //COUNTRY MAGGIORE
                //DEVO METTERE ANCHE DIFFERENZA O BASTA POS E NEG?
                @DisplayName("and the argument service is greater, method should return a negative number")
                @Test
                void aCountry_CompareTo_BiggerCountry_ReturnNegative() {
                    //arrange
                    //METTERNE UNO CON PIU'SENSO
                    argumentService = Help.getWholeList().getCountries().stream().findFirst().get()
                            .getProviders().stream().findFirst().get()
                            .getServices().stream().findFirst().get();
                    //act
                    int comparison = service1.compareTo(argumentService);
                    //assert
                    assertTrue(comparison < 0);

                }
                @DisplayName("and the argument service is lower, method should return a positive number")
                @Test
                void aCountry_CompareTo_LowerCountry_ReturnNegative() {
                    //arrange
                    argumentService =service1;
                    argumentService = Help.getWholeList().getCountries().stream().findFirst().get()
                            .getProviders().stream().findFirst().get()
                            .getServices().stream().findFirst().get();
                    //act
                    int comparison = service1.compareTo(argumentService);
                    //assert
                    assertTrue(comparison > 0);

                }
                @DisplayName("and the argument service is null, method should return a error")
                @Test
                void aCountry_CompareTo_null() {
                    //arrange
                    Service argumentService = null;
                    //act
                    assertThrows(NullPointerException.class, () -> service1.compareTo(argumentService));


                }

            }

            @DisplayName("and the argument service is greater, method should return a negative number")
            @Test
            void aCountry_CompareTo_BiggerCountry_ReturnNegative() {
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
            void aCountry_CompareTo_LowerCountry_ReturnNegative() {
                Service service2 = new Service(
                        1,
                        "TestService2",
                        "type2",
                        "status2",
                        Set.of("ServiceType1")
                );
                assertTrue(service2.compareTo(service1) > 0);

            }
            @DisplayName("and the argument service is null, method should return a error")
            @Test
            void aCountry_CompareTo_null() {
                assertThrows(NullPointerException.class, () -> service1.compareTo(null));
            }
        }

        @DisplayName("and I use the method clone()")
        @Nested
        class Clone {
            @DisplayName("It returns the same service")
            @Test
/*
            void cloneACountryReturnsSameService() {
                Service serviceToBeCloned = Help.getCountryN(0).getProviders().get(0).getServices().get(0);
*/
            void cloneACountryReturnsSameService() {
                //arrange
                Service serviceToBeCloned = Help.getWholeList().getCountries().stream().findFirst().get()
                        .getProviders().stream().findFirst().get()
                        .getServices().stream().findFirst().get();
                //act

                Service clonedService = serviceToBeCloned.clone();
                assertEquals(serviceToBeCloned, clonedService);
            }
        }
    }

