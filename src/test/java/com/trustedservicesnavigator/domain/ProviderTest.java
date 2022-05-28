package com.trustedservicesnavigator.domain;

import com.trustedservicesnavigator.domain.Provider;
import com.trustedservicesnavigator.domain.Service;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
@Disabled
@DisplayName("A Provider")

class ProviderTest {
Provider provider;
    @Nested
    @DisplayName("when null")
    class WhenNull {
        @BeforeEach
        void setProviderNull() {
            provider = null;
        }
        @DisplayName("and I use the method equals(Object)")
        @Nested
        class Equals{}
        @DisplayName("and I use the method compareTo(Country)")
        @Nested
        class CompareTo{}
        @DisplayName("and I use the method clone()")
        @Nested
        class Clone{}
    }
    @Test
    @DisplayName("is instantiated with new Provider(String,Int, String,String,List,List)")
    void isInstantiatedWithNewProvider() {
        //DEVO METTERE DELLE VERE LISTE
        //O PROVARE A METTERE LISTE PIENE E LISTE VUOTE IN UN ALTRO CASO
        new Provider("IT", 49,
                "Azienda Zero"
                ,"VATIT-05018720283", new ArrayList<String>(), new ArrayList<Service>());
    }
    @Nested
    @DisplayName("when new")
    class WhenNew{
        @BeforeEach
        void createAProvider() {
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
            provider = new Provider("IT", 49,
                    "Azienda Zero"
                    ,"VATIT-05018720283",  listOfServiceTypesOfAziendaZero,  listOfServicesOfAziendaZero );
        }
        @DisplayName("and I use the method equals(Object)")
        @Nested
        class Equals{}
        @DisplayName("and I use the method compareTo(Country)")
        @Nested
        class CompareTo{}
        @DisplayName("and I use the method clone()")
        @Nested
        class Clone{}
        //DECIDERE SE TESTARE GET E SET
        //EQUALS
        //HASHCODE
        //TOSTRING
        //CLONE
        //COMPARETO
        @Test
        void doesSetServiceTypesWork()
        {
            //preparazione
            Provider providerToTest=provider;

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
            providerToTest.setServiceTypes(listIWantToSetToAziendaZero);

            //test
            assertLinesMatch(listThatShouldBeEqualToServiceTypesOfAziendaZero,
                    providerToTest.getServiceTypes());
        }
    }

    @Test
    void doesSetServiceTypesWorkWithWrongInputs()
    {}

}