package com.trustedservices.domain;

import com.trustedservices.Help;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A Provider")

class ProviderTest {
Provider provider;
    @Nested
    @DisplayName("when null")
    class WhenNull {
        @BeforeEach
        void setProviderNull() {
            provider = null;
        }

        @DisplayName("and I use the method clone()")
        @Nested
        class Clone {
            @DisplayName("returns an Error")
            @Test
            void clone_NullProvider_returnsError() {
                //SAREBBE DA METTERE UN ERRORE PIU SPECIFICO NEL
                //METODO CLONE SE VOGLIO CLONARE QUALCOSA DI NULL
                //arrange
                Provider providerToClone = provider;
                //assert
                assertThrows(NullPointerException.class, providerToClone::clone);
                //assertEquals(getWholeList(),clonedList)
            }
        }

        @DisplayName("and I use the method equals(Object)")
        @Nested
        class Equals{
            Provider argumentProvider;
            @DisplayName("with a provider as argument, it returns an Error")
            @Test
            void NullEqualsProvider() {
                argumentProvider = new Provider("IT", 49,
                        "Azienda Zero"
                        ,"VATIT-05018720283",
                        new ArrayList<>(),
                        new ArrayList<>());
                //SAREBBE DA METTERE UN ERRORE PIU SPECIFICO NEL
                //arrange
                //assert
                assertThrows(NullPointerException.class, () -> provider.equals(argumentProvider));
                //assertEquals(getWholeList(),clonedList)
            }
            @DisplayName("with a null provider as argument, it returns error ")
            @Test
            void NullEqualsNull() {
                argumentProvider = null;
                //SAREBBE DA METTERE UN ERRORE PIU SPECIFICO NEL
                //arrange
                //assert
                assertThrows(NullPointerException.class, () -> provider.equals(argumentProvider));
                //assertEquals(getWholeList(),clonedList)
            }


        }
        @DisplayName("and I use the method compareTo(Country)")
        @Nested
        class CompareTo{
            Provider argumentProvider;
            @DisplayName("with a provider as argument, it returns an Error")
            @Test
            void NullCompareToProvider() {
                argumentProvider = new Provider("IT", 49,
                        "Azienda Zero"
                        ,"VATIT-05018720283", new ArrayList<String>(), new ArrayList<Service>());
                //SAREBBE DA METTERE UN ERRORE PIU SPECIFICO NEL
                //arrange
                //assert
                assertThrows(NullPointerException.class, () -> provider.compareTo(argumentProvider));
                //assertEquals(getWholeList(),clonedList)
            }
            @DisplayName("with a null provider as argument, it returns error ")
            @Test
            void NullCompareToNull() {
                argumentProvider = null;
                //SAREBBE DA METTERE UN ERRORE PIU SPECIFICO NEL
                //arrange
                //assert
                assertThrows(NullPointerException.class, () -> provider.compareTo(argumentProvider));
                //assertEquals(getWholeList(),clonedList)
            }
        }

    }
    @Test
    @DisplayName("is instantiated with new Provider(String,Int, String,String,List,List)")
    void isInstantiatedWithNewProvider() {
        //DEVO METTERE DELLE VERE LISTE
        //O PROVARE A METTERE LISTE PIENE E LISTE VUOTE IN UN ALTRO CASO
        List<String> listOfServiceTypesOfAziendaZero= new ArrayList<String>();
        listOfServiceTypesOfAziendaZero.add("QCertESeal");
        listOfServiceTypesOfAziendaZero.add("QCertESig");
        listOfServiceTypesOfAziendaZero.add("QTimestamp");
        List<Service> listOfServicesOfAziendaZero= new ArrayList<Service>();
        listOfServicesOfAziendaZero.add(new Service(
                1,
                "OID.2.5.4.97=VATIT-05018720283, CN=Azienda Zero CA Qualificata eIDAS 1, OU=TSP, O=Azienda Zero, C=IT",
                "http://uri.etsi.org/TrstSvc/Svctype/CA/QC",
                "http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/withdrawn",
                listOfServiceTypesOfAziendaZero));
        //Azienda zero ha anche un altro servizio ma non lo metto adesso
        provider = new Provider("IT", 49,
                "Azienda Zero"
                ,"VATIT-05018720283",  listOfServiceTypesOfAziendaZero,  listOfServicesOfAziendaZero );
    }
    @Nested
    @DisplayName("when new")
    class WhenNew{

        @BeforeEach
        void createAProvider() {
            List<String> listOfServiceTypesOfAziendaZero= new ArrayList<String>();
            listOfServiceTypesOfAziendaZero.add("QCertESeal");
            listOfServiceTypesOfAziendaZero.add("QCertESig");
            listOfServiceTypesOfAziendaZero.add("QTimestamp");
            List<Service> listOfServicesOfAziendaZero= new ArrayList<Service>();
            listOfServicesOfAziendaZero.add(new Service(
                    1,
                    "OID.2.5.4.97=VATIT-05018720283, CN=Azienda Zero CA Qualificata eIDAS 1, OU=TSP, O=Azienda Zero, C=IT",
                    "http://uri.etsi.org/TrstSvc/Svctype/CA/QC",
                    "http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/withdrawn",
                    listOfServiceTypesOfAziendaZero));
            //Azienda zero ha anche un altro servizio ma non lo metto adesso
            provider = new Provider("IT", 49,
                    "Azienda Zero"
                    ,"VATIT-05018720283",  listOfServiceTypesOfAziendaZero,  listOfServicesOfAziendaZero );
        }



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
                        //arrange
                        argumentProvider = provider;
                        //act
                        boolean areCountriesTHeSame = provider.equals(argumentProvider);
                        //assert
                        assertTrue(areCountriesTHeSame);
                    }

