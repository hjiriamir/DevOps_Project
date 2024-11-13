package tn.esprit.tpfoyer17;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer17.entities.Etudiant;
import tn.esprit.tpfoyer17.repositories.EtudiantRepository;
import tn.esprit.tpfoyer17.services.impementations.EtudiantService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

class EtudiantServiceMockitoTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantService etudiantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByDateNaissanceBetween() {
        Etudiant etudiant1 = new Etudiant();
        Etudiant etudiant2 = new Etudiant();
        Date startDate = new Date(1000000000L);
        Date endDate = new Date(2000000000L);
        List<Etudiant> etudiants = Arrays.asList(etudiant1, etudiant2);

        when(etudiantRepository.findByDateNaissanceBetween(startDate, endDate)).thenReturn(etudiants);

        List<Etudiant> foundEtudiants = etudiantService.findByDateNaissanceBetween(startDate, endDate);

        verify(etudiantRepository, times(1)).findByDateNaissanceBetween(startDate, endDate);
    }
}
