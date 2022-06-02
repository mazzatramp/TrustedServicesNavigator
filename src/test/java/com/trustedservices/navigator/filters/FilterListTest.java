package com.trustedservices.navigator.filters;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.trustedservices.Help;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.TrustedList;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

    @DisplayName("A FilterList")
    public class FilterListTest {

        //TESTO SOLO IL METODO GETFILTEREDLIST PERCHE' GLI ALTRI SONO SUPER DA UNA CLASSE CONOSCIUTISSIMA
        FilterList filterList;
        //NUOVA COLLEZIONE CON FILTRI NULLI
        //NUOVA COLLEZIONE CON FILTRI NOT EMPTY AND IMPOSSIBLE
        @Nested
        //IL PROBLEMA E' CHE VOLENDO CI SONO INFINITE COMBINAZIONI DI FILTRI DA METTERE NEL COSTRUTTORE
        @DisplayName("when new with collection of filters")
        class WhenNewWithFilters {
            ArrayList collectionOfFilters;
            @DisplayName("and the filters can link to a service")
            @Nested
            class PossibleFilter {
                @BeforeEach
                void createAFilterList() throws IOException {
                    collectionOfFilters = new ArrayList<>();
                    Set<String> providerSet = new HashSet<>();
                    providerSet.add("PrimeSign GmbH");
                    ProviderFilter filterProvider = new ProviderFilter();
                    filterProvider.setWhitelist(providerSet);
                    Set<String> serviceTypeSet = new HashSet<>();
                    serviceTypeSet.add("QCertESeal");
                    ServiceTypeFilter filterServiceType = new ServiceTypeFilter();
                    filterServiceType.setWhitelist(serviceTypeSet);
                    Set<String> statusSet = new HashSet<>();
                    statusSet.add("granted");
                    StatusFilter filterStatus = new StatusFilter();
                    filterStatus.setWhitelist(statusSet);
                    collectionOfFilters.add(filterProvider);
                    collectionOfFilters.add(filterServiceType);
                    collectionOfFilters.add(filterStatus);

                    filterList = new FilterList(collectionOfFilters);
                }

                @DisplayName("and I use the method getFilteredListFrom")
                @Nested
                class getFilteredListFrom {
                    TrustedList argumentTrustedList; //HA SENSO RIPETERE OGNI VOLTA ARGUMENTTRUSTEDLIST?

                    @DisplayName("a list as argument that contain services possible, should return just the filtered services")
                    @Test
                    void PossibleFiltersTrustedListAsArgument() throws IOException {
                        argumentTrustedList = Help.getWholeList();
                        String providerExpected = "PrimeSign GmbH";
                        String expectedServiceType = "QCertESeal";
                        String expectedStatus = "granted";
                        TrustedList filteredList = filterList.getFilteredListFrom(argumentTrustedList);
                        filteredList.getCountries().forEach(country -> {
                            country.getProviders().forEach(provider -> {
                                System.out.println(provider.getName());
                                assertTrue(provider.getName().equals(providerExpected));
                                provider.getServices().forEach(service -> {
                                    //System.out.println(service.getServiceTypes());
                                    assertTrue(service.getServiceTypes().contains(expectedServiceType));
                                    assertTrue(service.getStatus().equals(expectedStatus));
                                });
                            });
                        });
                    }

                    //LISTA CHE NON CONTIENE I SERVIZI DISPONIBILI

                    @DisplayName("Null TrustedList as argument, should return an error")
                    @Test
                    void NullTrustedListAsArgument() throws IOException {
                        argumentTrustedList = null;
                        //assert
                        assertThrows(NullPointerException.class, () -> filterList.getFilteredListFrom(argumentTrustedList));

                    }







                }
                //LISTA VUOTA
                //LISTA CON UN PO SI UN PO NO



                //POTREI ANCHE METTERE UNA TRUSTEDLIST VUOTA MA NON E' MOLTO INTERESSANTE


            }
            @DisplayName("and the filters do not link to any service")
            @Nested
            class ImpossibleFilters{
                @BeforeEach
                void createAFilterList() throws IOException {
                    collectionOfFilters = new ArrayList<>();
                    Set<String> providerSet = new HashSet<>();
                    providerSet.add("PrimeSign GmbH");
                    ProviderFilter filterProvider = new ProviderFilter();
                    filterProvider.setWhitelist(providerSet);
                    Set<String> serviceTypeSet = new HashSet<>();
                    serviceTypeSet.add("QCertESeal");
                    ServiceTypeFilter filterServiceType = new ServiceTypeFilter();
                    filterServiceType.setWhitelist(serviceTypeSet);
                    Set<String> statusSet = new HashSet<>();
                    statusSet.add("withdrawn");
                    StatusFilter filterStatus = new StatusFilter();
                    filterStatus.setWhitelist(statusSet);
                    collectionOfFilters.add(filterProvider);
                    collectionOfFilters.add(filterServiceType);
                    collectionOfFilters.add(filterStatus);

                    filterList = new FilterList(collectionOfFilters);
                }
                @DisplayName("and I use the method getFilteredListFrom")
                @Nested
                class getFilteredListFrom {
                    TrustedList argumentTrustedList; //HA SENSO RIPETERE OGNI VOLTA ARGUMENTTRUSTEDLIST?

                    @DisplayName("with impossible filters and a list as argument, should return no providers")
                    @Test
                    void TrustedListAsArgument() throws IOException {
                    argumentTrustedList = Help.getWholeList();

                    TrustedList filteredList = filterList.getFilteredListFrom(argumentTrustedList);
                    assertTrue(filteredList.getCountries().isEmpty());
                    filteredList.getCountries().forEach(country -> {
                        assertTrue(country.getProviders().isEmpty());
                        country.getProviders().forEach(provider -> {
                            assertTrue(provider.getServices().isEmpty()); //OPPURE BASTA VEDERE CHE L'ARRAY DI COUNTRIES E' VUOTO?
                        });
                    });
                }
                    @DisplayName("Null TrustedList as argument, should return an error") //HA SENSO RIPETERE CON NULL COME LISTA?
                    @Test
                    void NullTrustedListAsArgument() throws IOException {
                        argumentTrustedList = null;
                        //assert
                        assertThrows(NullPointerException.class, () -> filterList.getFilteredListFrom(argumentTrustedList));

                    }
                }

            }
        }
        @Nested
        @DisplayName("when new with collection of filters empty") //AVEVA SENSO FARLO?
        class WhenNewWithCollectionOfFiltersEmpty{
            ArrayList collectionOfFilters;
            @BeforeEach
            void createAFilterList() {
                collectionOfFilters = new ArrayList<>();
                filterList = new FilterList(collectionOfFilters);
            }
            @DisplayName("and I use the method getFilteredListFrom")
            @Nested
            class getFilteredListFrom{
                TrustedList argumentTrustedList;
                @DisplayName("with a TrustedList as argument, should return a list equal to the filtered one")
                @Test
                void TrustedListAsArgument() throws IOException {
                     argumentTrustedList = Help.getWholeList();
                    TrustedList filteredList = filterList.getFilteredListFrom(argumentTrustedList);
                    assertEquals(filteredList, argumentTrustedList);
                }
                @Disabled
                @DisplayName("Null TrustedList as argument, should return a list equal to the filtered one")
                @Test
                void NullTrustedListAsArgument() throws IOException {
                    argumentTrustedList=null;
                    //assert
                    assertThrows(NullPointerException.class, () -> filterList.getFilteredListFrom(argumentTrustedList));

                }
                //POTREI ANCHE METTER EUNA TRUSTEDLIST VUOTA MA NON E' MOLTO INTERESSANTE
            }

        }

        @Nested
        @DisplayName("when new")
        class WhenNew{
            @BeforeEach
            void createACountry() {
                filterList = new FilterList();
            }
            @DisplayName("and I use the method getFilteredListFrom(TrustedList)")
            @Nested
            class GetFilteredList{
                TrustedList argumentTrustedList;
                @DisplayName("with a TrustedList as argument should return the same trustedList")
                @Test
                void trustedListAsArgument() throws IOException {
                    argumentTrustedList=Help.getWholeList();
                    //assert
                    TrustedList filteredList = filterList.getFilteredListFrom(argumentTrustedList);
                    assertEquals(filteredList,argumentTrustedList);
                }
                @DisplayName("with a null TrustedList as argument should return error")
                @Test
                void cloneNullTrustedList() {
                    argumentTrustedList=null;
                    //assert
                    assertThrows(NullPointerException.class, () -> filterList.getFilteredListFrom(argumentTrustedList));
                }
                //potrei fare anche come argomento la lista vuota
            }
           }

        @Test
        @DisplayName("is instantiated with new FilterList()")
        void ConstructorWithoutArguments() {
            new FilterList();
        }
        @Test
        @DisplayName("is instantiated with new FilterList(Collection<? extends Filter>)")
        void ConstructorWithFilters() {
            new FilterList( new ArrayList<>() );
        } //E'UN ERRORE METTERE COSI' COME ARGOMENTO?
        //C'E' ANCHE UN ALTRO COSTRUTTORE IN VERITA'
        @Nested
        @DisplayName("when null")
        class WhenNull {
            @BeforeEach
            void setOggettoNull() {
                filterList = null;
            }
            //SICCOME FILTERLIST E' UN ARRAY POTREI PROVARE A FA REFILTERLIST.GET(NUMERO) E VEDERE COSA FA
            @DisplayName("and I use the method getFilteredListFrom(TrustedList)")
            @Nested
            class NullGetFilteredList{
                TrustedList argumentTrustedList;
                @DisplayName("with a TrustedList as argument should return error")
                @Test
                void trustedListAsArgument() throws IOException {
                    argumentTrustedList=Help.getWholeList();
                    //assert
                    assertThrows(NullPointerException.class, () -> filterList.getFilteredListFrom(argumentTrustedList));
                }
                @DisplayName("with a null TrustedList as argument should return error")
                @Test
                void cloneNullTrustedList() {
                    argumentTrustedList=null;
                    //assert
                    assertThrows(NullPointerException.class, () -> filterList.getFilteredListFrom(argumentTrustedList));
                }
            }
        }




}
