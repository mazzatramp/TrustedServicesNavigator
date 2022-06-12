package com.trustedservices;

import com.trustedservices.domain.Country;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.Service;
import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.web.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class TestTrustedList {
    private static TrustedList actualApiTrustedList;
    private static Map<String, Country> testCountries;

    public static TrustedList getTrustedListWith(String... countryNames) {
        if (testCountries == null)
            createTestCountries();

        TrustedList building = new TrustedList();
        for (String countryName : countryNames) {
            Country testToAdd = testCountries.get(countryName);
            if (testToAdd == null)
                System.err.println(countryName + " not found in testCountries");
            else
                building.getCountries().add(testToAdd);
        }
        building.updateServiceTypesAndStatuses();
        return building.clone();
    }


    public static TrustedList getWholeLocalTrustedList() {
        if (actualApiTrustedList == null)
            actualApiTrustedList = setupFileBuilder().build();
        return actualApiTrustedList.clone();
    }

    private static TrustedListJsonBuilder setupFileBuilder() {
        TrustedListJsonBuilder builder = new TrustedListJsonBuilder();

        Path countries = Path.of("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/countryListDummy.json");
        Path providers = Path.of("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/providerListDummy.json");
        try {
            builder.setCountriesJson(Files.readString(countries));
            builder.setProvidersJson(Files.readString(providers));
        } catch (IOException e) {
            System.err.println("Error reading json test files: \n" + e.getMessage());
            throw new RuntimeException(e);
        }
        return builder;
    }

    private static void createTestCountries() {
        testCountries = new HashMap<>();
        testCountries.put("countryWithNoProviders", new Country("EmptyCountry", "0P"));
        testCountries.put("countryWithOneProvider", getCountryWithOneProvider());
        testCountries.put("RealCountryWithOneRealProviderWitAllServices1", getAustriaWithDatakom());
        testCountries.put("RealCountryWithOneRealProviderWitAllServices2", getBelgiumWithConnective());
        testCountries.put("CountryWithProviderWithServices_ButServiceTypesSetToTest1", getAustriaWithDatakom_ServiceTypesSetAsTest());
        testCountries.put("CountryWithProviderWithServices_ButServiceTypesSetToTest2", getBelgiumWithConnective_ServiceTypesSetAsTest());
        testCountries.put("CountryWithProviderWithServices_ButStatusesSetToTest1", getAustriaWithDatakom_StatusesSetAsTest());
        testCountries.put("CountryWithProviderWithServices_ButStatusesSetToTest2", getBelgiumWithConnective_StatusesSetAsTest());
    }

    private static Country getCountryWithOneProvider() {
        Country country = new Country("OneProviderCountry", "1P");
        Provider provider = new Provider(country, 0, "Provider0", "ddd");
        Service service = new Service(provider, 0, "Service0", "T1", "status1");
        provider.getServiceTypes().addAll(List.of("ST1", "ST2"));
        service.getServiceTypes().addAll(List.of("ST1", "ST2"));
        provider.getServices().add(service);
        country.getProviders().add(provider);
        return country;
    }

    private static Country getAustriaWithDatakom() {
        Country country = new Country("Austria", "AT");
        TreeSet<String> serviceTypesDatakom = new TreeSet<>();
        serviceTypesDatakom.add("QCertESig");
        TreeSet<String> serviceTypesA_sign_premium_CA = new TreeSet<>();
        serviceTypesA_sign_premium_CA.add("QCertESig");
        TreeSet<Service> servicesDatakom = new TreeSet<>();
        Provider Datakom_Austria_GmbH = new Provider(country, 3, "Datakom Austria GmbH", "VATAT-U44837307", serviceTypesDatakom, servicesDatakom);
        Service A_sign_premium_CA = new Service(Datakom_Austria_GmbH, 1, "a-sign Premium CA", "http://uri.etsi.org/TrstSvc/Svctype/CA/QC", "withdrawn", serviceTypesA_sign_premium_CA);
        servicesDatakom.add(A_sign_premium_CA);
        country.getProviders().add(Datakom_Austria_GmbH);
        return country;
    }

    private static Country getBelgiumWithConnective() {
        Country country = new Country("Belgium", "Be");
        TreeSet<String> serviceTypesConnective = new TreeSet<>();
        serviceTypesConnective.add("QValQESig");
        serviceTypesConnective.add("QValQESeal");
        TreeSet<String> serviceTypesConnective_Validation_Service = new TreeSet<>();
        serviceTypesConnective_Validation_Service.add("QValQESig");
        serviceTypesConnective_Validation_Service.add("QValQESeal");
        TreeSet<Service> servicesConnective = new TreeSet<>();
        Provider Connective = new Provider(country, 13, "CONNECTIVE", "VATBE-0467046486", serviceTypesConnective, servicesConnective);
        Service Connective_Validation_Service = new Service(Connective, 1, "Connective Validation Service", "http://uri.etsi.org/TrstSvc/Svctype/QESValidation/Q", "granted", serviceTypesConnective_Validation_Service);
        servicesConnective.add(Connective_Validation_Service);
        country.getProviders().add(Connective);
        return country;
    }

    private static Country getAustriaWithDatakom_ServiceTypesSetAsTest() {
        Country country = new Country("Austria", "AT");
        TreeSet<String> serviceTypesDatakom = new TreeSet<>();
        serviceTypesDatakom.add("test1");
        TreeSet<String> serviceTypesA_sign_premium_CA = new TreeSet<>();
        serviceTypesA_sign_premium_CA.add("test1");
        TreeSet<Service> servicesDatakom = new TreeSet<>();
        Provider Datakom_Austria_GmbH = new Provider(country, 3, "Datakom Austria GmbH", "VATAT-U44837307", serviceTypesDatakom, servicesDatakom);
        Service A_sign_premium_CA = new Service(Datakom_Austria_GmbH, 1, "a-sign Premium CA", "http://uri.etsi.org/TrstSvc/Svctype/CA/QC", "withdrawn", serviceTypesA_sign_premium_CA);
        servicesDatakom.add(A_sign_premium_CA);
        country.getProviders().add(Datakom_Austria_GmbH);
        return country;
    }

    private static Country getBelgiumWithConnective_ServiceTypesSetAsTest() {
        Country country = new Country("Belgium", "Be");
        TreeSet<String> serviceTypesConnective = new TreeSet<>();
        serviceTypesConnective.add("test1");
        serviceTypesConnective.add("test2");
        TreeSet<String> serviceTypesConnective_Validation_Service = new TreeSet<>();
        serviceTypesConnective_Validation_Service.add("test1");
        serviceTypesConnective_Validation_Service.add("test2");
        TreeSet<Service> servicesConnective = new TreeSet<>();
        Provider Connective = new Provider(country, 13, "CONNECTIVE", "VATBE-0467046486", serviceTypesConnective, servicesConnective);
        Service Connective_Validation_Service = new Service(Connective, 1, "Connective Validation Service", "http://uri.etsi.org/TrstSvc/Svctype/QESValidation/Q", "granted", serviceTypesConnective_Validation_Service);
        servicesConnective.add(Connective_Validation_Service);
        country.getProviders().add(Connective);
        return country;
    }

    private static Country getAustriaWithDatakom_StatusesSetAsTest() {
        Country country = new Country("Austria", "AT");
        TreeSet<String> serviceTypesDatakom = new TreeSet<>();
        serviceTypesDatakom.add("QCertESig");
        TreeSet<String> serviceTypesA_sign_premium_CA = new TreeSet<>();
        serviceTypesA_sign_premium_CA.add("QCertESig");
        TreeSet<Service> servicesDatakom = new TreeSet<>();
        Provider Datakom_Austria_GmbH = new Provider(country, 3, "Datakom Austria GmbH", "VATAT-U44837307", serviceTypesDatakom, servicesDatakom);
        Service A_sign_premium_CA = new Service(Datakom_Austria_GmbH, 1, "a-sign Premium CA", "http://uri.etsi.org/TrstSvc/Svctype/CA/QC", "test1", serviceTypesA_sign_premium_CA);
        servicesDatakom.add(A_sign_premium_CA);
        country.getProviders().add(Datakom_Austria_GmbH);
        return country;
    }

    private static Country getBelgiumWithConnective_StatusesSetAsTest() {
        Country country = new Country("Belgium", "Be");
        TreeSet<String> serviceTypesConnective = new TreeSet<>();
        serviceTypesConnective.add("QValQESig");
        serviceTypesConnective.add("QValQESeal");
        TreeSet<String> serviceTypesConnective_Validation_Service = new TreeSet<>();
        serviceTypesConnective_Validation_Service.add("QValQESig");
        serviceTypesConnective_Validation_Service.add("QValQESeal");
        TreeSet<Service> servicesConnective = new TreeSet<>();
        Provider Connective = new Provider(country, 13, "CONNECTIVE", "VATBE-0467046486", serviceTypesConnective, servicesConnective);
        Service Connective_Validation_Service = new Service(Connective, 1, "Connective Validation Service", "http://uri.etsi.org/TrstSvc/Svctype/QESValidation/Q", "test1", serviceTypesConnective_Validation_Service);
        servicesConnective.add(Connective_Validation_Service);
        country.getProviders().add(Connective);
        return country;
    }

}
