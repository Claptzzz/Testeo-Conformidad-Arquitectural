package architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;


public class ArchitectureTest {

    /*
    @Test
    void verificarReglasDeCapas() {
        JavaClasses importedClasses = new ClassFileImporter()
                .importPackages("dao", "service", "ui");

        layeredArchitecture()
                .layer("UI").definedBy("ui..")
                .layer("Service").definedBy("service..")
                .layer("DAO").definedBy("dao..")

                .whereLayer("UI").mayOnlyAccessLayers("Service")
                .whereLayer("Service").mayOnlyAccessLayers("DAO")
                .whereLayer("DAO").mayNotAccessAnyLayer()

                .check(importedClasses);
    } */

    public static JavaClasses classes = new ClassFileImporter().importPackages("dao", "service", "ui");


    @Test
    void verificarReglasDeCapas() {
        //ui-accede solo-servicios
        uiAccedeSoloServicio();
        //servicios-accede solo-dao
        serviciosAccedeSoloDao();
        //dao-no accede a nada
        daoNoAccedeANada();
        //no hay dependecias en la misma capa
        //javier tonto tonto el mas tonto
        noDependenciasEnLaMismaCapa();
    }
    @Test
    void uiAccedeSoloServicio() {
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..ui..")
                .should().accessClassesThat().resideInAPackage("..dao..");
    }

    @Test
    void serviciosAccedeSoloDao() {
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..service..")
                .should().accessClassesThat().resideInAPackage("..ui..");
    }

    @Test
    void daoNoAccedeANada() {
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..dao..")
                .should().accessClassesThat().resideOutsideOfPackages("..dao..");
    }

    @Test
    void noDependenciasEnLaMismaCapa() {
        ArchRuleDefinition.noClasses()
                .that().resideInAPackage("..ui..")
                .should().dependOnClassesThat().resideInAnyPackage("..ui..")
                .allowEmptyShould(true);
    }
}
