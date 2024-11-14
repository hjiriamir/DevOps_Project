package tn.esprit.tpfoyer17;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.services.impementations.ChambreService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)

class ChambreServiceMockTest {

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private BlocRepository blocRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private ChambreService chambreService;

    private Chambre sampleChambre;
    private Bloc sampleBloc;
    private Universite sampleUniversite;

    @BeforeEach
    public void setUp() {
        // Set up sample data using Lombok Builder or constructor
        sampleChambre = Chambre.builder()
                .idChambre(1L)
                .numeroChambre(101L)
                .typeChambre(TypeChambre.SINGLE)
                .bloc(sampleBloc)
                .reservations(new HashSet<>())
                .build();

        sampleBloc = Bloc.builder()
                .idBloc(1L)
                .nomBloc("Bloc A")
                .build();

        sampleUniversite = Universite.builder()
                .nomUniversite("Université X")
                .build();
    }

    @Test
    @DisplayName("Test Retrieve All Chambres")
     void testRetrieveAllChambres() {
        // Mock the repository to return a list of chambres
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(sampleChambre);
        when(chambreRepository.findAll()).thenReturn(chambres);

        // Call the service method
        List<Chambre> retrievedChambres = chambreService.retrieveAllChambres();

        // Verify the results
        assertNotNull(retrievedChambres);
        assertEquals(1, retrievedChambres.size());
        assertEquals(sampleChambre.getIdChambre(), retrievedChambres.get(0).getIdChambre());

        // Verify interactions with the repository
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test Add Chambre")
     void testAddChambre() {
        // Mock the repository to return the chambre when saved
        when(chambreRepository.save(any(Chambre.class))).thenReturn(sampleChambre);

        // Call the service method
        Chambre addedChambre = chambreService.addChambre(sampleChambre);

        // Verify the results
        assertNotNull(addedChambre);
        assertEquals(sampleChambre.getIdChambre(), addedChambre.getIdChambre());

        // Verify interactions with the repository
        verify(chambreRepository, times(1)).save(sampleChambre);
    }

    @Test
    @DisplayName("Test Update Chambre")
     void testUpdateChambre() {
        // Mock the repository to return the chambre when saved
        when(chambreRepository.save(any(Chambre.class))).thenReturn(sampleChambre);

        // Call the service method
        Chambre updatedChambre = chambreService.updateChambre(sampleChambre);

        // Verify the results
        assertNotNull(updatedChambre);
        assertEquals(sampleChambre.getIdChambre(), updatedChambre.getIdChambre());

        // Verify interactions with the repository
        verify(chambreRepository, times(1)).save(sampleChambre);
    }

    @Test
    @DisplayName("Test Retrieve Chambre By ID")
     void testRetrieveChambre() {
        // Mock the repository to return an Optional of Chambre
        when(chambreRepository.findById(sampleChambre.getIdChambre())).thenReturn(Optional.of(sampleChambre));

        // Call the service method
        Chambre retrievedChambre = chambreService.retrieveChambre(sampleChambre.getIdChambre());

        // Verify the results
        assertNotNull(retrievedChambre);
        assertEquals(sampleChambre.getIdChambre(), retrievedChambre.getIdChambre());

        // Verify interactions with the repository
        verify(chambreRepository, times(1)).findById(sampleChambre.getIdChambre());
    }

    @Test
    @DisplayName("Test Find By Type Chambre")
     void testFindByTypeChambre() {
        // Mock the repository to return a list of chambres
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(sampleChambre);

        // Use argument matchers to avoid strict stubbing issues
        when(chambreRepository.findByTypeChambreAndReservationsEstValide(any(TypeChambre.class), eq(true)))
                .thenReturn(chambres);

        // Call the service method
        List<Chambre> retrievedChambres = chambreService.findByTypeChambre();

        // Verify the results
        assertNotNull(retrievedChambres);
        assertEquals(1, retrievedChambres.size());
        assertEquals(sampleChambre.getTypeChambre(), retrievedChambres.get(0).getTypeChambre());

        // Verify interactions with the repository
        verify(chambreRepository, times(1)).findByTypeChambreAndReservationsEstValide(any(TypeChambre.class), eq(true));
    }


    @Test
    @DisplayName("Test Affecter Chambres A Bloc")
     void testAffecterChambresABloc() {
        // Mock the repository calls
        List<Long> chambreIds = new ArrayList<>();
        chambreIds.add(sampleChambre.getIdChambre());
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(sampleChambre);
        when(chambreRepository.findByNumeroChambreIn(chambreIds)).thenReturn(chambres);
        when(blocRepository.findById(sampleBloc.getIdBloc())).thenReturn(Optional.of(sampleBloc));

        // Call the service method
        Bloc returnedBloc = chambreService.affecterChambresABloc(chambreIds, sampleBloc.getIdBloc());

        // Verify the result
        assertNotNull(returnedBloc);
        assertEquals(sampleBloc.getIdBloc(), returnedBloc.getIdBloc());
        verify(chambreRepository, times(1)).findByNumeroChambreIn(chambreIds);
        verify(blocRepository, times(1)).findById(sampleBloc.getIdBloc());
    }

    @Test
    @DisplayName("Test Get Chambres Par Nom Universite")
     void testGetChambresParNomUniversite() {
        // Mock the repository to return a list of chambres
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(sampleChambre);
        when(chambreRepository.findByBlocFoyerUniversiteNomUniversiteLike(sampleUniversite.getNomUniversite())).thenReturn(chambres);

        // Call the service method
        List<Chambre> retrievedChambres = chambreService.getChambresParNomUniversite(sampleUniversite.getNomUniversite());

        // Verify the results
        assertNotNull(retrievedChambres);
        assertEquals(1, retrievedChambres.size());
        assertEquals(sampleChambre.getIdChambre(), retrievedChambres.get(0).getIdChambre());
        verify(chambreRepository, times(1)).findByBlocFoyerUniversiteNomUniversiteLike(sampleUniversite.getNomUniversite());
    }

    @Test
    @DisplayName("Test Get Chambres Par Bloc Et Type")
     void testGetChambresParBlocEtType() {
        // Mock the repository to return a list of chambres
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(sampleChambre);
        when(chambreRepository.findByBlocIdBlocAndTypeChambre(sampleBloc.getIdBloc(), TypeChambre.SINGLE)).thenReturn(chambres);

        // Call the service method
        List<Chambre> retrievedChambres = chambreService.getChambresParBlocEtType(sampleBloc.getIdBloc(), TypeChambre.SINGLE);

        // Verify the results
        assertNotNull(retrievedChambres);
        assertEquals(1, retrievedChambres.size());
        assertEquals(sampleChambre.getIdChambre(), retrievedChambres.get(0).getIdChambre());
        verify(chambreRepository, times(1)).findByBlocIdBlocAndTypeChambre(sampleBloc.getIdBloc(), TypeChambre.SINGLE);
    }

    @Test
    @DisplayName("Test Get Chambres Par Bloc Et Type JPQL")
     void testGetChambresParBlocEtTypeJPQL() {
        // Mock the repository to return a list of chambres
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(sampleChambre);
        when(chambreRepository.recupererParBlocEtTypeChambre(sampleBloc.getIdBloc(), TypeChambre.SINGLE)).thenReturn(chambres);

        // Call the service method
        List<Chambre> retrievedChambres = chambreService.getChambresParBlocEtTypeJPQL(sampleBloc.getIdBloc(), TypeChambre.SINGLE);

        // Verify the results
        assertNotNull(retrievedChambres);
        assertEquals(1, retrievedChambres.size());
        assertEquals(sampleChambre.getIdChambre(), retrievedChambres.get(0).getIdChambre());
        verify(chambreRepository, times(1)).recupererParBlocEtTypeChambre(sampleBloc.getIdBloc(), TypeChambre.SINGLE);
    }

    @Test
    @DisplayName("Test Get Chambres Non Reserve Par Nom Universite Et Type Chambre")
     void testGetChambresNonReserveParNomUniversiteEtTypeChambre() {
        // Mock the repository to return a list of non-reserved chambres
        List<Chambre> chambresNonReserve = new ArrayList<>();
        chambresNonReserve.add(sampleChambre);
        when(chambreRepository.getChambresNonReserveParNomUniversiteEtTypeChambre(sampleUniversite.getNomUniversite(), TypeChambre.SINGLE))
                .thenReturn(chambresNonReserve);

        // Call the service method
        List<Chambre> retrievedChambres = chambreService.getChambresNonReserveParNomUniversiteEtTypeChambre(sampleUniversite.getNomUniversite(), TypeChambre.SINGLE);

        // Verify the results
        assertNotNull(retrievedChambres);
        assertEquals(1, retrievedChambres.size());
        assertEquals(sampleChambre.getIdChambre(), retrievedChambres.get(0).getIdChambre());
        verify(chambreRepository, times(1)).getChambresNonReserveParNomUniversiteEtTypeChambre(sampleUniversite.getNomUniversite(), TypeChambre.SINGLE);
    }

    @Test
    @DisplayName("Test Get Chambres Non Reserve")
     void testGetChambresNonReserve() {
        // Mock the repository to return a list of non-reserved chambres
        List<Chambre> chambresNonReserve = new ArrayList<>();
        chambresNonReserve.add(sampleChambre);
        when(chambreRepository.getChambresNonReserve()).thenReturn(chambresNonReserve);

        // Call the service method
        chambreService.getChambresNonReserve(); // This is a void method, but we can verify interaction

        // Verify interactions with the repository
        verify(chambreRepository, times(1)).getChambresNonReserve();
    }
}
