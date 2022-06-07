package com.trustedservices.domain;

import com.trustedservices.TestTrustedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("I create a TrustedList")
class TrustedListTest {
    TrustedList trustedList;

    //Three scenarios are possible: trustedList is created thanks to new TrustedList(),
    // thanks to new TrustedList(List<Country>) with a partial list of countries,
    //thanks to new TrustedList(List<Country>) with a full list of countries.
    // All three are tested to check for different outputs and show TrustedList behaviour
    @Nested
    @DisplayName("when created without arguments thanks to new TrustedList()") // the trustedList will then be empty
    class WhenNew {
        @BeforeEach
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
            TrustedList argumentList;

            @DisplayName("with a not empty list as argument, it returns false")
            @Test
            void equalsWithNotEmptyListAsArgument() {
                argumentList = TestTrustedList.getTrustedListWith("countryWithOneProvider");
                boolean areTrustedListsEquals = trustedList.equals(argumentList);

                assertFalse(areTrustedListsEquals);
            }

            @DisplayName("with a empty list as argument, it returns true")
            @Test
            void equalsWithEmptyListAsArgument() {
                argumentList = new TrustedList();

                boolean areTrustedListsEquals = trustedList.equals(argumentList);

                assertTrue(areTrustedListsEquals);
            }

            @DisplayName("with a null list as argument, it returns false")
            @Test
            void equalsWithNullListAsArgument() {
                argumentList = null;

                boolean areTrustedListsEquals = trustedList.equals(argumentList);

                assertFalse(areTrustedListsEquals);
            }

            @DisplayName("and the two lists are the same object, it returns true")
            @Test
            void equalsSameListObjectAsArgument() {
                argumentList = trustedList;

                boolean areTrustedListsEquals = trustedList.equals(argumentList);

                assertTrue(areTrustedListsEquals);
            }
        }
    }

    @DisplayName("when created with arguments thanks to new TrustedList(List<Country>) with a partial list of countries")
    @Nested
    class constructorWithArgument {
        @BeforeEach
        void createATrustedListWithArgument() {
            trustedList = TestTrustedList.getTrustedListWith(
                    "RealCountryWithOneRealProviderWitAllServices1",
                    "RealCountryWithOneRealProviderWitAllServices2"
            );
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

            boolean areEquals = trustedList.equals(clonedList) && clonedList.equals(trustedList);
            assertTrue(areEquals);
        }

        @DisplayName("and I use the method equals()")
        @Nested
        class Equals {
            TrustedList argumentList;

            @BeforeEach
            void createATrustedListWithArgument() {
                TreeSet<Country> listOfCountries;
                listOfCountries = TestTrustedList.getTrustedListWith("RealCountryWithOneRealProviderWitAllServices1",
                        "RealCountryWithOneRealProviderWitAllServices2").getCountries();
                trustedList = new TrustedList(listOfCountries);
            }

            @DisplayName("and the two lists are the same object, it returns true")
            @Test
            void equalsSameListObjectAsArgument() {
                argumentList = trustedList;


                boolean areTrustedListsEquals = trustedList.equals(argumentList);

                assertTrue(areTrustedListsEquals);
            }

            @DisplayName("and the two lists are the same, it returns true")
            @Test
            void equalsSameListAsArgument() {
                argumentList = TestTrustedList.getTrustedListWith("RealCountryWithOneRealProviderWitAllServices1",
                        "RealCountryWithOneRealProviderWitAllServices2");
                boolean areCountriesEqual = argumentList.getCountries().equals(trustedList.getCountries());
                assertTrue(areCountriesEqual);
            }

            @DisplayName("and the two lists are not the same, it returns false")
            @Test
            void equalsNotSameListAsArgument() {
                argumentList = new TrustedList();

                boolean areTrustedListsEquals = trustedList.equals(argumentList);

                assertFalse(areTrustedListsEquals);
            }


            @DisplayName("with a null list as argument, it returns false")
            @Test
            void equalsWithNullListAsArgument() {
                argumentList = null;

                boolean areTrustedListsEquals = trustedList.equals(argumentList);

                assertFalse(areTrustedListsEquals);
            }
        }

        @DisplayName("and I use the method updateServiceTypesAndStatuses, statuses and serviceTypes should be just the ones of the list")
        @Test
        void updateServiceTypesAndStatuses() {
            //ListOfServiceTypes and listOfStatuses are filled with all the service types and statuses of the services of testTrustedList1
            TreeSet<String> listOfServiceTypes = new TreeSet<>();
            TreeSet<String> listOfStatuses = new TreeSet<>();

            listOfStatuses.add("withdrawn");
            listOfStatuses.add("granted");
            listOfServiceTypes.add("QValQESig");
            listOfServiceTypes.add("QValQESeal");
            listOfServiceTypes.add("QCertESig");


            assertEquals(trustedList.getStatuses(), listOfStatuses);
            assertEquals(trustedList.getServiceTypes(), listOfServiceTypes);
        }
    }

    @DisplayName("when created with arguments thanks to new TrustedList(List<Country>) with a full list of countries")
    @Nested
    class constructorWithArgumentAllCountries {
        @BeforeEach
        void createATrustedListWithArgument() {
            TreeSet<Country> listOfCountries = TestTrustedList.getWholeLocalTrustedList().getCountries();
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

                boolean areTrustedListsEquals = trustedList.equals(argumentList);

                assertTrue(areTrustedListsEquals);
            }

            @DisplayName("and the two lists are the same, it returns true")
            @Test
            void equalsSameListAsArgument() {
                argumentList = TestTrustedList.getWholeLocalTrustedList();

                boolean areTrustedListsEquals = trustedList.equals(argumentList);

                assertTrue(areTrustedListsEquals);
            }

            @DisplayName("and the two lists are not the same, it returns false")
            @Test
            void equalsNotSameListAsArgument() {
                argumentList = TestTrustedList.getTrustedListWith("CountryWithProviderWithServices_ButServiceTypesSetToTest1",
                        "RealCountryWithOneRealProviderWitAllServices2");

                boolean areTrustedListsEquals = trustedList.equals(argumentList);

                assertFalse(areTrustedListsEquals);
            }


            @DisplayName("with a null list as argument, it returns false")
            @Test
            void equalsWithNullListAsArgument() {
                argumentList = null;

                boolean areTrustedListsEquals = trustedList.equals(argumentList);

                assertFalse(areTrustedListsEquals);
            }
        }

        @DisplayName("and I use the method updateServiceTypesAndStatuses, statuses and serviceTypes should be full with all the possible elements")
        @Test
        void updateServiceTypesAndStatuses() {
            //ListOfAllServiceTypes and listOfAllStatuses are filled with all the possible service types and statuses
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
            assertTrue(trustedList.getStatuses().equals(listOfAllStatuses));
            assertTrue(trustedList.getServiceTypes().equals(listOfAllServiceTypes));

        }
    }

}



