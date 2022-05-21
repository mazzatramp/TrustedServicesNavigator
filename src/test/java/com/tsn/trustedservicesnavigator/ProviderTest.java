package com.tsn.trustedservicesnavigator;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class ProviderTest {

    @Test
    void doesSetServiceTypesWork() // da mettere apposto
    {
        //preparazione
        //devo capire se va bene che inserisco così i dati oppure se sia meglio che utilizzo strumenti automatici
        List<String> listOfServiceTypesOfAziendaZero= new ArrayList<String>();
        listOfServiceTypesOfAziendaZero.add("QCertESeal");
        listOfServiceTypesOfAziendaZero.add("QCertESig");
        listOfServiceTypesOfAziendaZero.add("QTimestamp");

        List<Service> listOfServicesOfAziendaZero= new ArrayList<Service>();
        listOfServicesOfAziendaZero.add(new Service(
                1,
                "OID.2.5.4.97=VATIT-05018720283, CN=Azienda Zero CA Qualificata eIDAS 1, OU=TSP, O=Azienda Zero, C=IT",
                "http://uri.etsi.org/TrstSvc/Svctype/CA/QC",
                "http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/withdrawn",
                listOfServiceTypesOfAziendaZero));
        //Azienda zero ha anche un altro servizio ma non lo metto adesso

        Provider AziendaZero= new Provider("IT",
                49,
                "Azienda Zero",
                "VATIT-05018720283",
                listOfServiceTypesOfAziendaZero,
                listOfServicesOfAziendaZero);

        List<String> listIWantToSetToAziendaZero = new ArrayList<String>();
        listIWantToSetToAziendaZero.add("QCertESeal");
        listIWantToSetToAziendaZero.add("QCertESig");
        listIWantToSetToAziendaZero.add("QTimestamp");
        listIWantToSetToAziendaZero.add("QWAC");

        List<String> listThatShouldBeEqualToServiceTypesOfAziendaZero= new ArrayList<>();
        listThatShouldBeEqualToServiceTypesOfAziendaZero.add("QCertESeal");
        listThatShouldBeEqualToServiceTypesOfAziendaZero.add("QCertESig");
        listThatShouldBeEqualToServiceTypesOfAziendaZero.add("QTimestamp");
        listThatShouldBeEqualToServiceTypesOfAziendaZero.add("QWAC");

        //metodo che voglio testare
        AziendaZero.setServiceTypes(listIWantToSetToAziendaZero);

        //test
        assertLinesMatch(listThatShouldBeEqualToServiceTypesOfAziendaZero,
                AziendaZero.getServiceTypes());
    }
    @Test
    void doesSetServiceTypesWorkWithWrongInputs() // non saprei come farlo
    {}

}