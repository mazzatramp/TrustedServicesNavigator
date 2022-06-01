package com.trustedservices.navigator.components;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusFilterPaneTest {
    //QUI IN ALCUNI METODI MOCKANDO STATUSES POSSO FARLO ALTRI DOVE DEVO VERIFICARE CONDIZIONI STATUSES NON POSSO ARRIVARCI
    //FORSE ESISTE UN MODO INDIRETTAMENTE?

    StatusFilterPane statusFilterPane;
    @Test
    @DisplayName("is instantiated with new costruttoreOggetto")
    void isInstantiatedWithNewClasse() {
        //StatusFilterPane statusFilterPane1=  new StatusFilterPane(); NON SO PERCHE FUNZIONA
        new StatusFilterPane(); //NON SO PERCHE NON FUNZIONA
    }
    //FORSE SE RIESCO AD USARE CHECKBOX
    @Disabled
    @Nested
    class todo{
        @Test
        void setSelectionStatusForAll(){
        statusFilterPane.setSelectionStatusForAll(true);
        //MI SERVIREBBE CHE UN METODO RITORNASSE GLI STATUS
    }
        @Test
        void fillWith(){
            //NON SAPREI

        }
        @Test
        void getSelectedItems(){
            //QUI DOVREI MOCKARE CHE STATUSES QUANDO INTERROGATO CON ISDISABLED E IS SELECTED RISPONDA SI O NO
            //ANCHE GETTEXT DEVE ESSSERE MOCKATO
        }
        @Test
        void getUnselectedItems(){
            //QUI DOVREI MOCKARE CHE STATUSES QUANDO INTERROGATO CON ISDISABLED E IS SELECTED RISPONDA SI O NO
            //ANCHE GETTEXT DEVE ESSSERE MOCKATO
        }

        @Test
        void disable(){
            //ANCHE WUI DEVO MOCKARE STATUS
        }
    }



}