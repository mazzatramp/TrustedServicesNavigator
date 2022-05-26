package com.tsn.trustedservicesnavigator;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FilterControllerTest {
    //APPLYFILTERSTO
    @Nested
    class ApplyFiltersTo{
        @Test
        //METTO UN PROVIDER NELLA WHITELIST,FILTRO E CONTROLLO CHE LA TLISTA ABBIA SOLO QUEL PROVIDER
        void provaconunprovider() throws IOException {
            TrustedList lista = Help.getWholeList();
            FilterController fc = new FilterController();
            List<String> listaProv = new ArrayList<>();
            String providerExpected= ("A-Trust Gesellschaft für Sicherheitssysteme im elektronischen Datenverkehr GmbH");
            listaProv.add("A-Trust Gesellschaft für Sicherheitssysteme im elektronischen Datenverkehr GmbH");
            fc.getProviderFilter().setWhitelist(listaProv);
            fc.applyFiltersTo(lista);
            System.out.println(lista.getCountries().get(0).getProviders().get(0).getName());
            assertEquals(providerExpected,lista.getCountries().get(0).getProviders().get(0).getName());
        }
        //METTO UN PROVIDER NELLA WHITELIST,FILTRO E CONTROLLO CHE LA TLISTA ABBIA SOLO LA COUNTRY DI QUEL PROVIDER

        @Test
        void mettounproviderdiunostatoevedochequellostatocisia() throws IOException {
            TrustedList lista = Help.getWholeList();
            FilterController fc = new FilterController();
            List<String> listaProv = new ArrayList<>();
            String countryexpected= ("Austria");
            listaProv.add("A-Trust Gesellschaft für Sicherheitssysteme im elektronischen Datenverkehr GmbH");
            fc.getProviderFilter().setWhitelist(listaProv);
            fc.applyFiltersTo(lista);
            System.out.println(lista.getCountries().get(0).getName());
            if (!(lista.getCountries().size()==1)){
                fail("dovrebbe esserci solo l'austria");
            }
            assertEquals(countryexpected,lista.getCountries().get(0).getName());
        }


        @Test
        //QUESTO FALLISCE PERCHE NON E STATO CORRETTO IL BUG
        void mettounservicetypeecontrollochelalistaabbiaquesiservicetypes() throws IOException {
            TrustedList lista = Help.getWholeList();
            FilterController fc = new FilterController();
            List<String> listaServiceTypes = new ArrayList<>();
            Set<String> expectedservicetype= new HashSet<>();
            expectedservicetype.add("QCertESeal");
            listaServiceTypes.add("QCertESeal");
            fc.getProviderFilter().setWhitelist(listaServiceTypes);
            fc.applyFiltersTo(lista);
            System.out.println(lista.getServiceTypes());
            assertEquals(expectedservicetype,lista.getServiceTypes());
            //DOVREBBE ESSERE GIUSTO USARE ASSERT EQUALS PER I SET MA NON SONO SICURO AL 100%
        }
        @Test
            //QUESTO FALLISCE PERCHE NON E STATO CORRETTO IL BUG
        void mettounostatuspercontrollochelalistaabbiaquellostatus() throws IOException {
            TrustedList lista = Help.getWholeList();
            FilterController fc = new FilterController();
            List<String> listaStatuses = new ArrayList<>();
            Set<String> expectedstatuses= new HashSet<>();
            expectedstatuses.add("http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/withdrawn");
            listaStatuses.add("http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/withdrawn");
            fc.getProviderFilter().setWhitelist(listaStatuses);
            fc.applyFiltersTo(lista);
            System.out.println(lista.getStatuses());
            assertEquals(expectedstatuses,lista.getStatuses());
            //DOVREBBE ESSERE GIUSTO USARE ASSERT EQUALS PER I SET MA NON SONO SICURO AL 100%
        }
    //INSERISCI LISTA NULLA/VUOTA/PIENA A META
    //E CONTROLLA CHE FUNZI COJN UN FILTRO PROVIDER, UN FILTRO STATUS, SERVICE TYPES
    //E TUTTE LE COMBINAZIONI DI QUESTI FILTRI. POI CI SONO ANCHE I CASI LIMITE TIPO
    //SE I FILTRI SONO NULLI O VUOTI
    //CONTROLLA SIANO STATE RIMOSSE ENTITA VUOTE
    }

    //WOULDHAVEZEROSERVICES
    @Nested
    class WouldHaveZeroServices{
        //Provo a mettere un filtro con un provider e poi metto un tipodiservizio che quel provider non ha
        @Test
        void mettoprovidercomefiltroepoiunoservicetypenondiquelprovider() throws IOException {
            TrustedList lista = Help.getWholeList();
            FilterController fc = new FilterController();
            List<String> listaProv = new ArrayList<>();
            listaProv.add("PrimeSign GmbH");
            fc.getProviderFilter().setWhitelist(listaProv);
            fc.applyFiltersTo(lista);
            String nuovoelemento ="QWAC";
            boolean hazero = fc.wouldHaveZeroServices(fc.getServiceTypeFilter(),nuovoelemento);

            assertEquals(true, hazero);
            //System.out.println(lista.getCountries().get(0).getName());

            //assertEquals(countryexpected,lista.getCountries().get(0).getName());

        }
        @Test
        void mettoprovidercomefiltroepoiunoservicetypediquelprovider() throws IOException {
            TrustedList lista = Help.getWholeList();
            FilterController fc = new FilterController();
            List<String> listaProv = new ArrayList<>();
            listaProv.add("PrimeSign GmbH");
            fc.getProviderFilter().setWhitelist(listaProv);
            fc.applyFiltersTo(lista);
            String nuovoelemento ="QCertESig";
            boolean hazero = fc.wouldHaveZeroServices(fc.getServiceTypeFilter(),nuovoelemento);

            assertEquals(false, hazero);
            //System.out.println(lista.getCountries().get(0).getName());

            //assertEquals(countryexpected,lista.getCountries().get(0).getName());

        }

        @Test
        void mettoprovidercomefiltroepoiunostatusnondiquelprovider() throws IOException {
            TrustedList lista = Help.getWholeList();
            FilterController fc = new FilterController();
            List<String> listaProv = new ArrayList<>();
            listaProv.add("PrimeSign GmbH");
            fc.getProviderFilter().setWhitelist(listaProv);
            fc.applyFiltersTo(lista);
            String nuovoelemento ="http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/withdrawn";
            boolean hazero = fc.wouldHaveZeroServices(fc.getStatusFilter(),nuovoelemento);
            System.out.println(lista.getStatuses());

            assertEquals(true, hazero);
            System.out.println(lista.getCountries());

            //assertEquals(countryexpected,lista.getCountries().get(0).getName());

        }
        @Test
        void mettoprovidercomefiltroepoiunostatusdiquelprovider() throws IOException {
            TrustedList lista = Help.getWholeList();
            FilterController fc = new FilterController();
            List<String> listaProv = new ArrayList<>();
            listaProv.add("PrimeSign GmbH");
            fc.getProviderFilter().setWhitelist(listaProv);
            fc.applyFiltersTo(lista);
            String nuovoelemento ="http://uri.etsi.org/TrstSvc/TrustedList/Svcstatus/granted";
            boolean hazero = fc.wouldHaveZeroServices(fc.getStatusFilter(),nuovoelemento);
            System.out.println(lista.getStatuses());

            assertEquals(false, hazero);
            //System.out.println(lista.getCountries().get(0).getName());

            //assertEquals(countryexpected,lista.getCountries().get(0).getName());

        }
        //DOPO DEVO ANCHE CONTROLLARE CHE MESSO UNO STATUS

    }


    /*

    //non ho ben capito come testare i metodi privati, le classi di test hanno privilegi speciali?

/*@Test
    void doesFiltersMethodWork(){
}*/

  /*  @Test
    void doesSetCountryProviderWhitelistWork()
    // non ancora funzionante manca la parte di test
    {
        //preparazione
        FilterController fc = new FilterController();
        Map<String, List<String>> mappa = new LinkedHashMap<>();
        List<String> provider = new ArrayList<>();
        provider.add("JCC PAYMENT SYSTEMS LTD");
        mappa.put("Cyprus",provider);

        //metodo da testare
        fc.setCountryProviderWhitelist(mappa);

        //test
        //se nelle nuove versioni di codice posso recuperare la whitelist confronto con expected value
        //altrimenti devo scaricare tutti i dati, filtrarli e vedere che corrispondano con l'expected value
    }
    */

   /* @Test
    void doesSetServiceTypeFilterWork()
    // non ancora funzionante manca la parte di test
    {
        //preparazione
        FilterController fc = new FilterController();
        List<String> serviceTypes = new ArrayList<>();
        serviceTypes.add("QCertESig");
        serviceTypes.add("QCertESeal");

        //metodo da testare
        fc.setServiceTypeFilter(serviceTypes);

        //test
        //se nelle nuove versioni di codice posso recuperare la whitelist confronto con expected value
        //altrimenti devo scaricare tutti i dati, filtrarli e vedere che corrispondano con l'expected value

    }*/

/*
    @Test
    void GetFilteredDataFrom_WholeListWithAWhiteList_ReturnsWhitelist()throws IOException {
        //arrange
        //BRUTTO DOVERE FARE DIVERSE LISTE PER PAESI PROVIDER ETC SAREBBE BELLO LA CLASSE TRUSTELIST AVESSE COSTRUTTORE
        //sarebbe meglio mettere piu paesi e piu providers
        List<String> serviceTypes = new ArrayList<>();
        serviceTypes.add("QCertESig");
        serviceTypes.add("QCertESeal");
        FilterController fc = new FilterController();
        fc.getServiceTypeFilter().setWhitelist(serviceTypes);

        List<String> providers = new ArrayList<>();
        providers.add("JCC PAYMENT SYSTEMS LTD");
        fc.getProviderFilter().setWhitelist(providers);

        TrustedList initialList=new TrustedList();
        initialList.downloadApiData();

        //metodo da testare
        TrustedList filteredList = initialList.clone();
        fc.applyFiltersTo(filteredList);

        //test
        //testare che lista e whitelist siano identiche non lo posso fare
        List<String> expectedCountries=new ArrayList<>();
        expectedCountries.add("Cyprus");
        //System.out.println(paesi.get(0));
        //System.out.println(filteredList.getCountries().get(0).getName());

        //test che combacino i paesi
        if (!(filteredList.getCountries().get(0).getName().equals(expectedCountries.get(0)))){
            fail("I paesi non combaciano"); //italiano o inglese?
        }
        //test che combacino i providers
        filteredList.getCountries().forEach(country -> {
            country.getProviders().forEach(provider -> {
                if (!provider.getName().equals("JCC PAYMENT SYSTEMS LTD"))
                {fail("i providers non combaciano");}
            });
        });
        //test servicesTypes non lo posso fare perchè non si aggiornano i serviceTypes filtrati
        //System.out.println(filteredList.getServiceTypes());
        //System.out.println(filteredList.getCountries());
        //assertEquals(filteredList.getServiceTypes(),servtypes);


    }

    @Test
    void GetFilteredDataFrom_NullArgumentWithAWhiteList_Returns()throws IOException {
        //arrange
        //BRUTTO DOVERE FARE DIVERSE LISTE PER PAESI PROVIDER ETC SAREBBE BELLO LA CLASSE TRUSTELIST AVESSE COSTRUTTORE
        //sarebbe meglio mettere piu paesi e piu providers
        List<String> serviceTypes = new ArrayList<>();
        serviceTypes.add("QCertESig");
        serviceTypes.add("QCertESeal");
        FilterController fc = new FilterController();
        fc.setServiceTypeWhitelist(serviceTypes);

        Map<String, List<String>> mappaCountriesProviders = new LinkedHashMap<>();
        List<String> providers = new ArrayList<>();
        providers.add("JCC PAYMENT SYSTEMS LTD");
        mappaCountriesProviders.put("Cyprus",providers);
        fc.setCountryProviderWhitelist(mappaCountriesProviders);

        TrustedList initialList= null;
        assertThrows(NullPointerException.class,() ->  fc.getFilteredDataFrom(initialList));



    }*/


}