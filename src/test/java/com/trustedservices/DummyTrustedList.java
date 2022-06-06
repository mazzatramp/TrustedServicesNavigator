package com.trustedservices;

import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.web.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DummyTrustedList {
    private static DummyTrustedList instance;
    private final TrustedList dummyTrustedList;

    public DummyTrustedList() {
        try {
            this.dummyTrustedList = setupFileBuilder().build();
        } catch (IOException e) {
            System.err.println("Error reading file\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static DummyTrustedList getInstance() {
        if (instance == null)
            instance = new DummyTrustedList();
        return instance;
    }

    @NotNull
    private static TrustedListJsonBuilder setupFileBuilder() throws IOException {
        TrustedListJsonBuilder builder = new TrustedListJsonBuilder();

        Path countries = Path.of("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/countryListDummy.json");
        Path providers = Path.of("src/test/java/com/trustedservices/navigator/dummyCopyTrustedList/providerListDummy.json");
        builder.setCountriesJson(Files.readString(countries));
        builder.setProvidersJson(Files.readString(providers));
        return builder;
    }

    public TrustedList getDummyTrustedList() {
        return dummyTrustedList.clone();
    }
}
