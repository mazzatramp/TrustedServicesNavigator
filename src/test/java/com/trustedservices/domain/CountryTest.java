package com.trustedservices.domain;

import com.trustedservices.Help;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("I create a Country")
class CountryTest {
    Country country;

    @BeforeEach
    @Test
    @DisplayName("thanks to new Country(String,String)")
    void createACountry() {
        country = new Country("Austria", "AT");
    }

    @DisplayName("when I use the method equals(Object)")
    @Nested
    class Equals {
        Country argumentCountry;

        @DisplayName("and the two countries are the same object, method should return true")
        @Test
        void SameCountryObjectAsArgument() {

            argumentCountry = country;

            boolean areCountriesTHeSame = country.equals(argumentCountry);

            assertTrue(areCountriesTHeSame);

        }

        @DisplayName("and the two countries are the same , method should return true")
        @Test
        void SameCountryAsArgument() {

            argumentCountry = new Country("Austria", "AT");


            boolean areCountriesTHeSame = country.equals(argumentCountry);

            assertTrue(areCountriesTHeSame);
        }

        @DisplayName("and the two countries are not the same, method should return false")
        @Test
        void NotSameCountryAsArgument() {

            argumentCountry = new Country("Italia", "IT");

            boolean areCountriesTHeSame = country.equals(argumentCountry);

            assertFalse(areCountriesTHeSame);
        }

        @DisplayName("with a country null as argument, method should return false")
        @Test
        void NullAsArgument() {

            argumentCountry = null;

            boolean areCountriesTheSame = country.equals(argumentCountry);

            assertFalse(areCountriesTheSame);
        }

    }

    @DisplayName("when I use the method compareTo(Country)")
    @Nested
    class CompareTo {

        Country argumentCountry;

        @DisplayName("and the two countries are the same, method should return 0")
        @Test
        void SameCountryAsArgument() {

            argumentCountry =  new Country("Austria", "AT");;

            int comparison = country.compareTo(argumentCountry);
            int expectedreturn = 0;

            assertEquals(expectedreturn, comparison);
        }

        @DisplayName("and the argument country is greater, method should return a negative number")
        @Test
        void aCountry_CompareTo_BiggerCountry_ReturnNegative() {

            argumentCountry = new Country("Belgium", "BE");

            int comparison = country.compareTo(argumentCountry);

            assertTrue(comparison < 0);

        }

        @DisplayName("and the argument country is lower, method should return a positive number")
        @Test
        void aCountry_CompareTo_LowerCountry_ReturnPositive() {
            argumentCountry = new Country("Belgium", "Be");
            country =  new Country("Italy", "IT");

            int comparison = country.compareTo(argumentCountry);

            assertTrue(comparison > 0);
        }

        @DisplayName("and the argument country is null, method should return an error")
        @Test
        void aCountry_CompareTo_null() {
            Country argumentCountry = null;

            assertThrows(NullPointerException.class, () -> country.compareTo(argumentCountry));
        }
    }

    @DisplayName("when I use the method clone")
    @Nested
    class clone {
        @DisplayName("it returns the same country")
        @Test
        void cloneACountryReturnsSameCountry() {
            Country countryToBeCloned = country;

            Country clonedCountry = countryToBeCloned.clone();

            assertEquals(countryToBeCloned, clonedCountry);

        }

        @DisplayName("after attaching providers to country, it returns the same country with the providers attached")
        @Test
        void cloneACountryWithProvidersReturnsSameCountry() {
            Country countryToBeCloned = country;

            TreeSet<String> providerServiceTypes = new TreeSet<>(
                    List.of("QCertESeal", "QCertESig", "QTimestamp")
            );
            TreeSet<Service> providerServices = new TreeSet<>();
            Provider provider1 = new Provider(new Country("Belgium", "Be"), 0, "TestProvider", "TTT-000-X00", providerServiceTypes, providerServices);
            providerServices.add(new Service(provider1, 1, "Service 1", "QC", "granted", providerServiceTypes));
            providerServices.forEach(service -> service.setProvider(provider1));
            country.getProviders().add(provider1);
            Country clonedCountry = countryToBeCloned.clone();
            assertEquals(countryToBeCloned, clonedCountry);

        }
    }

    @DisplayName("when I use the method toString, it should return Country name and code")
    @Test
    void toStringMethod() {
        String expectedString = ("Country{" +
                "name='" + "Austria" + "', " +
                "code='" + "AT" + '\'' +
                '}');
        assertEquals(expectedString, country.toString());

    }

    @DisplayName("when I use the method getInformation, it should return a List of Country name and code")
    @Test
    void getInformationMethod() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add("Austria");
        expectedList.add("AT");
        assertEquals(expectedList, country.getInformation());

    }

}