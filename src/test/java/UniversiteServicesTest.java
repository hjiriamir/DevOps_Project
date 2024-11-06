
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




}
