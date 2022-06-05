package com.trustedservices.domain;

import com.trustedservices.Help;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("I create a TrustedList")
class TrustedListTest {
    TrustedList trustedList;

    @Nested
    @DisplayName("when created without arguments thanks to new TrustedList()")
    class WhenNew {
        @BeforeEach
        @Test
        void createATrustedList() {
            trustedList = new TrustedList();
        }

        @DisplayName("and I use the method isEmpty, returns true")
        @Test
        void isEmpty() {
            assertTrue(trustedList.isEmpty());
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
            @DisplayName("with a not empty list as argument, it returns false")
            @Test
            void equalsWithWholeListAsArgument() {
                System.out.println(trustedList.getCountries());
                TrustedList list1 = trustedList;
                TrustedList list2 = Help.getWholeList();
                System.out.println("lista 1 " + list1.getCountries());
                System.out.println("lista 2 " + list2.getCountries());

                boolean haveTrustedListsTheSameValues = list1.equals(list2);

                assertFalse(haveTrustedListsTheSameValues);
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

    @DisplayName("when created with arguments thanks to new TrustedList(List<Country>)")
    @Nested
    class constructorWithArgument {
        @BeforeEach
        @Test
        void createATrustedListWithArgument() {
            Set<Country> listOfCountries;
            listOfCountries = Help.getWholeList().getCountries();
            trustedList = new TrustedList(listOfCountries);
        }

        @DisplayName("and I use the method isEmpty, returns false")
        @Test
        void isEmpty() {
            assertFalse(trustedList.isEmpty());
        }

        @DisplayName("and I use the method clone, it returns the same TrustedList")
        @Test
        void cloneAFullTrustedList() {

            TrustedList listToClone = trustedList;

            TrustedList clonedList = listToClone.clone();

            //mettere un fail se non è vuota?
            assertEquals(listToClone, clonedList);
        }

        @DisplayName("and I use the method equals()")
        @Nested
        class Equals {
            TrustedList argumentList;

            @DisplayName("and the two lists are the same object, it returns true")
            @Test
            void equalsSameListObjectAsArgument() {
                argumentList = trustedList;

                boolean haveTrustedListTheSameValues = trustedList.equals(argumentList);

                assertTrue(haveTrustedListTheSameValues);
            }

            @DisplayName("and the two lists are the same, it returns true")
            @Test
            void equalsSameListAsArgument(){
                argumentList = Help.getWholeList();

                boolean haveTrustedListTheSameValues = trustedList.equals(argumentList);

                assertTrue(haveTrustedListTheSameValues);
            }

            @DisplayName("and the two lists are not the same, it returns false")
            @Test
            void equalsNotSameListAsArgument() {
                argumentList = new TrustedList();

                boolean haveTrustedListTheSameValues = trustedList.equals(argumentList);

                assertFalse(haveTrustedListTheSameValues);
            }


            @DisplayName("with a null list as argument, it returns false")
            @Test
            void equalsWithNullListAsArgument() throws IOException {
                argumentList = null;

                boolean haveTrustedListTheSameValues = trustedList.equals(argumentList);

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



