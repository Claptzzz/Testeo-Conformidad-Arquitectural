package architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import dao.ConnectionManager;
import org.junit.jupiter.api.Test;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import ui.UserView;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;


public class ArchitectureTest {


    @Test
    void uiAccedeSoloServicio() {
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..ui..")
                .should().accessClassesThat().resideInAPackage("..dao..");
    //ninguna clase que resida en ui debe tener acceso a una de dao
    }

    @Test
    void uiNoAccedeAui() {
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..ui..")
                .should().accessClassesThat().resideInAPackage("..ui..");
    //ui no accede a ui, so this is for la misma capa
    }

    @Test
    void serviciosAccedeSoloDao() {
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..service..")
                .should().accessClassesThat().resideInAPackage("..ui..");
        //service no accede a ui
    }

    @Test
    void serviceNoAccedeAservice() {
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..service..")
                .should().accessClassesThat().resideInAPackage("..service..");
        //service no accede a service
    }

    @Test
    void daoNoAccedeANada() {
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..dao..")
                .should().accessClassesThat().resideOutsideOfPackages("..dao..");
        //dao no accede a nada
    }

    @Test
    void daoNoAccedeAdao() {
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..dao..")
                .should().accessClassesThat().resideInAPackage("..dao..");
        //no sé si esta debe ir ya que se ve que dao no accede a nada, pero no se si eso incluye que no pueda acceder a si mismo so lo pongo igual
    }

    //especificossss
/*
    @Test
    void userServiceNoAccedeALoginController() {
        ArchRuleDefinition.noClasses()
                .that().
                .that().resideInClass("..service.UserService")
                .should().accessClassesThat().resideInClass("..ui.LoginController");

    }*/


    //2) UserView accediendo a ConnectionManager.
/*
    @Test
    void userViewShouldNotAccessDatabase() {
        UserView view = new UserView();
        assertFalse(view instanceof ConnectionManager); // Valida independencia
    } */

}
