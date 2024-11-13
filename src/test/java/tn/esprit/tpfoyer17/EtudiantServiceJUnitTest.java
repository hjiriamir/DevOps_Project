package tn.esprit.tpfoyer17;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer17.entities.Etudiant;
import tn.esprit.tpfoyer17.repositories.EtudiantRepository;
import tn.esprit.tpfoyer17.services.impementations.EtudiantService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EtudiantServiceJUnitTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantService etudiantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateEtudiantByCin() {

        long cinEtudiant = 12345;
        Etudiant existingEtudiant = new Etudiant();
        Etudiant updatedEtudiant = new Etudiant();

        when(etudiantRepository.findByCinEtudiant(cinEtudiant)).thenReturn(existingEtudiant);
        when(etudiantRepository.save(existingEtudiant)).thenReturn(existingEtudiant);


        Etudiant result = etudiantService.updateEtudiantByCin(cinEtudiant, updatedEtudiant);


        assertEquals(existingEtudiant, result, "The Etudiant should be updated and returned");

        verify(etudiantRepository, times(1)).findByCinEtudiant(cinEtudiant);
        verify(etudiantRepository, times(1)).save(existingEtudiant);
    }
}
