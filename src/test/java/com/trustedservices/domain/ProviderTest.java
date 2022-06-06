package com.trustedservices.domain;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("I create a Provider")
class ProviderTest {
    Provider provider;

    @BeforeEach
    @DisplayName("thanks to new Provider(String,Int, String,String,List<ServiceType>,List<providerServices>)")
    void isInstantiatedWithNewProvider() {
        TreeSet<String> providerServiceTypes = new TreeSet<>(
                List.of("QCertESeal", "QCertESig", "QTimestamp")
        );

        TreeSet<Service> providerServices = new TreeSet<>();

        provider = new Provider(new Country("Italia", "IT"), 0, "TestProvider", "TTT-000-X01", providerServiceTypes, providerServices);

        providerServices.add(new Service(provider, 1, "Service 1", "QC", "granted", providerServiceTypes));
        providerServices.forEach(service -> service.setProvider(provider));
    }

    @Test
    @DisplayName("or alternatively thanks to new Provider(Country, int, String, String)")
    void AlternativelyIsInstantiatedWithNewProvider() {
        new Provider(new Country("Italia", "IT"), 0, "TestProvider", "TTT-000-X01");
    }

    @DisplayName("when I use the method equals(Object)")
    @Nested
    class Equals {

        Provider argumentProvider;

        @DisplayName("and the two providers are the object, method should return true")
        @Test
        void SameProviderObjectAsArgument() {

            argumentProvider = provider;

            assertEquals(provider, argumentProvider);
        }

        @DisplayName("and the two providers are the same, method should return true")
        @Test
        void SameProviderAsArgument() {

            argumentProvider = getATestProvider1();

            assertEquals(provider, argumentProvider);
        }

        @DisplayName("and the two providers are not the same, method should return false")
        @Test
        void NotSameProviderAsArgument() {
            argumentProvider = getATestProvider2();

            assertNotEquals(provider, argumentProvider);
        }

        @DisplayName("with a provider null as argument, method should return false")
        @Test
        void NullAsArgument() {

            argumentProvider = null;

            boolean areProvidersTheSame = provider.equals(argumentProvider);

            assertFalse(areProvidersTheSame);
        }

    }

    @DisplayName("when I use the method compareTo(Provider)")
    @Nested
    class CompareTo {

        Provider argumentProvider;

        @DisplayName("and the two providers are the same, method should return 0")
        @Test
        void SameProviderAsArgument() {
            argumentProvider = getATestProvider1();

            int comparison = provider.compareTo(argumentProvider);
            int expectedResult = 0;
            assertEquals(expectedResult, comparison);

        }

        @DisplayName("and the argument provider is greater, method should return a negative number")
        @Test
        void aProviderCompareToBiggerProviderReturnNegative() {

            argumentProvider = getATestProvider2();

            int comparison = provider.compareTo(argumentProvider);

            assertTrue(comparison < 0);

        }

        @DisplayName("and the argument provider is lower, method should return a positive number")
        @Test
        void aProviderCompareToLowerProviderReturnPositive() {
            argumentProvider = getATestProvider0();

            int comparison = provider.compareTo(argumentProvider);

            assertTrue(comparison > 0);

        }

        @DisplayName("and the argument provider is null, method should return NullPointerException")
        @Test
        void aProviderCompareToNullReturnException() {
            Provider argumentProvider = null;

            assertThrows(NullPointerException.class, () -> provider.compareTo(argumentProvider));
        }
    }

    @DisplayName("when I use the method clone, it returns the same provider")

    @Test
    void cloneAProviderReturnsSameProvider() {
        Provider providerToBeCloned = provider;

        Provider clonedProvider = providerToBeCloned.clone();

        assertEquals(providerToBeCloned, clonedProvider);


    }

    @DisplayName("when I use the method toString, it should return Provider providerId, name, trustmark, serviceTypes, countryCode, Services")
    @Test
    void toStringMethod() {
        //Another service is attached to provider in order to better test toStringMethod()
        TreeSet<String> providerServiceTypes = new TreeSet<>(
                List.of("QCertESeal", "QCertESig", "QTimestamp")
        );
        TreeSet<Service> providerServices = new TreeSet<>();
        providerServices.add(new Service(provider, 1, "Service 1", "QC", "granted", providerServiceTypes));
        providerServices.forEach(service -> service.setProvider(provider));

        String expectedString = "Provider{" +
                "providerId=" + "0" +
                ", name='" + "TestProvider" + '\'' +
                ", trustmark='" + "TTT-000-X01" + '\'' +
                ", serviceTypes=" + providerServiceTypes +
                ", countryCode=" + "IT" +
                ", services=" + providerServices +
                '}';
        assertEquals(expectedString, provider.toString());

    }

    @DisplayName("when I use the method getHumanInformation, it should return string with information")
    @Test
    void getInformationMethod() {
        String expectedString = "Name: " + "TestProvider" + "\n" +
                "Trustmark: " + "TTT-000-X01" + "\n\n" +
                "Based in " + "Italia" + " (" + "IT" + ")\n" +
                "With " + "1" + " services displayed.";
        assertEquals(expectedString, provider.getDescription());

    }

    private Provider getATestProvider0() {

        TreeSet<String> providerServiceTypes0 = new TreeSet<>(
                List.of("QCertESeal", "QCertESig", "QTimestamp")
        );

        TreeSet<Service> providerServices0 = new TreeSet<>();

        Provider provider0 = new Provider(new Country("Italia", "IT"), 0, "TestProvider", "TTT-000-X00", providerServiceTypes0, providerServices0);

        providerServices0.add(new Service(provider0, 1, "Service 1", "QC", "granted", providerServiceTypes0));
        providerServices0.forEach(service -> service.setProvider(provider0));
        return provider0;
    }

    private Provider getATestProvider1() {
        TreeSet<String> providerServiceTypes1 = new TreeSet<>(
                List.of("QCertESeal", "QCertESig", "QTimestamp")
        );

        TreeSet<Service> providerServices1 = new TreeSet<>();

        Provider provider1 = new Provider(new Country("Italia", "IT"), 0, "TestProvider", "TTT-000-X01", providerServiceTypes1, providerServices1);

        providerServices1.add(new Service(provider1, 1, "Service 1", "QC", "granted", providerServiceTypes1));
        providerServices1.forEach(service1 -> service1.setProvider(provider1));
        return provider1;
    }

    private Provider getATestProvider2() {
        TreeSet<String> providerServiceTypes2 = new TreeSet<>(
                List.of("QCertESeal", "QCertESig", "QTimestamp")
        );

        TreeSet<Service> providerServices2 = new TreeSet<>();

        Provider provider2 = new Provider(new Country("Italia", "IT"), 0, "TestProvider2", "TTT-000-X02", providerServiceTypes2, providerServices2);

        providerServices2.add(new Service(provider2, 1, "Service 1", "QC", "granted", providerServiceTypes2));
        providerServices2.forEach(service -> service.setProvider(provider2));
        return provider2;
    }

}


