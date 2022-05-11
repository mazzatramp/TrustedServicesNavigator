package com.tsn.trustedservicesnavigator;

import java.util.Objects;

public class Service {
    private int providerId;
    private int serviceId;
    private String countryCode;
    private String name;
    private String type;
    private String status;
    private String serviceType;

    public Service(int providerId, int serviceId, String countryCode, String name, String type, String status, String serviceType) {
        this.providerId = providerId;
        this.serviceId = serviceId;
        this.countryCode = countryCode;
        this.name = name;
        this.type = type;
        this.status = status;
        this.serviceType = serviceType;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return providerId == service.providerId && serviceId == service.serviceId && countryCode.equals(service.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerId, serviceId, countryCode);
    }

    @Override
    public String toString() {
        return "Service{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", serviceType='" + serviceType + '\'' +
                '}';
    }
}
