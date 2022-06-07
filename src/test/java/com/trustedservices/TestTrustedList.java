package com.trustedservices;

import com.trustedservices.domain.Country;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.Service;
import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.web.*;

import javax.swing.text.html.ListView;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

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

        return building;
    }


    public static TrustedList getActualApiTrustedList() {
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
        testCountries.put("countryWithNoProviders", new Country("EmptyCountry", "0P"));
        testCountries.put("countryWithOneProvider", getCountryWIthOneProvider());
    }

    private static Country getCountryWIthOneProvider() {
        Country country = new Country("OneProviderCountry", "1P");
        Provider provider = new Provider(country, 0, "Provider0", "ddd");
        Service service = new Service(provider, 0, "Service0", "T1", "status1");
        provider.getServiceTypes().addAll(List.of("ST1", "ST2"));
        service.getServiceTypes().addAll(List.of("ST1", "ST2"));
        provider.getServices().add(service);
        country.getProviders().add(provider);
        return country;
    }
}
