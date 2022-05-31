package com.trustedservices;
import com.trustedservices.navigator.NavigationController;
import com.trustedservices.navigator.WindowController;
import org.junit.jupiter.api.*;

@DisplayName("A NavigationController")
class NavigationControllerTest {
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
    @DisplayName("is instantiated with new NavigationController()")
    void isInstantiatedWithNewClasse() {
        new NavigationController();
    }

    NavigationController navigationController;

    @Nested
    @DisplayName("when new")
    class WhenNew{

        @BeforeEach
        @Test
        void createANavigationMediator() {
            navigationController = new NavigationController();
        }

        @Disabled
        @DisplayName("and I use the method readActiveFiltersFrom")
        @Test
        void prova(){
                WindowController windowController = new WindowController();
                //assertEquals(navigationController.getFilteredList().getCountries().getProviders, windowController.getFilterAccordion().getSelectedProviders());
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