                    @DisplayName("and the two providers are not the same, method should return false")
                    @Test
                    void NotSameCountryAsArgument() {
                        //arrange
                        //DEVO METTERE UN PROVIDER VERO E PROPRIO
                        List<String> listOfServiceTypesOfAziendaZero= new ArrayList<String>();
                        listOfServiceTypesOfAziendaZero.add("QCertESeal");
                        listOfServiceTypesOfAziendaZero.add("QCertESig");
                        listOfServiceTypesOfAziendaZero.add("QTimestamp");
                        List<Service> listOfServicesOfAziendaZero= new ArrayList<Service>();
                        listOfServicesOfAziendaZero.add(new Service(
                                2,
                                "OID.2.5.4.97=VATIT-05018720283, CN=Azienda Zero CA Qualificata eIDAS 1, OU=TSP, O=Azienda Zero, C=IT",
                                "http://uri.etsi.org/TrstSvc/Svctype/CA/QC",
                                "http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/withdrawn",
                                listOfServiceTypesOfAziendaZero));
                        //Azienda zero ha anche un altro servizio ma non lo metto adesso
                        provider = new Provider("IT", 49,
                                "Azienda Zero"
                                ,"VATIT-05018720283",  listOfServiceTypesOfAziendaZero,  listOfServicesOfAziendaZero );

                        //act
                        boolean areCountriesTHeSame = provider.equals(argumentProvider);
                        //assert
                        assertFalse(areCountriesTHeSame);
                    }
                    @DisplayName("with a provider null as argument, method should return false")
                    @Test
                    void NullAsArgument() {
                        //arrange
                        argumentProvider = null;
                        //act
                        boolean areCountriesTHeSame = provider.equals(argumentProvider);
                        //assert
                        assertFalse(areCountriesTHeSame);
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
                    void aCountry_CompareTo_BiggerCountry_ReturnNegative() throws IOException {
                        //arrange
                        //METTERNE UNO CON PIU'SENSO
                        argumentProvider = Help.getCountryN(1).getProviders().get(1);
                        //act
                        int comparison = provider.compareTo(argumentProvider);
                        //assert
                        assertTrue(comparison < 0);

                    }
                    @DisplayName("and the argument provider is lower, method should return a positive number")
                    @Test
                    void aCountry_CompareTo_LowerCountry_ReturnNegative() throws IOException {
                        //arrange
                        argumentProvider =provider;
                        provider = Help.getCountryN(1).getProviders().get(1);
                        //act
                        int comparison = provider.compareTo(argumentProvider);
                        //assert
                        assertTrue(comparison > 0);

                    }
                    @DisplayName("and the argument provider is null, method should return a error")
                    @Test
                    void aCountry_CompareTo_null() throws IOException {
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
                    //IL PROBLEMA E' O IN EQUALS O CLONE

                    //arrange
                    Provider providerToBeCloned = Help.getCountryN(0).getProviders().get(2);
                    providerToBeCloned.toString();
                    //act
                    Provider clonedProvider = providerToBeCloned.clone();
/*
                    //boolean areeq = providerToBeCloned.equals(clonedProvider);
                    //assertTrue(areeq);
                    //assert
                    //assertEquals(providerToBeCloned, clonedProvider);
*/
                    clonedProvider.toString(); //SIAMO SICURI CHE FUNZIONI?
                    //assert
                    assertTrue(providerToBeCloned.equals(clonedProvider));

                }

            }

        //DECIDERE SE TESTARE GET E SET
        //EQUALS
        //HASHCODE
        //TOSTRING
        //CLONE
        //COMPARETO
        @Test
        void doesSetServiceTypesWork()
        {
            //preparazione
            Provider providerToTest=provider;

            List<String> listIWantToSetToAziendaZero = new ArrayList<String>();
            listIWantToSetToAziendaZero.add("QCertESeal");
            listIWantToSetToAziendaZero.add("QCertESig");
            listIWantToSetToAziendaZero.add("QTimestamp");
            listIWantToSetToAziendaZero.add("QWAC");

            List<String> listThatShouldBeEqualToServiceTypesOfAziendaZero= new ArrayList<>();
            listThatShouldBeEqualToServiceTypesOfAziendaZero.add("QCertESeal");
            listThatShouldBeEqualToServiceTypesOfAziendaZero.add("QCertESig");
            listThatShouldBeEqualToServiceTypesOfAziendaZero.add("QTimestamp");
            listThatShouldBeEqualToServiceTypesOfAziendaZero.add("QWAC");

            //metodo che voglio testare
            providerToTest.setServiceTypes(listIWantToSetToAziendaZero);

            //test
            assertLinesMatch(listThatShouldBeEqualToServiceTypesOfAziendaZero,
                    providerToTest.getServiceTypes());
        }
    }


}