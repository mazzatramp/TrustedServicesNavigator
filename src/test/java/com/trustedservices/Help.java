package com.trustedservices;

import com.trustedservices.domain.Country;
import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.web.TrustedListApiBuilder;
import com.trustedservices.navigator.web.TrustedListBuilder;

import java.io.IOException;

public class Help {
    //DEVO TESTARE GLI HELP?
    //SAREBBE MEGLIO AVERE QUESTI METODI NEL CODICE DEL PROGRAMMA
    private static TrustedList wholeList;
    private static void constructWholeList() throws IOException {
        TrustedListBuilder builder = new TrustedListApiBuilder();
        wholeList = builder.build();
    }

    public static TrustedList getWholeList() throws IOException {
        if(wholeList==null) {
            constructWholeList();
        }
        return wholeList.clone();
    }

    public static Country getCountryN(int indexOfTheCountry) throws IOException {
        TrustedList initialList = Help.getWholeList();
        return initialList.getCountries().get(indexOfTheCountry);
    }

    //METTERE GETPROVIDERN GETSERVICETYPEN GETSTATUSN
}
