package com.trustedservices;

import com.trustedservices.domain.Country;
import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.web.TrustedListJsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Help {
    private static TrustedList wholeList;

    private static void constructWholeList() {
        try {
            TrustedListJsonBuilder builder = new TrustedListJsonBuilder();

            Path countries = Path.of("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/countryListDummy.json");
            Path providers = Path.of("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/providerListDummy.json");
            builder.setCountriesJson(Files.readString(countries));
            builder.setProvidersJson(Files.readString(providers));

            wholeList = builder.build();
        } catch (IOException e) {
            System.err.println("Error reading file\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static TrustedList getWholeList() {
        if (wholeList == null) {
            constructWholeList();
        }
        return wholeList.clone();
    }

    public static Country getCountryN(int indexOfTheCountry) {
        return wholeList.getCountries().stream().toList().get(indexOfTheCountry);
    }
}
