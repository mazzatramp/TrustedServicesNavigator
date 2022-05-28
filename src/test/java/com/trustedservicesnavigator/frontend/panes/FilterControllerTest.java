package com.trustedservicesnavigator.frontend.panes;

import com.trustedservicesnavigator.Help;
import com.trustedservicesnavigator.domain.TrustedList;
import com.trustedservicesnavigator.frontend.FilterController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A FilterController") //SULLA GUIDA DI JUNIT I DISPLAYNAME SONO UN PO DIVERSI, BISOGNA DECIDERE QUALE USARE //When I have a FilterController
class FilterControllerTest {
    FilterController fc;
    //CHE DEBBA FARE ANCHE TEST SENZA AVERE INSTANZIATO FC?
    @Test
    @DisplayName("is instantiated with new FilterController()")
    void isInstantiatedWithNew() {
        new FilterController();
    }
    @Nested
    @DisplayName("when new")
    class WhenNew
    {
        @BeforeEach
        void createAFilterController(){fc = new FilterController();
        }

        @DisplayName("and I use the method applyFiltersTo")
        @Nested
        class ApplyFiltersTo{
            @DisplayName("with a whole list as an argument")
            @Nested
            class WholeListAsArgument{
                TrustedList lista;
                @BeforeEach
                void createAWholeList() throws IOException {
                    lista = Help.getWholeList(); //SICURO CHE DOPO FILTRO IN UN TEST QUA SOTTO E LA LISTA NON CAMBIA?
                }
                @DisplayName("and I put as the first filter a provider in the list whitelist")
                @Nested
                class FirstFilterIsAProvider{
                    String providerExpected= ("A-Trust Gesellschaft für Sicherheitssysteme im elektronischen Datenverkehr GmbH");
                    @DisplayName("I should have only that provider in the filtered list")
                    @Test
                    void FirstProviderInTheWhitelist() throws IOException {
                        //arrange
                        //FilterController fc = new FilterController();
                        List<String> listaProv = new ArrayList<>();
                        listaProv.add(providerExpected);
                        fc.getProviderFilter().setWhitelist(listaProv);

                        //act
                        fc.applyFiltersTo(lista);

                        //assert
                        assertEquals(providerExpected,lista.getCountries().get(0).getProviders().get(0).getName());
                        //DEVO CONTROLLARE CHE LA LISTA ABBIA SOLO QUEL PROVIDER
                        lista.getCountries().forEach(country -> {
                            country.getProviders().forEach(provider -> {
                                assertTrue(provider.getName().equals(providerExpected));
                            });
                        });
                    }
                    @DisplayName("and I put as a second filter a ServiceType in the list whitelist ")
                    @Nested
                    class SecondFilterIsAServiceType{
                        String expectedServiceType = "QCertESeal";
                        @DisplayName("I should have only services of that provider of at least that service type in the filtered list")
                        @Test
                        void SecondProviderInTheWhitelist() throws IOException {
                        //System.out.println(lista.getCountries().get(0).getProviders());
                        //arrange
                        //FilterController fc = new FilterController();
                        List<String> listaServiceTypes = new ArrayList<>();
                        listaServiceTypes.add(providerExpected);
                        fc.getServiceTypeFilter().setWhitelist(listaServiceTypes);

                        //act
                        fc.applyFiltersTo(lista);


                        //assert
                        //DEVO CONTROLLARE CHE LA LISTA ABBIA SOLO QUEL PROVIDER
                            lista.getCountries().forEach(country -> {
                                country.getProviders().forEach(provider -> {
                                    System.out.println(provider.getName());
                                    assertTrue(provider.getName().equals(providerExpected));
                                    provider.getServices().forEach(service -> {
                                        System.out.println(service.getServiceTypes());
                                        assertTrue(service.getServiceTypes().contains(expectedServiceType));
                                    });
                                });
                            });
                    }}
                }

