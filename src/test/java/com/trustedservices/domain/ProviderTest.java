package com.trustedservices.domain;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("I create a Provider")
class ProviderTest {
    Provider provider;
    @BeforeEach
    @Test
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
        provider = new Provider(new Country("Italia", "IT"), 0, "TestProvider", "TTT-000-X01");
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
            TreeSet<String> providerServiceTypes = new TreeSet<>(
                    List.of("QCertESeal", "QCertESig", "QTimestamp")
            );

            TreeSet<Service> providerServices = new TreeSet<>();

            argumentProvider = new Provider(new Country("Italia", "IT"), 0, "TestProvider", "TTT-000-X01", providerServiceTypes, providerServices);

            providerServices.add(new Service(provider, 1, "Service 1", "QC", "granted", providerServiceTypes));
            providerServices.forEach(service -> service.setProvider(provider));

            assertEquals(provider, argumentProvider);
        }

        @DisplayName("and the two providers are not the same, method should return false")
        @Test
        void NotSameProviderAsArgument() {
            TreeSet<String> providerServiceTypes = new TreeSet<>(
                    List.of("QCertESeal", "QCertESig", "QTimestamp")
            );

            TreeSet<Service> providerServices = new TreeSet<>();

            argumentProvider = new Provider(new Country("Italia", "IT"), 0, "TestProvider2", "TTT-000-X02", providerServiceTypes, providerServices);

            providerServices.add(new Service(provider, 1, "Service 1", "QC", "granted", providerServiceTypes));
            providerServices.forEach(service -> service.setProvider(provider));

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
            TreeSet<String> argumentProviderServiceTypes = new TreeSet<>(
                    List.of("QCertESeal", "QCertESig", "QTimestamp")
            );

            TreeSet<Service> argumentProviderServices = new TreeSet<>();

            argumentProvider = new Provider(new Country("Italia", "IT"), 0, "TestProvider", "TTT-000-X01", argumentProviderServiceTypes, argumentProviderServices);

            argumentProviderServices.add(new Service(provider, 1, "Service 1", "QC", "granted", argumentProviderServiceTypes));
            argumentProviderServices.forEach(service -> service.setProvider(provider));

            int comparison = provider.compareTo(argumentProvider);
            int expectedReturn = 0;

            assertEquals(expectedReturn, comparison);

        }

        @DisplayName("and the argument provider is greater, method should return a negative number")
        @Test
        void aProviderCompareToBiggerProviderReturnNegative() {

            TreeSet<String> providerServiceTypes = new TreeSet<>(
                    List.of("QCertESeal", "QCertESig", "QTimestamp")
            );

            TreeSet<Service> providerServices = new TreeSet<>();

            argumentProvider = new Provider(new Country("Italia", "IT"), 0, "TestProvider2", "TTT-000-X02", providerServiceTypes, providerServices);

            providerServices.add(new Service(provider, 1, "Service 1", "QC", "granted", providerServiceTypes));
            providerServices.forEach(service -> service.setProvider(provider));

            int comparison = provider.compareTo(argumentProvider);

            assertTrue(comparison < 0);

        }

        @DisplayName("and the argument provider is lower, method should return a positive number")
        @Test
        void aProviderCompareToLowerProviderReturnPositive() {

            TreeSet<String> providerServiceTypes = new TreeSet<>(
                    List.of("QCertESeal", "QCertESig", "QTimestamp")
            );

            TreeSet<Service> providerServices = new TreeSet<>();

            argumentProvider = new Provider(new Country("Italia", "IT"), 0, "TestProvider", "TTT-000-X00", providerServiceTypes, providerServices);

            providerServices.add(new Service(provider, 1, "Service 1", "QC", "granted", providerServiceTypes));
            providerServices.forEach(service -> service.setProvider(provider));

            int comparison = provider.compareTo(argumentProvider);

            assertTrue(comparison > 0);

        }

        @DisplayName("and the argument provider is null, method should return NullPointerException")
        @Test
        void aProviderCompareToNullReturnException() {
            Provider argumentProvider = null;

            assertThrows(NullPointerException.class, () -> provider.compareTo(argumentProvider));
        }

        //EMPTY A SX
        //EMPTY A DX NON INTERESSANTE
        //EMPTY EMPTY
    }

    @DisplayName("when I use the method clone, it returns the same provider")

    @Test
    void cloneAProviderReturnsSameProvider()  {
        Provider providerToBeCloned = provider;

        Provider clonedProvider = providerToBeCloned.clone();

        assertEquals(providerToBeCloned, clonedProvider);


    }

    @Test
    @Disabled
    void doesSetServiceTypesWork() {

        Provider providerToTest = provider;

        Set<String> listIWantToSetToAziendaZero = new TreeSet<>();
        listIWantToSetToAziendaZero.add("QCertESeal");
        listIWantToSetToAziendaZero.add("QCertESig");
        listIWantToSetToAziendaZero.add("QTimestamp");
        listIWantToSetToAziendaZero.add("QWAC");

        Set<String> listThatShouldBeEqualToServiceTypesOfAziendaZero = new TreeSet<>();
        listThatShouldBeEqualToServiceTypesOfAziendaZero.add("QCertESeal");
        listThatShouldBeEqualToServiceTypesOfAziendaZero.add("QCertESig");
        listThatShouldBeEqualToServiceTypesOfAziendaZero.add("QTimestamp");
        listThatShouldBeEqualToServiceTypesOfAziendaZero.add("QWAC");

        assertLinesMatch(
                listThatShouldBeEqualToServiceTypesOfAziendaZero.stream().toList(),
                providerToTest.getServiceTypes().stream().toList()
        );
    }
    @DisplayName("when I use the method toString, it should return Provider providerId, name, trustmark, serviceTypes, coountryCode, Services")
    @Test
    void toStringMethod(){
        TreeSet<String> providerServiceTypes = new TreeSet<>(
                List.of("QCertESeal", "QCertESig", "QTimestamp")
        );
        TreeSet<Service> providerServices = new TreeSet<>();
        providerServices.add(new Service(provider, 1, "Service 1", "QC", "granted", providerServiceTypes));
        providerServices.forEach(service -> service.setProvider(provider));

        String expectedString= "Provider{" +
                "providerId=" + "0" +
                ", name='" + "TestProvider" + '\'' +
                ", trustmark='" + "TTT-000-X01" + '\'' +
                ", serviceTypes=" + providerServiceTypes +
                ", countryCode=" + "IT" +
                ", services=" + providerServices +
                '}';
        assertEquals(expectedString, provider.toString());

    }
    @DisplayName("when I use the method getInformation, it should return a List of Country name and code")
    @Test
    void getInformationMethod(){
        String expectedString = "Name: " + "TestProvider" + "\n" +
                "Trustmark: " + "TTT-000-X01" + "\n\n" +
                "Based in " + "Italia" + " (" + "IT" + ")\n" +
                "With " + "1" + " services displayed.";
        assertEquals(expectedString, provider.getHumanInformation());

    }

}


