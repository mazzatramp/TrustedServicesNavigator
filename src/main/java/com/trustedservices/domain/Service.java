package com.trustedservices.domain;

import com.fasterxml.jackson.annotation.*;
import com.trustedservices.navigator.filters.ProviderFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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

    public Service(int serviceId, String name, String type, String status, Set<String> serviceTypes) {
        this(null, serviceId, name, type, status, serviceTypes);
    }

    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getInformation() {
        return List.of(name, status, serviceTypes.toString());
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
        int providerComparison = 0;
        if (provider != null && service.provider != null)
            providerComparison = this.getProvider().compareTo(service.getProvider());

        int idComparison = Integer.compare(this.getId(), service.getId());
        if (providerComparison != 0)
            return providerComparison;
        else
            return idComparison;
    }
}
