package com.tsn.trustedservicesnavigator;

import java.util.ArrayList;

public class TrustedList {
    private static TrustedList instance;
    private ArrayList<Country> countries;

    public static TrustedList getInstance() {
        if (instance == null) {
            instance = new TrustedList();
        }
        return instance;
    }

    private TrustedList() {
        countries = new ArrayList<>(0);
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }
}
