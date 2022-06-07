package com.trustedservices.domain;

import com.sun.source.tree.Tree;
import com.trustedservices.DummyTrustedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("I create a TrustedList")
class TrustedListTest {
    TrustedList trustedList;

    @Nested
    @DisplayName("when created without arguments thanks to new TrustedList()")
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
            void equalsWithWholeListAsArgument() {
                argumentList = getTestTrustedList1();
                boolean haveTrustedListsTheSameValues = trustedList.equals(argumentList);

                assertFalse(haveTrustedListsTheSameValues);
            }

            @DisplayName("with a empty list as argument, it returns true")
            @Test
            void equalsWithEmptyListAsArgument() {
                argumentList = new TrustedList();

                boolean haveTrustedListTheSameValues = trustedList.equals(argumentList);

                assertTrue(haveTrustedListTheSameValues);
            }

            @DisplayName("with a null list as argument, it returns false")
            @Test
            void equalsWithNullListAsArgument() {
                argumentList = null;

                boolean haveTrustedListTheSameValues = trustedList.equals(argumentList);

                assertFalse(haveTrustedListTheSameValues);
            }
        }
    }

    @DisplayName("when created with arguments thanks to new TrustedList(List<Country>) with a partial list of countries")
    @Nested
    class constructorWithArgument {
        @BeforeEach
        void createATrustedListWithArgument() {
            TreeSet<Country> listOfCountries;
            listOfCountries = getTestTrustedList1().getCountries();
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

            boolean areEquals = trustedList.equals(clonedList) && clonedList.equals(trustedList);
            assertTrue(areEquals);
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
            void equalsSameListAsArgument() {
                argumentList = getTestTrustedList1();
                boolean areCountriesEqual = argumentList.getCountries().equals(trustedList.getCountries());
                System.out.println((argumentList.getCountries()).equals(trustedList.getCountries()));
                System.out.println(trustedList.getCountries().equals(argumentList.getCountries()));
                System.out.println(argumentList.getServiceTypes().equals(trustedList.getServiceTypes()));
                System.out.println(argumentList.getStatuses().equals(trustedList.getStatuses()));
                System.out.println(argumentList.getCountries());
                System.out.println(trustedList.getCountries());
                System.out.println(argumentList.getStatuses());
                System.out.println(trustedList.getStatuses());
                System.out.println(argumentList.getServiceTypes());
                System.out.println(trustedList.getServiceTypes());

                boolean haveTrustedListTheSameValuesbefore = trustedList.equals(argumentList); //da togliere
                boolean haveTrustedListTheSameValues = argumentList.equals(trustedList);//da togliere
                System.out.println(argumentList.equals(trustedList));//da togliere
                System.out.println(trustedList.equals(argumentList));//da togliere
                assertTrue(haveTrustedListTheSameValues);
                assertTrue(haveTrustedListTheSameValuesbefore);
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
            void equalsWithNullListAsArgument() {
                argumentList = null;

                boolean haveTrustedListTheSameValues = trustedList.equals(argumentList);

                assertFalse(haveTrustedListTheSameValues);
            }
        }

        @DisplayName("and I use the method updateServiceTypesAndStatuses, statuses and serviceTypes should be full with all the possible elements")
        @Test
        void updateServiceTypesAndStatuses() {
            //ListOfServiceTypes and listOfStatuses are filled with all the service types and statuses of the services of testTrustedList1
            Set<String> listOfServiceTypes = new HashSet<>();
            Set<String> listOfStatuses = new HashSet<>();
            listOfStatuses.add("granted");
            listOfStatuses.add("withdrawn");
            listOfServiceTypes.add("QCertESeal");
            listOfServiceTypes.add("QCertESig");
            listOfServiceTypes.add("QWAC");
            listOfServiceTypes.add("QTimestamp");
            System.out.println(trustedList.getStatuses().equals(listOfStatuses));
            System.out.println(trustedList.getServiceTypes().equals(listOfServiceTypes));
            assertTrue(trustedList.getStatuses().equals(listOfStatuses));
            assertTrue(trustedList.getServiceTypes().equals(listOfServiceTypes));


            /* penso sia errato e va corretto anche da altre parti
            trustedList.updateServiceTypesAndStatuses();
            trustedList.getCountries().forEach(country -> {
                country.getProviders().forEach(provider -> {
                    provider.getServices().forEach(service -> {
                        assertTrue(listOfServiceTypes.containsAll(service.getServiceTypes()));
                        assertTrue(listOfStatuses.contains(service.getStatus()));
                    });
                });
            });*/
        }
    }
    @DisplayName("when created with arguments thanks to new TrustedList(List<Country>) with a full list of countries")
    @Nested
    class constructorWithArgumentAllCountries {
        @BeforeEach
        void createATrustedListWithArgument() {
            DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
            TreeSet<Country>listOfCountries = dummyTrustedList.getDummyTrustedList().getCountries();
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
            assertTrue(listToClone.equals(clonedList));//da togliere
            assertTrue(clonedList.equals(listToClone));//da togliere
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
            void equalsSameListAsArgument() {
                DummyTrustedList dummyTrustedList = DummyTrustedList.getInstance();
                argumentList = dummyTrustedList.getDummyTrustedList();

                boolean haveTrustedListTheSameValues = trustedList.equals(argumentList);

                assertTrue(haveTrustedListTheSameValues);
            }

            @DisplayName("and the two lists are not the same, it returns false")
            @Test
            void equalsNotSameListAsArgument() {
                argumentList = getTestTrustedList1();

                boolean haveTrustedListTheSameValues = trustedList.equals(argumentList);

                assertFalse(haveTrustedListTheSameValues);
            }


            @DisplayName("with a null list as argument, it returns false")
            @Test
            void equalsWithNullListAsArgument() {
                argumentList = null;

                boolean haveTrustedListTheSameValues = trustedList.equals(argumentList);

                assertFalse(haveTrustedListTheSameValues);
            }
        }

        @DisplayName("and I use the method updateServiceTypesAndStatuses, statuses and serviceTypes should be full with all the possible elements")
        @Test
        void updateServiceTypesAndStatuses() {
            System.out.println(trustedList.getCountries());
            System.out.println(trustedList.getStatuses());
            System.out.println(trustedList.getServiceTypes());
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
            /*
            trustedList.updateServiceTypesAndStatuses();
            trustedList.getCountries().forEach(country -> {
                country.getProviders().forEach(provider -> {
                    provider.getServices().forEach(service -> {
                        assertTrue(listOfAllServiceTypes.containsAll(service.getServiceTypes()));
                        assertTrue(listOfAllStatuses.contains(service.getStatus()));
                    });
                });
            });*/
        }
    }
    private TrustedList getTestTrustedList1(){
        TrustedList trustedList1;

        Country country1 = new Country("Austria","AT");
        Country country2 = new Country("Italy", "IT");

        Provider provider1 = getATestProvider1(country1);
        Provider provider2 = getATestProvider2(country1);

        country1.getProviders().add(provider1);
        country1.getProviders().add(provider2);

        Provider provider3 = getATestProvider1(country2);
        country2.getProviders().add(provider3);

        TreeSet<Country> countriesOfTrustedList1 = new TreeSet<>();
        countriesOfTrustedList1.add(country1);
        countriesOfTrustedList1.add(country2);

        trustedList1= new TrustedList(countriesOfTrustedList1);
        trustedList1.updateServiceTypesAndStatuses();

        return trustedList1;
    }

    private Provider getATestProvider1(Country country) {
        TreeSet<String> providerServiceTypes1 = new TreeSet<>(List.of("QCertESeal", "QCertESig", "QTimestamp"));
        TreeSet<Service> providerServices1 = new TreeSet<>();

        Provider provider1 = new Provider(country, 0, "Azienda Zero", "TTT-000-X01", providerServiceTypes1, providerServices1);

        providerServices1.add(new Service(provider1, 1, "Service 1", "QC", "granted", providerServiceTypes1));
        providerServices1.forEach(service1 -> service1.setProvider(provider1));
        return provider1;
    }

    private Provider getATestProvider2(Country country) {
        TreeSet<String> providerServiceTypes2 = new TreeSet<>(
                List.of("QWAC", "QCertESig", "QTimestamp")
        );

        TreeSet<Service> providerServices2 = new TreeSet<>();

        Provider provider2 = new Provider(country, 0, "PrimeSign GmbH", "TTT-000-X02", providerServiceTypes2, providerServices2);

        providerServices2.add(new Service(provider2, 1, "Service 1", "QC", "withdrawn", providerServiceTypes2));
        providerServices2.forEach(service -> service.setProvider(provider2));
        return provider2;
    }

}



