package tn.esprit.tpfoyer17.services.impementations;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FoyerServiceJUnitTest {

    @Autowired
    private FoyerService foyerService;

    @Autowired
    private FoyerRepository foyerRepository;

    @Test
    @Order(1)
    void addFoyer() {
        // Arrange
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Foyer Test");
        foyer.setCapaciteFoyer(50);

        // Act
        Foyer savedFoyer = foyerService.addFoyer(foyer);

        // Assert
        assertNotNull(savedFoyer);
        assertEquals("Foyer Test", savedFoyer.getNomFoyer());
        System.out.println("Add Foyer: Ok");
    }

    @Test
    @Order(2)
    void retrieveAllFoyers() {
        // Act
        List<Foyer> foyers = foyerService.retrieveAllFoyers();

        // Assert
        assertNotNull(foyers);
        assertFalse(foyers.isEmpty());
        System.out.println("Retrieve All Foyers: Ok");
    }

    @Test
    @Order(3)
    void retrieveFoyer() {
        // Act
        Foyer foyer = foyerService.retrieveFoyer(1L); // Adjust ID as necessary

        // Assert
        assertNotNull(foyer);
        assertEquals("Foyer Test", foyer.getNomFoyer()); // Adjust based on your data
        System.out.println("Retrieve Foyer: Ok");
    }

    @Test
    @Order(4)
    void updateFoyer() {
        // Arrange
        Foyer foyer = foyerService.retrieveFoyer(1L); // Adjust ID as necessary
        foyer.setNomFoyer("Updated Foyer");

        // Act
        Foyer updatedFoyer = foyerService.updateFoyer(foyer);

        // Assert
        assertNotNull(updatedFoyer);
        assertEquals("Updated Foyer", updatedFoyer.getNomFoyer());
        System.out.println("Update Foyer: Ok");
    }

    @Test
    @Order(5)
    void removeFoyer() {
        // Act
        foyerService.removeFoyer(1L); // Adjust ID as necessary

        // Assert
        assertThrows(EntityNotFoundException.class, () -> foyerService.retrieveFoyer(1L));
        System.out.println("Remove Foyer: Ok");
    }

}
