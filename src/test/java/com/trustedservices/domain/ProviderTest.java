package com.trustedservices.domain;

import com.sun.source.tree.Tree;
import com.trustedservices.Help;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A Provider")

class ProviderTest {
Provider provider;

    @Test
    @DisplayName("is instantiated with new Provider(String,Int, String,String,List,List)")
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

        @DisplayName("and I use the method equals(Object)")
        @Nested
        class Equals {
            @DisplayName("with a provider as argument")
            @Nested
            class CountryAsArgument {
                Provider argumentProvider;

                @DisplayName("and the two providers are the same, method should return true")
                @Test
                void SameCountryAsArgument() {
                    TreeSet<String> providerServiceTypes = new TreeSet<>(
                            List.of("QCertESeal", "QCertESig", "QTimestamp")
                    );

                    TreeSet<Service> providerServices = new TreeSet<>();
                    provider = new Provider(0, "TestProvider", "TTT-000-X01", providerServiceTypes, providerServices);
                    argumentProvider = new Provider(0, "TestProvider", "TTT-000-X01",
                            (TreeSet) providerServiceTypes.clone(), (TreeSet)providerServices.clone());

                    assertEquals(provider, argumentProvider);
                }

                @DisplayName("and the two providers are not the same, method should return false")
                @Test
                void NotSameCountryAsArgument() {
                    TreeSet<String> providerServiceTypes = new TreeSet<>(
                            List.of("QCertESeal", "QCertESig", "QTimestamp")
                    );

                    TreeSet<Service> providerServices = new TreeSet<>();
                    provider = new Provider(0, "TestProvider", "TTT-000-X01", providerServiceTypes, providerServices);
                    argumentProvider = new Provider(1, "TestProvider2", "TTT-000-X02",
                            (TreeSet) providerServiceTypes.clone(), (TreeSet)providerServices.clone());

                    assertNotEquals(provider, argumentProvider);
                }
            }

        /*
        DOVREI FARLO CON STESSI VALORI MA OGGETTO DIVERSO, NON PENSO NEL CASO PRIMA FOSSE COSI
        */
        }

        @DisplayName("and I use the method compareTo(Provider)")
        @Nested
        class CompareTo {
            @DisplayName("with a provider as argument")
            @Nested
            class CountryAsArgument {
                Provider argumentProvider;

                @DisplayName("and the two providers are the same, method should return 0")
                @Test
                void SameCountryAsArgument() throws IOException {
                    //arrange
                    argumentProvider = provider;
                    //act
                    int comparison = provider.compareTo(argumentProvider);
                    int expectedreturn = 0;
                    //assert
                    assertEquals(expectedreturn, comparison);
                }

                //COUNTRY MAGGIORE
                //DEVO METTERE ANCHE DIFFERENZA O BASTA POS E NEG?
                @DisplayName("and the argument provider is greater, method should return a negative number")
                @Test
                void aCountry_CompareTo_BiggerCountry_ReturnNegative() {
                    //arrange
                    //METTERNE UNO CON PIU'SENSO
                    argumentProvider = Help.getCountryN(1).getProviders().stream().findAny().get();
                    //act
                    int comparison = provider.compareTo(argumentProvider);
                    //assert
                    assertTrue(comparison < 0);

                }
                @DisplayName("and the argument provider is lower, method should return a positive number")
                @Test
                void aCountry_CompareTo_LowerCountry_ReturnNegative() {
                    //arrange
                    argumentProvider =provider;
                    provider = Help.getCountryN(1).getProviders().stream().findAny().get();
                    //act
                    int comparison = provider.compareTo(argumentProvider);
                    //assert
                    assertTrue(comparison > 0);

                }
                @DisplayName("and the argument provider is null, method should return a error")
                @Test
                void aCountry_CompareTo_null() {
                    //arrange
                    Provider argumentProvider = null;
                    //act
                    assertThrows(NullPointerException.class, () -> provider.compareTo(argumentProvider));


                }
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

        @DisplayName("and I use the method clone()")
        @Nested
        class Clone {
            @DisplayName("It returns the same provider")
            @Test
            void cloneACountryReturnsSameProvider() throws IOException {
                //arrange
                Provider providerToBeCloned = Help.getCountryN(0).getProviders().stream().findAny().get();
                //act
                Provider clonedProvider = providerToBeCloned.clone();
                //assert
                assertEquals(providerToBeCloned, clonedProvider);
            }

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