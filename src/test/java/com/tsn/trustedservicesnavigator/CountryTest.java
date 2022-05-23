package com.tsn.trustedservicesnavigator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {
@Test
    void getName_NoInputs_ReturnsName()
{
    //arrange
    Country ITA = new Country("Italy","1");

    //act
    String actualName = ITA.getName();

    //assert
    assertEquals("Italy",actualName);
}

}