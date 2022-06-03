package com.trustedservices.domain;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Service implements Cloneable, Comparable<Service>, TrustedListEntity {
    private Provider provider;

    private int id;
    private String name;
    private String type;
    private String status;
    private List<String> serviceTypes;

    @JsonCreator
    public Service(
            @JsonProperty("serviceId") int serviceId,
            @JsonProperty("serviceName") String name,
            @JsonProperty("type") String type,
            @JsonProperty("currentStatus") String statusUrl,
            @JsonProperty("qServiceTypes") List<String> serviceTypes
    ) {
        this.id = serviceId;
        this.name = name;
        this.type = type;
        this.status = getLastPartFromUrl(statusUrl);
        this.serviceTypes = serviceTypes;
    }

    private String getLastPartFromUrl(String statusUrl) {
        String[] splitUrl = statusUrl.split("/");
        return splitUrl[splitUrl.length-1];
    }

    public int getId() {
        return id;
    }

    @JsonSetter
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getInformation() {
        List<String> information = new ArrayList<>(3);
        information.add(name);
        information.add(serviceTypes.toString());
        information.add(status);
        return information;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(List<String> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return this.name.equals(service.name) && this.id == service.id;
    }

    @Override
    public String toString() {
        return "Service{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", serviceTypes='" + serviceTypes + '\'' +
                '}';
    }

    @Override
    public Service clone() {
        try {
            return (Service) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public int compareTo(Service service) {
        int providerComparison = this.getProvider().compareTo(service.getProvider());
        int idComparison = Integer.compare(this.getId(), service.getId());
        if (providerComparison != 0)
            return providerComparison;
        else
            return idComparison;
    }
}
