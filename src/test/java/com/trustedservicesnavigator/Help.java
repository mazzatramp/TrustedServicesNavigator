package com.trustedservicesnavigator;

import com.trustedservicesnavigator.domain.Country;
import com.trustedservicesnavigator.domain.TrustedList;

import java.io.IOException;

public class Help {
    //DEVO TESTARE GLI HELP?
    //SAREBBE MEGLIO AVERE QUESTI METODI NEL CODICE DEL PROGRAMMA
    private static TrustedList wholeList;
    private static void constructWholeList() throws IOException {
        wholeList= new TrustedList();
        wholeList.downloadApiData();
    }
    public static TrustedList getWholeList() throws IOException {
        if(wholeList==null) {constructWholeList();}
        return wholeList.clone();
    }
    public static Country getCountryN(int indexOfTheCountry) throws IOException {
        TrustedList initialList = Help.getWholeList();
        Country aCountry = initialList.getCountries().get(indexOfTheCountry);
        return aCountry;
    }

    //METTERE GETPROVIDERN GETSERVICETYPEN GETSTATUSN
}
