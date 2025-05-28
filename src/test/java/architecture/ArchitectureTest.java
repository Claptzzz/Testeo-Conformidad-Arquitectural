package architecture;


import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import dao.ConnectionManager;
import org.junit.jupiter.api.Test;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import ui.*;
import service.*;
import dao.*;



public class ArchitectureTest {

    private static JavaClasses classes = new ClassFileImporter().importPackages("");

    @Test
    void uiAccedeSoloServicio() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..ui..")
                .should().accessClassesThat().resideInAPackage("..dao..")
                .allowEmptyShould(true);

        rule.check(classes);
    //ninguna clase que resida en ui debe tener acceso a una de dao
    }

    @Test
    void uiNoAccedeAui() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..ui..")
                .should().accessClassesThat().resideInAPackage("..ui..")
                .allowEmptyShould(true);

        rule.check(classes);
    //ui no accede a ui, so this is for la misma capa
    }

    @Test
    void serviciosAccedeSoloDao() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..service..")
                .should().accessClassesThat().resideInAPackage("..ui..")
                .allowEmptyShould(true);

        rule.check(classes);
        //service no accede a ui
    }

    @Test
    void serviceNoAccedeAservice() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..service..")
                .should().accessClassesThat().resideInAPackage("..service..")
                .allowEmptyShould(true);

        rule.check(classes);
        //service no accede a service
    }

    @Test
    void daoNoAccedeANada() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..dao..")
                .should().accessClassesThat().resideOutsideOfPackages("..dao..")
                .allowEmptyShould(true);

        rule.check(classes);
        //dao no accede a nada
    }

    @Test
    void daoNoAccedeAdao() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..dao..")
                .should().accessClassesThat().resideInAPackage("..dao..")
                .allowEmptyShould(true);

        rule.check(classes);
        //no sé si esta debe ir ya que se ve que dao no accede a nada, pero no se si eso incluye que no pueda acceder a si mismo so lo pongo igual
    }

    //especificossss

    @Test
    void userServiceNoLoginController() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().areAssignableTo(UserService.class)
                .should().accessClassesThat().areAssignableTo(LoginController.class)
                .allowEmptyShould(true);

        rule.check(classes);
        //userService no puede acceder a loginController
    }

    @Test
    void UserViewNoConnectionManager() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().areAssignableTo(UserView.class)
                .should().accessClassesThat().areAssignableTo(ConnectionManager.class)
                .allowEmptyShould(true);

        rule.check(classes);
        //userView no puede acceder a connectionManager
    }
}
