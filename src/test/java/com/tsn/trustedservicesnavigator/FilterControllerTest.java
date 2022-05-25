package com.tsn.trustedservicesnavigator;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class FilterControllerTest {

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


    @Test
    void doesGetFilteredDataFromWork()throws IOException {
        //preparazione
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

}