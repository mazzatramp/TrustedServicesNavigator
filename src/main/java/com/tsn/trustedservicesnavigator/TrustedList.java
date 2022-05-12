package com.tsn.trustedservicesnavigator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrustedList {
    private static TrustedList instance;
    private List<Country> countries;
    private static String COUNTRIES_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/countries_list";
    private static String PROVIDER_API_ENDPOINT = "https://esignature.ec.europa.eu/efda/tl-browser/api/v1/search/tsp_list";

    public static TrustedList getInstance() {
        if (instance == null) {
            instance = new TrustedList();
        }
        return instance;
    }


    public List<Country> getCountries() {
        return countries;
    }

    public void fillCountriesData() throws Exception {

        URL callApi = new URL(COUNTRIES_API_ENDPOINT);
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) callApi.openConnection();
        } catch (IOException e) {
            System.err.println("unable to connect to the URL");
            throw e;
        }
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Request", "Mozilla 5.0");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Connessione andata a buon fine");
            try {
                ObjectMapper mapper = new ObjectMapper();
                BufferedReader jackiefile = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputStr;
                StringBuffer response = new StringBuffer();

                while ((inputStr = jackiefile.readLine()) != null) {
                    response.append(inputStr);
                }
                System.out.println(response.toString());
                countries = mapper.readValue(response.toString(), mapper.getTypeFactory().constructCollectionType(List.class, Country.class));
            } catch (Exception e) {
                System.err.println("json parsing impossible");
                throw e;
            }
            countries.forEach(System.out::println);
        }
        callApi = new URL(PROVIDER_API_ENDPOINT);
        try {
            connection = (HttpURLConnection) callApi.openConnection();
        } catch (IOException e) {
            System.err.println("unable to connect to the URL");
            throw e;
        }
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Request", "Mozilla 5.0");

        responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Connessione andata a buon fine");
            try {
                ObjectMapper mapper = new ObjectMapper();
                BufferedReader jackiefile = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputStr;
                StringBuffer response = new StringBuffer();

                while ((inputStr = jackiefile.readLine()) != null) {
                    response.append(inputStr);
                }
                System.out.println(response.toString());
                List<Provider> providers = mapper.readValue(response.toString(), mapper.getTypeFactory().constructCollectionType(List.class, Provider.class));
                providers.forEach(System.out::println);
            } catch (Exception e) {
                System.err.println("unable to parse providers");
                throw e;
            }
        }
    }
}