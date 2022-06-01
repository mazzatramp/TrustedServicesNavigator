package com.trustedservices.domain;

import com.trustedservices.Help;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

//SAREBBE BELLO ANCHE METTERE DEI TIMEOUT NEI TEST

@DisplayName("A Service")
public class ServiceTest {
    Service service;
    @Nested
    @DisplayName("when null")
    class WhenNull {
        @BeforeEach
        void setServiceNull() {
            service = null;
        }
        @DisplayName("and I use the method clone()")
        @Nested
        class Clone {
            @DisplayName("returns an Error")
            @Test
            void clone_NullService_returnsError() {
                //SAREBBE DA METTERE UN ERRORE PIU SPECIFICO NEL
                //METODO CLONE SE VOGLIO CLONARE QUALCOSA DI NULL
                //arrange
                Service serviceToClone = service;
                //assert
                assertThrows(NullPointerException.class, () -> serviceToClone.clone());
                //assertEquals(getWholeList(),clonedList)
            }
        }

        @DisplayName("and I use the method equals(Object)")
        @Nested
        class Equals{
            Service argumentService;
            @DisplayName("with a service as argument, it returns an Error")
            @Test
            void NullEqualsService() throws IOException {
                argumentService = Help.getWholeList().getCountries().get(0).getProviders().get(0).getServices().get(1); //SAREBBE DA METTERE UN ERRORE PIU SPECIFICO NEL
                //arrange
                //assert
                assertThrows(NullPointerException.class, () -> service.equals(argumentService));
                //assertEquals(getWholeList(),clonedList)
            }
            @DisplayName("with a null service as argument, it returns error ")
            @Test
            void NullEqualsNull() {
                argumentService = null;
                //SAREBBE DA METTERE UN ERRORE PIU SPECIFICO NEL
                //arrange
                //assert
                assertThrows(NullPointerException.class, () -> service.equals(argumentService));
                //assertEquals(getWholeList(),clonedList)
            }


        }
    }
    @Disabled
    @Test
    @DisplayName("is instantiated with new costruttoreOggetto")
    void isInstantiatedWithNewClasse() {
        //new Service(argomenti)
    }
    @Nested
    @DisplayName("when new")
    class WhenNew{

        @BeforeEach
        void createAService() throws IOException {
            //VA CREATO CON IL COSTRUTTORE NON COSI
            service = Help.getWholeList().getCountries().get(0).getProviders().get(0).getServices().get(1); //SAREBBE DA METTERE UN ERRORE PIU SPECIFICO NEL

        }

        @DisplayName("and I use the method equals(Object)")
        @Nested
        class Equals {
            @DisplayName("with a service as argument")
            @Nested
            class CountryAsArgument {
                Service argumentService;

                @DisplayName("and the two services are the same, method should return true")
                @Test
                void SameCountryAsArgument() {
                    //arrange
                    argumentService = service;
                    //act
                    boolean areCountriesTHeSame = service.equals(argumentService);
                    //assert
                    assertTrue(areCountriesTHeSame);
                }

                @DisplayName("and the two services are not the same, method should return false")
                @Test


                void NotSameCountryAsArgument() throws IOException {
                    //arrange
                    //DEVO METTERE UN Service VERO E PROPRIO
                    argumentService = Help.getWholeList().getCountries().get(2).getProviders().get(0).getServices().get(1); //SAREBBE DA METTERE UN ERRORE PIU SPECIFICO NEL

                    //act
                    boolean areCountriesTHeSame = service.equals(argumentService);
                    //assert
                    assertFalse(areCountriesTHeSame);
                }
                @DisplayName("with a service null as argument, method should return false")
                @Test
                void NullAsArgument() {
                    //arrange
                    argumentService = null;
                    //act
                    boolean areCountriesTHeSame = service.equals(argumentService);
                    //assert
                    assertFalse(areCountriesTHeSame);
                }
            }

            /*
            DOVREI FARLO CON STESSI VALORI MA OGGETTO DIVERSO, NON PENSO NEL CASO PRIMA FOSSE COSI
            */
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
                void SameCountryAsArgument() throws IOException {
                    //arrange
                    argumentService = service;
                    //act
                    int comparison = service.compareTo(argumentService);
                    int expectedreturn = 0;
                    //assert
                    assertEquals(expectedreturn, comparison);
                }

                //COUNTRY MAGGIORE
                //DEVO METTERE ANCHE DIFFERENZA O BASTA POS E NEG?
                @DisplayName("and the argument service is greater, method should return a negative number")
                @Test
                void aCountry_CompareTo_BiggerCountry_ReturnNegative() throws IOException {
                    //arrange
                    //METTERNE UNO CON PIU'SENSO
                    argumentService = Help.getCountryN(4).getProviders().get(0).getServices().get(5);
                    //act
                    int comparison = service.compareTo(argumentService);
                    //assert
                    assertTrue(comparison < 0);

                }
                @DisplayName("and the argument service is lower, method should return a positive number")
                @Test
                void aCountry_CompareTo_LowerCountry_ReturnNegative() throws IOException {
                    //arrange
                    argumentService =service;
                    service = Help.getCountryN(4).getProviders().get(0).getServices().get(5);
                    //act
                    int comparison = service.compareTo(argumentService);
                    //assert
                    assertTrue(comparison > 0);

                }
                @DisplayName("and the argument service is null, method should return a error")
                @Test
                void aCountry_CompareTo_null() throws IOException {
                    //arrange
                    Service argumentService = null;
                    //act
                    assertThrows(NullPointerException.class, () -> service.compareTo(argumentService));


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
            @DisplayName("It returns the same service")
            @Test
            void cloneACountryReturnsSameService() throws IOException {
                //arrange
                Service serviceToBeCloned = Help.getCountryN(0).getProviders().get(0).getServices().get(0);
                //act
                Service clonedService = serviceToBeCloned.clone();
                //assert
                assertEquals(serviceToBeCloned, clonedService);
            }

        }

        //DECIDERE SE TESTARE GET E SET
        //EQUALS
        //HASHCODE
        //TOSTRING
        //CLONE
        //COMPARETO

    }
}
