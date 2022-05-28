package com.trustedservicesnavigator;

import com.trustedservicesnavigator.domain.TrustedList;

import java.io.IOException;

public class Aiuto {
    private static TrustedList wholeList;
    private static void constructWholeList() throws IOException {
        wholeList= new TrustedList();
        wholeList.downloadApiData();
    }
    public static TrustedList getWholeList() throws IOException {
        if(wholeList==null) {constructWholeList();}
        return wholeList.clone();
    }
}
