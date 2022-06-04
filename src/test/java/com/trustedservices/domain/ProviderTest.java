package com.trustedservices.domain;

import org.junit.jupiter.api.*;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A Provider")

class ProviderTest {
Provider provider;

    @Test
    @DisplayName("is instantiated with new Provider(String,Int, String,String,List<ServiceType>,List<providerServices>)")
    void isInstantiatedWithNewProvider() {
        TreeSet<String> providerServiceTypes = new TreeSet<>(
                List.of("QCertESeal", "QCertESig", "QTimestamp")
        );

        TreeSet<Service> providerServices = new TreeSet<>();

        provider = new Provider(0, "TestProvider", "TTT-000-X01", providerServiceTypes, providerServices);

        providerServices.add(new Service(1, "Service 1", "QC", "granted", providerServiceTypes));
        providerServices.forEach(service -> service.setProvider(provider));
    }

    @Nested
    @DisplayName("when new")
    class WhenNew{
        @BeforeEach
        void createProvider() {
            TreeSet<String> providerServiceTypes = new TreeSet<>(
                    List.of("QCertESeal", "QCertESig", "QTimestamp")
            );

            TreeSet<Service> providerServices = new TreeSet<>();

            provider = new Provider(0, "TestProvider", "TTT-000-X01", providerServiceTypes, providerServices);

            providerServices.add(new Service(1, "Service 1", "QC", "granted", providerServiceTypes));
            providerServices.forEach(service -> service.setProvider(provider));
        }

        @DisplayName("and I use the method equals(Object)")
        @Nested
        class Equals {

            Provider argumentProvider;

                @DisplayName("and the two providers are the same, method should return true")
                @Test
                void SameProviderAsArgument() {
                    TreeSet<String> providerServiceTypes = new TreeSet<>(
                            List.of("QCertESeal", "QCertESig", "QTimestamp")
                    );

                    TreeSet<Service> providerServices = new TreeSet<>();

                    argumentProvider = new Provider(0, "TestProvider", "TTT-000-X01", providerServiceTypes, providerServices);

                    providerServices.add(new Service(1, "Service 1", "QC", "granted", providerServiceTypes));
                    providerServices.forEach(service -> service.setProvider(provider));

                    assertEquals(provider, argumentProvider);
                }

                @DisplayName("and the two providers are not the same, method should return false")
                @Test
                void NotSameProviderAsArgument() {
                    TreeSet<String> providerServiceTypes = new TreeSet<>(
                            List.of("QCertESeal", "QCertESig", "QTimestamp")
                    );

                    TreeSet<Service> providerServices = new TreeSet<>();

                    argumentProvider = new Provider(0, "TestProvider2", "TTT-000-X02", providerServiceTypes, providerServices);

                    providerServices.add(new Service(1, "Service 1", "QC", "granted", providerServiceTypes));
                    providerServices.forEach(service -> service.setProvider(provider));

                    assertNotEquals(provider, argumentProvider);
                }
            @DisplayName("with a provider null as argument, method should return false")
            @Test
            void NullAsArgument() {

                argumentProvider = null;

                boolean areProvidersTheSame = provider.equals(argumentProvider);

                assertFalse(areProvidersTheSame);
            }
        /*
        DOVREI FARLO CON STESSI VALORI MA OGGETTO DIVERSO, NON PENSO NEL CASO PRIMA FOSSE COSI
        */
        }

        @DisplayName("and I use the method compareTo(Provider)")
        @Nested
        class CompareTo {

                Provider argumentProvider;

                @DisplayName("and the two providers are the same, method should return 0")
                @Test

                void SameProviderAsArgument() {

                    argumentProvider = provider;

                    int comparison = provider.compareTo(argumentProvider);
                    int expectedReturn = 0;
                    //assert
                    assertEquals(expectedReturn, comparison);

                }

                @DisplayName("and the argument provider is greater, method should return a negative number")
                @Test
                void aProviderCompareToBiggerProviderReturnNegative() {

                    TreeSet<String> providerServiceTypes = new TreeSet<>(
                            List.of("QCertESeal", "QCertESig", "QTimestamp")
                    );

                    TreeSet<Service> providerServices = new TreeSet<>();

                    argumentProvider = new Provider(0, "TestProvider2", "TTT-000-X02", providerServiceTypes, providerServices);

                    providerServices.add(new Service(1, "Service 1", "QC", "granted", providerServiceTypes));
                    providerServices.forEach(service -> service.setProvider(provider));

                    int comparison = provider.compareTo(argumentProvider);

                    assertTrue(comparison < 0);

                }
                @DisplayName("and the argument provider is lower, method should return a positive number")
                @Test
                void aProviderCompareToLowerProviderReturnPositive() {

                    TreeSet<String> providerServiceTypes = new TreeSet<>(
                            List.of("QCertESeal", "QCertESig", "QTimestamp")
                    );

                    TreeSet<Service> providerServices = new TreeSet<>();

                    argumentProvider = new Provider(0, "TestProvider", "TTT-000-X00", providerServiceTypes, providerServices);

                    providerServices.add(new Service(1, "Service 1", "QC", "granted", providerServiceTypes));
                    providerServices.forEach(service -> service.setProvider(provider));

                    int comparison = provider.compareTo(argumentProvider);

                    assertTrue(comparison > 0);

                }
                @DisplayName("and the argument provider is null, method should return NullPointerException")
                @Test
                void aProviderCompareToNullReturnException() {
                    Provider argumentProvider = null;

                    assertThrows(NullPointerException.class, () -> provider.compareTo(argumentProvider));
                }


            //COUNTRY MINORE
            //NULL A SX FATTO
            //NULL A DX FATTO
            //NULL NULLL FATTO


            //NON SO SE FARLI QUESTI PERCHE ESISTONO SET MA NON LI USIAMO MAI
            //EMPTY A SX
            //EMPTY A DX NON INTERESSANTE
            //EMPTY EMPTY
        }

        @DisplayName("and I use the method clone, it returns the same provider")

            @Test
            void cloneAProviderReturnsSameProvider() throws IOException {
                //arrange
                Provider providerToBeCloned = provider;
                //act
                Provider clonedProvider = providerToBeCloned.clone();
                //assert
                assertEquals(providerToBeCloned, clonedProvider);


        }

        //DECIDERE SE TESTARE GET E SET
        //EQUALS
        //HASHCODE
        //TOSTRING
        //CLONE
        //COMPARETO
        @Test
        @Disabled
        void doesSetServiceTypesWork()
        {
            //preparazione
            Provider providerToTest=provider;

            Set<String> listIWantToSetToAziendaZero = new TreeSet<>();
            listIWantToSetToAziendaZero.add("QCertESeal");
            listIWantToSetToAziendaZero.add("QCertESig");
            listIWantToSetToAziendaZero.add("QTimestamp");
            listIWantToSetToAziendaZero.add("QWAC");

            Set<String> listThatShouldBeEqualToServiceTypesOfAziendaZero= new TreeSet<>();
            listThatShouldBeEqualToServiceTypesOfAziendaZero.add("QCertESeal");
            listThatShouldBeEqualToServiceTypesOfAziendaZero.add("QCertESig");
            listThatShouldBeEqualToServiceTypesOfAziendaZero.add("QTimestamp");
            listThatShouldBeEqualToServiceTypesOfAziendaZero.add("QWAC");

            //test
            assertLinesMatch(
                    listThatShouldBeEqualToServiceTypesOfAziendaZero.stream().toList(),
                    providerToTest.getServiceTypes().stream().toList()
            );
        }
    }


}