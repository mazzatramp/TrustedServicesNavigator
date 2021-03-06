package com.trustedservices.domain;

import org.junit.jupiter.api.*;

import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("I create a Country")
class CountryTest {
    Country country;

    @Test
    @DisplayName("thanks to new Country(String,String)")
    void testingConstructor() {
        new Country("Austria", "AT");
    }


    @BeforeEach
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

            boolean areCountriesTheSame = country.equals(argumentCountry);

            assertTrue(areCountriesTheSame);

        }

        @DisplayName("and the two countries are the same , method should return true")
        @Test
        void SameCountryAsArgument() {

            argumentCountry = new Country("Austria", "AT");


            boolean areCountriesTheSame = country.equals(argumentCountry);

            assertTrue(areCountriesTheSame);
        }

        @DisplayName("and the two countries are not the same, method should return false")
        @Test
        void NotSameCountryAsArgument() {

            argumentCountry = new Country("Italia", "IT");

            boolean areCountriesTheSame = country.equals(argumentCountry);

            assertFalse(areCountriesTheSame);
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

            argumentCountry = new Country("Austria", "AT");

            int comparison = country.compareTo(argumentCountry);
            int expectedReturn = 0;

            assertEquals(expectedReturn, comparison);
        }

        @DisplayName("and the argument country is greater, method should return a negative number")
        @Test
        void aCountryCompareToBiggerCountryReturnNegative() {

            argumentCountry = new Country("Belgium", "BE");

            int comparison = country.compareTo(argumentCountry);

            assertTrue(comparison < 0);

        }

        @DisplayName("and the argument country is lower, method should return a positive number")
        @Test
        void aCountryCompareToLowerCountryReturnPositive() {
            argumentCountry = new Country("Belgium", "Be");
            country = new Country("Italy", "IT");

            int comparison = country.compareTo(argumentCountry);

            assertTrue(comparison > 0);
        }

        @DisplayName("and the argument country is null, method should return a NullPointerException")
        @Test
        void aCountryCompareToNull() {
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
            attachProviderToCountry(country);
            Country countryToBeCloned = country;
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

    @DisplayName("when I use the method getHumanInformation, it should return a string with information")
    @Test
    void getInformationMethod() {
        attachProviderToCountry(country);
        String expectedString = "Austria" + " (" + "AT" + ")\n\n" +
                "With " + 2 + " providers displayed\n" +
                "And " + 2 + " services displayed\n";
        assertEquals(expectedString, country.getDescription());

    }

    private void attachProviderToCountry(Country country) // two test providers are attached to the country in order to execute a better test of the methods clone() and getHumanInformation
    {
        TreeSet<String> providerServiceTypes = new TreeSet<>(
                List.of("QCertESeal", "QCertESig", "QTimestamp")
        );
        TreeSet<Service> providerServices = new TreeSet<>();
        Provider provider1 = new Provider(country, 0, "TestProvider", "TTT-000-X00", providerServiceTypes, providerServices);
        providerServices.add(new Service(provider1, 1, "Service 1", "QC", "granted", providerServiceTypes));
        providerServices.forEach(service -> service.setProvider(provider1));
        TreeSet<String> providerServiceTypes2 = new TreeSet<>(
                List.of("QCertESeal", "QCertESig", "QTimestamp")
        );
        TreeSet<Service> providerServices2 = new TreeSet<>();
        Provider provider2 = new Provider(country, 0, "TestProvider", "TTT-000-X01", providerServiceTypes2, providerServices2);
        providerServices2.add(new Service(provider2, 1, "Service 1", "QC", "granted", providerServiceTypes2));
        providerServices2.forEach(service -> service.setProvider(provider2));
        country.getProviders().add(provider1);
        country.getProviders().add(provider2);
    }
}