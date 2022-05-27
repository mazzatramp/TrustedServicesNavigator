package com.tsn.trustedservicesnavigator;

import java.io.IOException;

public class Help {
    private static TrustedList wholeList;
    private static void constructWholeList() throws IOException {
        wholeList= new TrustedList();
        wholeList.downloadApiData();
    }
    public static TrustedList getWholeList() throws IOException {
        if(wholeList==null) {constructWholeList();}
        return wholeList.clone();
    }

    //METTERE GETPROVIDERN GETSERVICETYPEN GETSTATUSN
}
