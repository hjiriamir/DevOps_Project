
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;
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

    @Mock
    private FoyerRepository foyerRepository;

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
    public void testRetrieveUniversity_Existe() {
        Universite universite = new Universite();
        universite.setIdUniversite(1L);
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        Universite resultat = universiteService.retrieveUniversity(1L);

        assertNotNull(resultat);  // Vérifie que le résultat n'est pas null
        assertEquals(1L, resultat.getIdUniversite());  // Vérifie que l'université retournée a l'ID correct
    }

    @Test
    public void testRetrieveUniversity_Exists() {
        Universite universite = Universite.builder()
                .idUniversite(1L)
                .nomUniversite("Test University")
                .adresse("Test Address")
                .build();

        // Simulation du comportement du repository
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        Universite result = universiteService.retrieveUniversity(1L);

        // Vérifier que l'université retournée n'est pas null et a l'ID correct
        assertNotNull(result);
        assertEquals(1L, result.getIdUniversite());
        assertEquals("Test University", result.getNomUniversite());
        assertEquals("Test Address", result.getAdresse());

        // Vérifier que la méthode findById a été appelée une seule fois avec l'ID correct
        verify(universiteRepository, times(1)).findById(1L);
    }

    @Test
    public void testRetrieveUniversity_NotExists() {
        // Simulation du comportement du repository lorsqu'aucune université n'est trouvée
        when(universiteRepository.findById(1L)).thenReturn(Optional.empty());

        Universite result = universiteService.retrieveUniversity(1L);

        // Vérifier que le résultat est null car l'université n'a pas été trouvée
        assertNull(result);

        // Vérifier que la méthode findById a été appelée une seule fois avec l'ID correct
        verify(universiteRepository, times(1)).findById(1L);
    }
    @Test
    public void testAffecterFoyerAUniversite_Success() {
        Foyer foyer = Foyer.builder().idFoyer(1L).build();
        Universite universite = Universite.builder().idUniversite(1L).nomUniversite("Test University").build();

        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));
        when(universiteRepository.findByNomUniversiteLike("Test University")).thenReturn(universite);
        when(universiteRepository.save(universite)).thenReturn(universite);

        Universite result = universiteService.affecterFoyerAUniversite(1L, "Test University");

        assertNotNull(result);
        assertEquals(foyer, result.getFoyer()); // Vérifie que le foyer a bien été affecté
        verify(foyerRepository, times(1)).findById(1L);
        verify(universiteRepository, times(1)).findByNomUniversiteLike("Test University");
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testDesaffecterFoyerAUniversite_Success() {
        // Arrange
        long idUniversite = 1L;
        Universite universite = new Universite();
        universite.setFoyer(new Foyer());  // Initially set with a Foyer

        when(universiteRepository.findById(idUniversite)).thenReturn(Optional.of(universite));
        when(universiteRepository.save(universite)).thenReturn(universite);

        // Act
        Universite result = universiteService.desaffecterFoyerAUniversite(idUniversite);

        // Assert
        assertNotNull(result);
        assertNull(result.getFoyer(), "The foyer should be set to null after desaffecting.");
        verify(universiteRepository, times(1)).findById(idUniversite);
        verify(universiteRepository, times(1)).save(universite);
    }
    @Test
    void testRetrieveAllUniversities() {
        // Arrange
        Universite universite1 = new Universite();
        universite1.setNomUniversite("Universite 1");
        Universite universite2 = new Universite();
        universite2.setNomUniversite("Universite 2");
        List<Universite> universities = Arrays.asList(universite1, universite2);
        when(universiteRepository.findAll()).thenReturn(universities);
        // Act
        List<Universite> result = universiteService.retrieveAllUniversities();
        // Assert
        assertNotNull(result);
        assertEquals(2, result.size(), "The list should contain two universities.");
        assertEquals("Universite 1", result.get(0).getNomUniversite());
        assertEquals("Universite 2", result.get(1).getNomUniversite());
        verify(universiteRepository, times(1)).findAll();
    }
}
