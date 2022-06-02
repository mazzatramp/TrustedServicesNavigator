package com.trustedservices;

import com.trustedservices.domain.Country;
import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.web.*;

public class Help {
    private static TrustedList wholeList;

    private static void constructWholeList() {
        TrustedJsonBuilder builder = new TrustedJsonBuilder();
        wholeList = builder.build();
    }

    public static TrustedList getWholeList() {
        if (wholeList == null) {
            constructWholeList();
        }
        return wholeList.clone();
    }

    public static Country getCountryN(int indexOfTheCountry) {
        return wholeList.getCountries().get(indexOfTheCountry);
    }
}
