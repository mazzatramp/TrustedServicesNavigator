package com.trustedservices.domain;

/**
 * Interface used to define each object of the TrustedList: Country, Provider and Service. It has only two methods, implemented
 * in each class. Will be used to create a
 * @see com.trustedservices.navigator.components.TrustedEntityLabel
 */
public interface TrustedListEntity {
    public String getName();
    public String getHumanInformation();
}
