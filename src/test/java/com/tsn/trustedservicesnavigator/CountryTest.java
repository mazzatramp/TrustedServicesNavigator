package com.tsn.trustedservicesnavigator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {
@Test
    void italyNameIsItaly() 
{
  Country ITA = new Country("Italy","1");
  assertEquals("Italy",ITA.getName());
}

}