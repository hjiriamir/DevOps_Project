package tn.esprit.tpfoyer17;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer17.entities.Etudiant;
import tn.esprit.tpfoyer17.repositories.EtudiantRepository;
import tn.esprit.tpfoyer17.services.impementations.EtudiantService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EtudiantServiceTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantService etudiantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddEtudiants() {

        Etudiant etudiant1 = new Etudiant();
        Etudiant etudiant2 = new Etudiant();
        List<Etudiant> etudiantsToAdd = Arrays.asList(etudiant1, etudiant2);


        when(etudiantRepository.saveAll(etudiantsToAdd)).thenReturn(etudiantsToAdd);


        List<Etudiant> addedEtudiants = etudiantService.addEtudiants(etudiantsToAdd);


        assertEquals(etudiantsToAdd, addedEtudiants, "The returned list should match the added list");
        verify(etudiantRepository, times(1)).saveAll(etudiantsToAdd);
    }
    @Test
    void testRetrieveAllEtudiants() {

        Etudiant etudiant1 = new Etudiant();
        Etudiant etudiant2 = new Etudiant();
        List<Etudiant> etudiants = Arrays.asList(etudiant1, etudiant2);


        when(etudiantRepository.findAll()).thenReturn(etudiants);


        List<Etudiant> retrievedEtudiants = etudiantService.retrieveAllEtudiants();


        assertEquals(etudiants, retrievedEtudiants, "The returned list should match the repository's list");
        verify(etudiantRepository, times(1)).findAll();
    }
    @Test
    void testUpdateEtudiant() {

        Etudiant etudiant = new Etudiant();
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);


        Etudiant updatedEtudiant = etudiantService.updateEtudiant(etudiant);


        assertEquals(etudiant, updatedEtudiant, "The returned Etudiant should match the updated Etudiant");
        verify(etudiantRepository, times(1)).save(etudiant);
    }
    @Test
    void testRetrieveEtudiant() {

        Etudiant etudiant = new Etudiant();
        long etudiantId = 1L;
        when(etudiantRepository.findById(etudiantId)).thenReturn(java.util.Optional.of(etudiant));


        Etudiant retrievedEtudiant = etudiantService.retrieveEtudiant(etudiantId);


        assertEquals(etudiant, retrievedEtudiant, "The returned Etudiant should match the retrieved Etudiant");
        verify(etudiantRepository, times(1)).findById(etudiantId);
    }
    @Test
    void testRemoveEtudiant() {

        long etudiantId = 1L;


        etudiantService.removeEtudiant(etudiantId);

        verify(etudiantRepository, times(1)).deleteById(etudiantId);
    }
    @Test
    void testFindByReservationsAnneeUniversitaire() {

        Etudiant etudiant1 = new Etudiant();
        Etudiant etudiant2 = new Etudiant();
        List<Etudiant> etudiants = Arrays.asList(etudiant1, etudiant2);
        when(etudiantRepository.findByReservationsAnneeUniversitaire(LocalDate.now())).thenReturn(etudiants);


        List<Etudiant> foundEtudiants = etudiantService.findByReservationsAnneeUniversitaire();


        assertEquals(etudiants, foundEtudiants, "The returned list should match the list for the current year");
        verify(etudiantRepository, times(1)).findByReservationsAnneeUniversitaire(LocalDate.now());
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


        assertEquals(etudiants, foundEtudiants, "The returned list should match the date range");
        verify(etudiantRepository, times(1)).findByDateNaissanceBetween(startDate, endDate);
    }

    @Test
    void testUpdateEtudiantByCin() {

        long cinEtudiant = 12345L;
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