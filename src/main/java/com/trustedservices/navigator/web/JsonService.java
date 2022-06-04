package com.trustedservices.navigator.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trustedservices.domain.Provider;
import com.trustedservices.domain.Service;

import java.util.List;

public class JsonService {
    private int tspId;
    private int serviceId;
    private String countryCode;
    private String serviceName;
    private String type;
    private String statusAsUrl;
    private String tob;
    private List<String> serviceTypes;

    public void createServiceIn(Provider provider) {
        Service createdService = new Service(provider, serviceId, serviceName, type, getLastPartFromUrl(statusAsUrl));
        createdService.getServiceTypes().addAll(serviceTypes);
        provider.getServices().add(createdService);
    }

    private String getLastPartFromUrl(String statusUrl) {
        String[] splitUrl = statusUrl.split("/");
        return splitUrl[splitUrl.length-1];
    }

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
