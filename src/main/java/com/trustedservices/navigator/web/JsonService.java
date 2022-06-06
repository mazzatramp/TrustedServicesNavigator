package com.trustedservices.navigator.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.Service;

import java.util.List;

/**
 * Class created to get the attributes of a Service from a json file and create a new Service safely (with this system it's hard
 * to modify items of the TrustedList, or add external objects).
 */
public class JsonService {
    private int tspId;
    private int serviceId;
    private String countryCode;
    private String serviceName;
    private String type;
    private String statusAsUrl;
    private String tob;
    private List<String> serviceTypes;

    /**
     * @param provider creates a Service associated to a given Provider and adds it to the provider's list of services
     */
    public void createServiceIn(Provider provider) {
        Service createdService = new Service(provider, serviceId, serviceName, type, getLastPartFromUrl(statusAsUrl));
        createdService.getServiceTypes().addAll(serviceTypes);
        provider.getServices().add(createdService);
    }

    /**
     * @param statusUrl gets an url string and splits it in a string array element for each "/" it contains.
     * @return the last element of the array, the real status of the service
     */
    private String getLastPartFromUrl(String statusUrl) {
        String[] splitUrl = statusUrl.split("/");
        return splitUrl[splitUrl.length-1];
    }

    /**
     * Reads the parameters from the json property to identify the attributes of a service and constructs a new JsonService
     */
    @JsonCreator
    public JsonService(
            @JsonProperty("serviceId") int serviceId,
            @JsonProperty("tspId") int tspId,
            @JsonProperty("countryCode") String countryCode,
            @JsonProperty("serviceName") String name,
            @JsonProperty("type") String type,
            @JsonProperty("currentStatus") String statusUrl,
            @JsonProperty("tob") String tob,
            @JsonProperty("qServiceTypes") List<String> serviceTypes
    ) {
        this.serviceId = serviceId;
        this.tspId = tspId;
        this.countryCode = countryCode;
        this.serviceName = name;
        this.type = type;
        this.statusAsUrl = statusUrl;
        this.tob = tob;
        this.serviceTypes = serviceTypes;
    }

    public int getServiceId() {
        return serviceId;
    }
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getStatusAsUrl() {
        return statusAsUrl;
    }
    public void setStatusAsUrl(String statusAsUrl) {
        this.statusAsUrl = statusAsUrl;
    }

    public List<String> getServiceTypes() {
        return serviceTypes;
    }
    public void setServiceTypes(List<String> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public int getTspId() {
        return tspId;
    }
    public void setTspId(int tspId) {
        this.tspId = tspId;
    }

    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getTob() {
        return tob;
    }
    public void setTob(String tob) {
        this.tob = tob;
    }
}
