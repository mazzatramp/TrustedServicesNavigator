package com.trustedservices.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Service implements Cloneable, Comparable<Service>, TrustedListEntity {
    private final int id;
    private final String name;
    private final String type;
    private final String status;
    private final Set<String> serviceTypes;
    private Provider provider;

    public Service(Provider provider, int serviceId, String name, String type, String status, Set<String> serviceTypes) {
        this.provider = provider;
        this.id = serviceId;
        this.name = name;
        this.type = type;
        this.status = status;
        this.serviceTypes = serviceTypes;
    }

    public Service(Provider provider, int serviceId, String name, String type, String status) {
        this(provider, serviceId, name, type, status, new HashSet<>());
    }

    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getHumanInformation() {
        return  name + "\n" +
                "Of " + provider.getName() + " (" + provider.getCountry().getCode() + ")\n" +
                "\n" +
                "Status: " + status + "\n" +
                "Service Types: " + serviceTypes.toString();
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public Set<String> getServiceTypes() {
        return serviceTypes;
    }

    public Provider getProvider() {
        return provider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service that = (Service) o;
        return this.id == that.id && this.provider.equals(that.provider);
    }

    @Override
    public String toString() {
        return "Service{" +
                "tspId=" + provider.getProviderId() +
                ", serviceI=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", serviceTypes=" + serviceTypes +
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
    public int compareTo(Service that) {
        int idComparison = Integer.compare(this.id, that.id);
        if (idComparison == 0)
            return this.provider.compareTo(that.provider);
        return idComparison;
    }

    void setProvider(Provider provider) {
        this.provider = provider;
    }
}
