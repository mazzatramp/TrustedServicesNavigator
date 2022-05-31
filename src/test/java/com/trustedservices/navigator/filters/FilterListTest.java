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
        FilterList filterList;
        @Nested
        //IL PROBLEMA E' CHE VOLENDO CI SONO INFINITE COMBINAZIONI DI FILTRI DA METTERE NEL COSTRUTTORE
        @DisplayName("when new with collection of filters not empty and possible")
        class WhenNewWithFilters {
            ArrayList collectionOfFilters;

            @BeforeEach
            void createAFilterList() throws IOException {
                collectionOfFilters = new ArrayList<>();
                Set<String> providerSet = new HashSet<>();
                providerSet.add("PrimeSign GmbH");
                ProviderFilter filterProvider= new ProviderFilter();
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
                @DisplayName("with a possible , should return just the filtered services")
                @Test
                void TrustedListAsArgument() throws IOException {
                    TrustedList listToFilter = Help.getWholeList();
                    TrustedList filteredList = filterList.getFilteredListFrom(listToFilter);
                    assertEquals(filteredList, listToFilter);
                }

                @Disabled
                @DisplayName("Null TrustedList as argument, should return a list equal to the filtered one")
                @Test
                void NullTrustedListAsArgument() throws IOException {
                    TrustedList listToFilter = null;
                    TrustedList filteredList = filterList.getFilteredListFrom(listToFilter);
                    assertEquals(filteredList, listToFilter);
                }
                //POTREI ANCHE METTER EUNA TRUSTEDLIST VUOTA MA NON E' MOLTO INTERESSANTE


            }
        }
        @Nested
        @DisplayName("when new with collection of filters empty")
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
                @DisplayName("with a TrustedList as argument, should return a list equal to the filtered one")
                @Test
                void TrustedListAsArgument() throws IOException {
                    TrustedList listToFilter = Help.getWholeList();
                    TrustedList filteredList = filterList.getFilteredListFrom(listToFilter);
                    assertEquals(filteredList, listToFilter);
                }
                @Disabled
                @DisplayName("Null TrustedList as argument, should return a list equal to the filtered one")
                @Test
                void NullTrustedListAsArgument() throws IOException {
                    TrustedList listToFilter = null;
                    TrustedList filteredList = filterList.getFilteredListFrom(listToFilter);
                    assertEquals(filteredList, listToFilter);
                }
                //POTREI ANCHE METTER EUNA TRUSTEDLIST VUOTA MA NON E' MOLTO INTERESSANTE
            }
            @Disabled
            @DisplayName("and I use the method ...")
            @Nested
            class nomeMetodo2{}
        }

        @Nested
        @DisplayName("when new")
        class WhenNew{
            @BeforeEach
            void createACountry() {
                filterList = new FilterList();
            }
            @DisplayName("and I use the method getFilteredListFrom")
            @Nested
            class getFilteredListFrom{
                @DisplayName("and I use the method getFilteredListFrom")
                @Test
                void TrustedListAsArgument(){

                }
            }
            @Disabled
            @DisplayName("and I use the method ...")
            @Nested
            class nomeMetodo2{}}

        @Test
        @DisplayName("is instantiated with new FilterList()")
        void ConstructorWithoutArguments() {
            new FilterList();
        }
        @Test
        @DisplayName("is instantiated with new FilterList(Collection<? extends Filter>)")
        void ConstructorWithFilters() {
            new FilterList( new ArrayList<>() );
        }
        @Nested
        @DisplayName("when null")
        class WhenNull {
            @BeforeEach
            void setOggettoNull() {
                //nomeOggetto=null;
            }
            @DisplayName("and I use the method ...")
            @Nested
            class nomeMetodo1{}
            @DisplayName("and I use the method ...")
            @Nested
            class nomeMetodo2{}
        }




}
