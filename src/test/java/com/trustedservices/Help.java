package com.trustedservices;

import com.trustedservices.domain.Country;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.Service;
import com.trustedservices.domain.TrustedList;
import com.trustedservices.navigator.web.TrustedDummyBuilder;
import com.trustedservices.navigator.web.TrustedListApiBuilder;
import com.trustedservices.navigator.web.TrustedListBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Help {
    //DEVO TESTARE GLI HELP?
    //SAREBBE MEGLIO AVERE QUESTI METODI NEL CODICE DEL PROGRAMMA
    private static TrustedList wholeList;
    /*
    //HA SENSO FARE COSI OPPURE MEGLIO CHIEDERE GET DA COUNTRIES.PROVIDER.SERVICE(0)?
    public static Service getServizio1AziendaZero(){
        Service servizio1AziendaZero = new Service("1", "OID.2.5.4.97=VATIT-05018720283, CN=Azienda Zero CA Qualificata eIDAS 1, OU=TSP, O=Azienda Zero, C=IT," ,
                "http://uri.etsi.org/TrstSvc/Svctype/CA/QC", "http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/withdrawn",
                new String[] {"QTimestamp"});
        return servizio1AziendaZero;
    }
    public static Service getServizio2AziendaZero(){
        Service servizio1AziendaZero = new Service("2", "OID.2.5.4.97=VATIT-05018720283, CN=Azienda Zero TSA Qualificata eIDAS 1, OU=TSP, O=Azienda Zero, C=IT" ,
                "http://uri.etsi.org/TrstSvc/Svctype/TSA/QTST", "http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/withdrawn"
                , new String[] {"QTimestamp"});
        return servizio1AziendaZero;
    }
    public static Provider getAziendaZero(){
        Provider aziendaZero = new Provider("IT", 49,"Azienda Zero","VATIT-05018720283",  )

    }
     */
    private static void constructWholeList() throws IOException {
        TrustedListBuilder builder = new TrustedDummyBuilder();
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
