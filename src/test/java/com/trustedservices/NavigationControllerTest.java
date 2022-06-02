package com.trustedservices;
import com.trustedservices.navigator.NavigationController;
import org.junit.jupiter.api.*;

@DisplayName("A NavigationController")
class NavigationControllerTest {

    NavigationController navigationController;

    @Test
    @DisplayName("is instantiated with new NavigationController()")
    void isInstantiatedWithNewClass() {
        new NavigationController();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew {

        @BeforeEach
        @Test
        void createANavigationMediator() {
            navigationController = new NavigationController();
        }
    }
}