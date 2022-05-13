package com.tsn.trustedservicesnavigator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Service {
    private Provider provider;

    private int serviceId;
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
        this.serviceId = serviceId;
        this.name = name;
        this.type = type;
        this.status = getStatusFromUrl(statusUrl);
        this.serviceTypes = serviceTypes;
    }

    private String getStatusFromUrl(String statusUrl) {
        String[] splittedUrl = statusUrl.split("/");
        return splittedUrl[splittedUrl.length-1];
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return serviceId == service.serviceId;
    }

    @Override
    public String toString() {
        return "Service{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", serviceTypes='" + serviceTypes + '\'' +
                '}';
    }
}
