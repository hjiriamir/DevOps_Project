package tn.esprit.tpfoyer17;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.services.impementations.ChambreService;
import org.junit.jupiter.api.*;
import org.springframework.test.annotation.DirtiesContext;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest

public class ChambreServiceTest {

    @Autowired
    private ChambreService chambreService;

    @Test
    @Order(1)
    @DisplayName("Test Add Chambre")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testAddChambre() {
        Chambre chambre = new Chambre();
        chambre.setTypeChambre(TypeChambre.SINGLE);
        chambre.setNumeroChambre(101L); // Example number

        Chambre addedChambre = chambreService.addChambre(chambre);

        // Verify the chambre was added
        assertNotNull(addedChambre);
        assertNotNull(addedChambre.getIdChambre());
        assertEquals(TypeChambre.SINGLE, addedChambre.getTypeChambre());

        // Clean up (delete the added chambre to keep database state clean)
        chambreService.retrieveAllChambres().removeIf(c -> Long.valueOf(c.getIdChambre()).equals(Long.valueOf(addedChambre.getIdChambre())));
    }

    @Test
    @Order(2)
    @DisplayName("Test Retrieve All Chambres")
    public void testRetrieveAllChambres() {
        List<Chambre> chambres = chambreService.retrieveAllChambres();

        // Verify the list of chambres is retrieved and has expected content
        assertNotNull(chambres);
        assertTrue(chambres.size() > 0, "The list of chambres should not be empty.");
    }

    @Test
    @Order(3)
    @DisplayName("Test Retrieve Chambre by ID")
    public void testRetrieveChambre() {
        long idChambre = 1L; // Replace with a valid ID
        Chambre chambre = chambreService.retrieveChambre(idChambre);

        if (chambre != null) {
            assertEquals(idChambre, chambre.getIdChambre());
        } else {
            fail("Chambre with ID " + idChambre + " not found.");
        }
    }

    @Test
    @Order(4)
    @DisplayName("Test Update Chambre")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void testUpdateChambre() {
        Chambre chambre = chambreService.retrieveChambre(1L); // Replace with a valid ID

        if (chambre != null) {
            chambre.setTypeChambre(TypeChambre.DOUBLE);
            Chambre updatedChambre = chambreService.updateChambre(chambre);

            // Verify update
            assertNotNull(updatedChambre);
            assertEquals(TypeChambre.DOUBLE, updatedChambre.getTypeChambre());

            // Revert changes to maintain clean database state
            chambre.setTypeChambre(TypeChambre.SINGLE);
            chambreService.updateChambre(chambre);
        } else {
            fail("Chambre with ID 1 not found for update test.");
        }
    }

    @Test
    @Order(5)
    @DisplayName("Test Find By Type Chambre")
    public void testFindByTypeChambre() {
        List<Chambre> doubleChambres = chambreService.findByTypeChambre();

        assertNotNull(doubleChambres);
        doubleChambres.forEach(chambre -> assertEquals(TypeChambre.DOUBLE, chambre.getTypeChambre()));
    }

    @Test
    @Order(7)
    @DisplayName("Test Get Chambres Par Nom Universite")
    public void testGetChambresParNomUniversite() {
        String nomUniversite = "University XYZ"; // Replace with a valid university name

        List<Chambre> chambres = chambreService.getChambresParNomUniversite(nomUniversite);

        // Verify that the chambres belong to the correct universite
        assertNotNull(chambres);
        chambres.forEach(chambre ->
                assertTrue(chambre.getBloc().getFoyer().getUniversite().getNomUniversite().contains(nomUniversite)));
    }

    @Test
    @Order(8)
    @DisplayName("Test Get Chambres Par Bloc et Type")
    public void testGetChambresParBlocEtType() {
        long idBloc = 1L; // Replace with a valid bloc ID
        TypeChambre typeChambre = TypeChambre.SINGLE; // Replace with a valid type

        List<Chambre> chambres = chambreService.getChambresParBlocEtType(idBloc, typeChambre);

        // Verify that all chambres belong to the correct bloc and type
        assertNotNull(chambres);
        chambres.forEach(chambre -> {
            assertEquals(idBloc, chambre.getBloc().getIdBloc());
            assertEquals(typeChambre, chambre.getTypeChambre());
        });
    }

    @Test
    @Order(9)
    @DisplayName("Test Get Chambres Par Bloc et Type (JPQL)")
    public void testGetChambresParBlocEtTypeJPQL() {
        long idBloc = 1L; // Replace with a valid bloc ID
        TypeChambre typeChambre = TypeChambre.SINGLE; // Replace with a valid type

        List<Chambre> chambres = chambreService.getChambresParBlocEtTypeJPQL(idBloc, typeChambre);

        // Verify that all chambres belong to the correct bloc and type
        assertNotNull(chambres);
        chambres.forEach(chambre -> {
            assertEquals(idBloc, chambre.getBloc().getIdBloc());
            assertEquals(typeChambre, chambre.getTypeChambre());
        });
    }

    @Test
    @Order(10)
    @DisplayName("Test Get Chambres Non Réservées Par Nom Université et Type Chambre")
    public void testGetChambresNonReserveParNomUniversiteEtTypeChambre() {
        String nomUniversite = "University XYZ"; // Replace with a valid university name
        TypeChambre typeChambre = TypeChambre.SINGLE; // Replace with a valid chambre type

        List<Chambre> chambres = chambreService.getChambresNonReserveParNomUniversiteEtTypeChambre(nomUniversite, typeChambre);

        // Verify that all chambres are non-reserved and belong to the correct university and type
        assertNotNull(chambres);
        chambres.forEach(chambre -> {
            // Check that there are no valid reservations (i.e., estValide is false)
            assertTrue(chambre.getReservations().stream().noneMatch(reservation -> reservation.isEstValide()));

            // Check that the chambre belongs to the correct university
            assertTrue(chambre.getBloc().getFoyer().getUniversite().getNomUniversite().contains(nomUniversite));

            // Check that the chambre has the correct type
            assertEquals(typeChambre, chambre.getTypeChambre());
        });
    }
}
