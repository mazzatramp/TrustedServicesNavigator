package com.trustedservices;

import com.trustedservicesnavigator.frontend.UserInterfaceController;
import com.trustedservicesnavigator.frontend.panes.FilterSelectionAccordion;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A NavigationMediator")
class NavigationMediatorTest {
    @Nested
    @DisplayName("when null")
    class WhenNull {
        @BeforeEach
        void setOggettoNull() {
            //nomeOggetto=null;
        }
        @DisplayName("and I use the method ...")
        @Nested
        class nomeMetodo1{}
        @DisplayName("and I use the method ...")
        @Nested
        class nomeMetodo2{}
    }
    @Test
    @DisplayName("is instantiated with new NavigationMediator()")
    void isInstantiatedWithNewClasse() {
        new NavigationMediator();
    }
    NavigationMediator navigationMediator;
    @Nested
    @DisplayName("when new")
    class WhenNew{
        @BeforeEach
        @Test
        void createANavigationMediator() {
            navigationMediator = new NavigationMediator();
        }
        @Disabled
        @DisplayName("and I use the method readActiveFiltersFrom")
        @Test
        void prova(){
                UserInterfaceController userInterfaceController= new UserInterfaceController();
                navigationMediator.setUserInterfaceController(userInterfaceController);
                navigationMediator.readActiveFiltersFrom(new FilterSelectionAccordion());
                //assertEquals(navigationMediator.getFilteredList().getCountries().getProviders, userInterfaceController.getFilterAccordion().getSelectedProviders());
            }



        }
        @DisplayName("and I use the method ..")
        @Nested
        class nomeMetodo2{}}

/*
    @Test
    void setUserInterfaceController() {
    }

    @Test
    void getFilteredList() {
    }

    @Test
    void getCompleteList() {
    }

    @Test
    void fillCompleteListFromApiData() {
    }

    @Test
    void getFilterController() {
    }

    @Test
    void readActiveFiltersFrom() {
    }
}
*/