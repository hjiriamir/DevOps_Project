
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.impementations.UniversiteService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UniversiteServicesTest {

    @InjectMocks
    private UniversiteService universiteService;

    @Mock
    private UniversiteRepository universiteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddUniversity() {
        Universite universite = Universite.builder()
                .nomUniversite("Test University")
                .adresse("Test Address")
                .build();

        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite savedUniversity = universiteService.addUniversity(universite);

        assertNotNull(savedUniversity);
        assertEquals("Test University", savedUniversity.getNomUniversite());
        assertEquals("Test Address", savedUniversity.getAdresse());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    public void testGetProductById_ProductNotFound() {
        when(universiteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> universiteService.retrieveUniversity(1L));
    }

    @Test
    public void testUpdateUniversity() {
        Universite universite = Universite.builder()
                .nomUniversite("Test University")
                .adresse("Test Address")
                .build();

        // Pas besoin de définir l'ID manuellement si JPA le gère
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        universite.setNomUniversite("Updated University");

        Universite updatedUniversity = universiteService.updateUniversity(universite);

        assertNotNull(updatedUniversity);
        assertEquals("Updated University", updatedUniversity.getNomUniversite());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    public void testDeleteUniversity_ProductExists() {
        when(universiteRepository.existsById(1L)).thenReturn(true);

        universiteService.deleteUniversity(1L);

        verify(universiteRepository, times(1)).deleteById(1L);
    }
    @Test
    public void testRetrieveUniversity_NotFound() {
       when(universiteRepository.findById(1L)).thenReturn(Optional.empty());
       assertThrows(EntityNotFoundException.class,() -> universiteService.retrieveUniversity(1L));
    }
    @Test
    public void testRetrieveUniversity_Existe() {
        Universite universite = new Universite();
        universite.setIdUniversite(1L);
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        Universite resultat = universiteService.retrieveUniversity(1L);

        assertNotNull(resultat);  // Vérifie que le résultat n'est pas null
        assertEquals(1L, resultat.getIdUniversite());  // Vérifie que l'université retournée a l'ID correct
    }


    @Test
    public void testFilterUniversities() {
        Universite u1 = Universite.builder()
                .nomUniversite("Tech University")
                .adresse("Paris")
                .build();

        Universite u2 = Universite.builder()
                .nomUniversite("Science University")
                .adresse("Lyon")
                .build();

        when(universiteRepository.findByNomUniversiteContainingAndAdresseContaining("University", "France"))
                .thenReturn(Arrays.asList(u1, u2));

        List<Universite> result = universiteService.filterUniversities("University", "France");

        assertEquals(2, result.size());
        assertEquals("Tech University", result.get(0).getNomUniversite());
        assertEquals("Science University", result.get(1).getNomUniversite());
        verify(universiteRepository, times(1)).findByNomUniversiteContainingAndAdresseContaining("University", "France");
    }
}