                @DisplayName("and I put as the first filter a service type in the list whitelist")
                @Nested
                class FirstFilterIsAServiceType{
                    String expectedservicetype= "QCertESeal";
                    @DisplayName("I should have only services that have at least that service type")
                    @Test
                    void FirstFilterIsAServiceType() throws IOException {
                        //FilterController fc = new FilterController();
                        List<String> listaServiceTypes = new ArrayList<>();
                        listaServiceTypes.add(expectedservicetype);
                        fc.getServiceTypeFilter().setWhitelist(listaServiceTypes);

                        fc.applyFiltersTo(lista);

                        lista.getCountries().forEach(country -> {
                            country.getProviders().forEach(provider -> {
                                provider.getServices().forEach(service -> {
                                    assertTrue(lista.getServiceTypes().contains(expectedservicetype));
                                    if(!(lista.getServiceTypes().contains(expectedservicetype))){
                                        fail("un servizio non contiene QCertESeal"); //DECIDERE SE E' MEGLIO FAIL O ASSERTTRUE
                                    }
                                });
                            });
                        });
                        //assertTrue(lista.getServiceTypes().contains(expectedservicetype));

                        //DOVREBBE ESSERE GIUSTO USARE ASSERT EQUALS PER I SET MA NON SONO SICURO AL 100%

                    }

                }

                @DisplayName("and I put as the first filter a status in the list whitelist")
                @Nested
                class FirstFilterIsAStatus{
                    //SIAMO SICURI CI SIA UNA CORRISPONDENZA UNO A UNO FRA SERVIZI E STATUSES?
                    String expectedstatuses = "withdrawn";
                    @DisplayName("I should only have services with that status")
                    @Test
                    void FirstFilterIsA() throws IOException {
                        //TrustedList lista = Help.getWholeList();
                        //FilterController fc = new FilterController();
                        List<String> listaStatuses = new ArrayList<>();
                        listaStatuses.add(expectedstatuses);
                        fc.getStatusFilter().setWhitelist(listaStatuses);

                        fc.applyFiltersTo(lista);
                        //lista.constructMetadata();

                        lista.getCountries().forEach(country -> {
                            country.getProviders().forEach(provider -> {
                                provider.getServices().forEach(service -> {
                                    assertTrue(service.getStatus().equals(expectedstatuses));
                                });
                            });
                        });
                        //DOVREBBE ESSERE GIUSTO USARE ASSERT EQUALS PER I SET MA NON SONO SICURO AL 100%
                        //HO NOTATO CHE SE UNO METTE UN FILTRO CHE NON ESISTE DA LISTA VUOTA, VOGLIAMO CHE SIA QUESTO IL COMPARTAMENTO DEL CODICE?
                    }
                }



             /* DA QUANTO HO CAPITO QUESTO NON E' SEMPRE VERO PERCHE' CI SONO PROVIDER CON PIU' COUNTRIES
             @DisplayName(" and a provider in the list whitelist, i should have only the country of that provider")
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
            }*/

                //METTERE ANCHE COMBINAZIONE DI FILTRI SENSATA
                //METTERE  ANCHE COMBINAZIONE DI FILTRI INSENSATA
                //METTERE COMBINAZIONE DI FILTRI SEQUENZIALE SENSATA
                //METTRE COMBINAZIONE DI FILTRI SEQUENZIALE INSENSATA
                //PROVARE A METTERE PER ESEMPIO UN STATUS NELLA WHITELIST DEI PROVIDER E VEDERE COSA SUCCEDE
            }

            //INSERISCI LISTA NULLA/VUOTA/PIENA A META
            //E CONTROLLA CHE FUNZI COJN UN FILTRO PROVIDER, UN FILTRO STATUS, SERVICE TYPES
            //E TUTTE LE COMBINAZIONI DI QUESTI FILTRI. POI CI SONO ANCHE I CASI LIMITE TIPO
            //SE I FILTRI SONO NULLI O VUOTI
            //CONTROLLA SIANO STATE RIMOSSE ENTITA VUOTE
        }

        @DisplayName("and I use the method wouldHaveZeroServices")
        @Nested
        class WouldHaveZeroServices{
            //NON CONTINUO A MIGLIORARE IL TEST PER PERICOLO CHE WOULDHAVEZEROSERVICE DIVENTI PRIVATO
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
                String nuovoelemento ="withdrawn";
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
                String nuovoelemento ="granted";
                boolean hazero = fc.wouldHaveZeroServices(fc.getStatusFilter(),nuovoelemento);
                System.out.println(lista.getStatuses());

                assertEquals(false, hazero);
                //System.out.println(lista.getCountries().get(0).getName());

                //assertEquals(countryexpected,lista.getCountries().get(0).getName());

            }
            //DOPO DEVO ANCHE CONTROLLARE CHE MESSO UNO STATUS

        }
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