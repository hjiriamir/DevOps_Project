package tn.esprit.tpfoyer17.services.implementations;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FoyerServiceMockitoTest {

    @Mock
    private FoyerRepository foyerRepository;

    @Mock
    private BlocRepository blocRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private FoyerService foyerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addFoyer() {
        // Arrange
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Foyer 1");
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        // Act
        Foyer savedFoyer = foyerService.addFoyer(foyer);

        // Assert
        assertNotNull(savedFoyer);
        assertEquals("Foyer 1", savedFoyer.getNomFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void retrieveAllFoyers() {
        // Arrange
        List<Foyer> foyers = new ArrayList<>();
        foyers.add(new Foyer());
        foyers.add(new Foyer());
        when(foyerRepository.findAll()).thenReturn(foyers);

        // Act
        List<Foyer> retrievedFoyers = foyerService.retrieveAllFoyers();

        // Assert
        assertNotNull(retrievedFoyers);
        assertEquals(2, retrievedFoyers.size());
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    void retrieveFoyer() {
        // Arrange
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(1L);
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        // Act
        Foyer retrievedFoyer = foyerService.retrieveFoyer(1L);

        // Assert
        assertNotNull(retrievedFoyer);
        assertEquals(1L, retrievedFoyer.getIdFoyer());
        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    void retrieveFoyer_NotFound() {
        // Arrange
        when(foyerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> foyerService.retrieveFoyer(1L));
    }

    @Test
    void updateFoyer() {
        // Arrange
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Updated Foyer");
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        // Act
        Foyer updatedFoyer = foyerService.updateFoyer(foyer);

        // Assert
        assertNotNull(updatedFoyer);
        assertEquals("Updated Foyer", updatedFoyer.getNomFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void removeFoyer() {
        // Act
        foyerService.removeFoyer(1L);

        // Assert
        verify(foyerRepository, times(1)).deleteById(1L);
    }

    @Test
    void ajouterFoyerEtAffecterAUniversite() {
        // Arrange
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer for Universite");

        Bloc bloc = new Bloc();
        Set<Bloc> blocs = new HashSet<>();
        blocs.add(bloc);
        foyer.setBlocs(blocs);

        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(new Universite()));

        // Act
        Foyer savedFoyer = foyerService.ajouterFoyerEtAffecterAUniversite(foyer, 1L);

        // Assert
        assertNotNull(savedFoyer);
        assertEquals("Foyer for Universite", savedFoyer.getNomFoyer());
        verify(foyerRepository, times(1)).save(foyer);
        verify(blocRepository, times(1)).save(bloc);
        verify(universiteRepository, times(1)).findById(1L);
        verify(universiteRepository, times(1)).save(any(Universite.class));
    }
}
