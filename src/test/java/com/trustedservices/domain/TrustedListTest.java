package com.trustedservices.domain;

import com.trustedservices.Help;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A TrustedList")
class TrustedListTest {
    TrustedList trustedList;

    @Test
    @DisplayName("is instantiated with new TrustedList()")
    void isInstantiatedWithNewTrustedList() {
        new TrustedList();
    }

    @Test
    @DisplayName("is instantiated with new TrustedList(List<Country>)")
    void isInstantiatedWithNewTrustedListListOfCountriesAsArgument() {
        new TrustedList(new TreeSet<>());
    }

    @Nested
    @DisplayName("when new")
    class WhenNew {
        @BeforeEach
        void createATrustedList() {
            trustedList = new TrustedList();
        }


        @DisplayName("and I use the method updateServiceTypesAndStatuses, statuses and serviceTypes should be empty")
        @Test
        void updateServiceTypesAndStatuses() {
            trustedList.updateServiceTypesAndStatuses();
            assertTrue(trustedList.getServiceTypes().isEmpty());
            assertTrue(trustedList.getStatuses().isEmpty());
        }

        @DisplayName("and I use the method clone, it returns the same TrustedList")

            @Test
            void cloneATrustedListEmpty() {

                TrustedList listToClone = trustedList;

                TrustedList clonedList = listToClone.clone();

                assertEquals(listToClone, clonedList);
            }



        @DisplayName("and I use the method equals")
        @Nested
        class Equals {
            @DisplayName("with a whole list as argument, it returns false")
            @Test
            void equalsWithWholeListAsArgument() throws IOException {
                System.out.println(trustedList.getCountries());
                TrustedList list1 = trustedList;
                TrustedList list2 = Help.getWholeList();
                System.out.println("lista 1 " + list1.getCountries());
                System.out.println("lista 2 " + list2.getCountries());

                boolean haveTrustedListTheSameValues = list1.equals(list2);

                assertFalse(haveTrustedListTheSameValues);
            }

            @DisplayName("with a empty list as argument, it returns true")
            @Test
            void equalsWithEmptyListAsArgument() {
                TrustedList list1 = trustedList;
                TrustedList list2 = new TrustedList();

                boolean haveTrustedListTheSameValues = list1.equals(list2);

                assertTrue(haveTrustedListTheSameValues);
            }

        }
    }
    @DisplayName("when new with argument, I put all the Countries full of all the services as argument")
    @Nested
    class constructorWithArgument{
        @BeforeEach
        void createATrustedListWithArgument() throws IOException {
            Set<Country> listOfCountries;
            listOfCountries = Help.getWholeList().getCountries();
            trustedList = new TrustedList(listOfCountries);
        }

        @DisplayName("and I use the method clone, it returns the same TrustedList")
        @Test
        void cloneAFullTrustedList() throws IOException {

                TrustedList listToClone = trustedList;

                TrustedList clonedList = listToClone.clone();

                //mettere un fail se non è vuota?
                assertEquals(listToClone, clonedList);
        }

        @DisplayName("and I use the method equals()")
        @Nested
        class Equals {
            TrustedList argumentList;
                @DisplayName("with a whole list as argument, it returns true")
                @Test
                void equalsWithWholeListAsArgument() throws IOException {
                    TrustedList list1 = trustedList;
                    argumentList = Help.getWholeList();

                    boolean haveTrustedListTheSameValues = list1.equals(argumentList);

                    assertTrue(haveTrustedListTheSameValues);
                }


                @DisplayName("with a null list as argument, it returns false")
                @Test
                void equalsWithNullListAsArgument() throws IOException {
                    TrustedList list1 = trustedList;
                    argumentList = null;

                    boolean haveTrustedListTheSameValues = list1.equals(argumentList);

                    assertFalse(haveTrustedListTheSameValues);
                }
            }
        @DisplayName("and I use the method updateServiceTypesAndStatuses, statuses and serviceTypes should be full with all the possible elements")
        @Test
        void updateServiceTypesAndStatuses() {
            Set<String> listOfAllServiceTypes = new HashSet<>();
            Set<String> listOfAllStatuses = new HashSet<>();
            listOfAllStatuses.add("granted");
            listOfAllStatuses.add("deprecatedatnationallevel");
            listOfAllStatuses.add("withdrawn");
            listOfAllStatuses.add("recognisedatnationallevel");
            listOfAllServiceTypes.add("QCertESeal");
            listOfAllServiceTypes.add("QValQESig");
            listOfAllServiceTypes.add("QeRDS");
            listOfAllServiceTypes.add("QPresQESeal");
            listOfAllServiceTypes.add("Timestamp");
            listOfAllServiceTypes.add("NonRegulatory");
            listOfAllServiceTypes.add("QWAC");
            listOfAllServiceTypes.add("QPresQESig");
            listOfAllServiceTypes.add("CertESeal");
            listOfAllServiceTypes.add("QCertESig");
            listOfAllServiceTypes.add("WAC");
            listOfAllServiceTypes.add("QValQESeal");
            listOfAllServiceTypes.add("QTimestamp");
            listOfAllServiceTypes.add("CertUndefined");
            listOfAllServiceTypes.add("GenESig");
            listOfAllServiceTypes.add("CertESig");
            trustedList.updateServiceTypesAndStatuses();
            trustedList.getCountries().forEach(country -> {
                country.getProviders().forEach(provider -> {
                    provider.getServices().forEach(service -> {
                        System.out.println((service.getServiceTypes()));
                        System.out.println((service.getStatus()));
                        assertTrue(listOfAllServiceTypes.containsAll(service.getServiceTypes()));
                        assertTrue(listOfAllStatuses.contains(service.getStatus()));
                    });
                });
            });
        }
        }

    }

    /*
    DOVREI FARE Equals CON STESSI VALORI MA OGGETTO DIVERSO, NON PENSO NEL CASO PRIMA FOSSE COSI
    */

