package tn.esprit.tpfoyer17.services.impementations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.entities.Bloc;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)  // Utilisation de Mockito dans les tests
class BlocServiceTest2 {

    @Mock
    private BlocRepository blocRepository;  // Simulation de BlocRepository

    @InjectMocks
    private BlocService blocService;  // Injection de BlocRepository simulé dans BlocService

    private Bloc bloc;

    @BeforeEach
    void setUp() {
        bloc = new Bloc.Builder()
                    .nomBloc("Bloc 1")
                    .capaciteBloc(100)
                    .build();
    }

    @Test
    public void testAddBloc() {
        // Création d'un bloc avec un nom et une capacité pour les tests
    	 bloc = new Bloc.Builder()
                 .nomBloc("Bloc 1")
                 .capaciteBloc(100)
                 .build();

        // Comportement simulé de BlocRepository
        Mockito.when(blocRepository.save(Mockito.any(Bloc.class))).thenReturn(bloc);

        // Appel de la méthode à tester
        Bloc result = blocService.addBloc(bloc);

        // Vérification que le bloc a été ajouté correctement
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Bloc 1", result.getNomBloc());
        Assertions.assertEquals(100, result.getCapaciteBloc());

        // Vérifier que les contraintes sur le nom et la capacité sont respectées
        Assertions.assertTrue(result.getNomBloc().length() < 15, "Nom du bloc trop long");
        Assertions.assertTrue(result.getCapaciteBloc() > 0, "Capacité du bloc doit être positive");

        // Vérification que le save() a bien été appelé une fois avec le bon argument
        Mockito.verify(blocRepository, Mockito.times(1)).save(Mockito.any(Bloc.class));
    }

   /* @Test
    void testAddBloc() {
        // Comportement simulé de BlocRepository
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // Appel de la méthode à tester
        Bloc result = blocService.addBloc(bloc);

        // Vérification du comportement
        assertNotNull(result);
        assertEquals("Bloc 1", result.getNomBloc());
        assertEquals(100, result.getCapaciteBloc());

        // Vérification que le save() a été appelé
        verify(blocRepository, times(1)).save(bloc);
    }*/
    @Test
    void testRetrieveBlocs() {
        // Comportement simulé de BlocRepository
        when(blocRepository.findAll()).thenReturn(List.of(bloc));

        // Appel de la méthode à tester
        List<Bloc> result = blocService.retrieveBlocs();

        // Vérification des résultats
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Bloc 1", result.get(0).getNomBloc());

        // Vérification que findAll() a été appelé
        verify(blocRepository, times(1)).findAll();
    }
    @Test
    void testUpdateBloc() {
        // Comportement simulé de BlocRepository
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // Modification du bloc
        bloc.setNomBloc("Bloc 1 Updated");

        // Appel de la méthode à tester
        Bloc result = blocService.updateBloc(bloc);

        // Vérification des résultats
        assertNotNull(result);
        assertEquals("Bloc 1 Updated", result.getNomBloc());

        // Vérification que save() a été appelé
        verify(blocRepository, times(1)).save(bloc);
    }
    @Test
    void testRetrieveBloc() {
        // Comportement simulé de BlocRepository
        when(blocRepository.findById(1L)).thenReturn(java.util.Optional.of(bloc));

        // Appel de la méthode à tester
        Bloc result = blocService.retrieveBloc(1L);

        // Vérification des résultats
        assertNotNull(result);
        assertEquals("Bloc 1", result.getNomBloc());

        // Vérification que findById() a été appelé
        verify(blocRepository, times(1)).findById(1L);
    }
    @Test
    void testRemoveBloc() {
        // Appel de la méthode à tester
        blocService.removeBloc(1L);

        // Vérification que deleteById() a été appelé avec l'ID du bloc
        verify(blocRepository, times(1)).deleteById(1L);
    }
    @Test
    void testFindByFoyerIdFoyer() {
        // Comportement simulé de BlocRepository
        when(blocRepository.findByFoyerIdFoyer(1L)).thenReturn(List.of(bloc));

        // Appel de la méthode à tester
        List<Bloc> result = blocService.findByFoyerIdFoyer(1L);

        // Vérification des résultats
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Bloc 1", result.get(0).getNomBloc());

        // Vérification que findByFoyerIdFoyer() a été appelé
        verify(blocRepository, times(1)).findByFoyerIdFoyer(1L);
    }
    @Test
    void testFindByChambresIdChambre() {
        // Comportement simulé de BlocRepository
        when(blocRepository.findByChambresIdChambre(1L)).thenReturn(bloc);

        // Appel de la méthode à tester
        Bloc result = blocService.findByChambresIdChambre(1L);

        // Vérification des résultats
        assertNotNull(result);
        assertEquals("Bloc 1", result.getNomBloc());

        // Vérification que findByChambresIdChambre() a été appelé
        verify(blocRepository, times(1)).findByChambresIdChambre(1L);
    }

}
