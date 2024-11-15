package tn.esprit.tpfoyer17.services.impementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.interfaces.IUniversiteService;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UniversiteService implements IUniversiteService {
    UniversiteRepository universiteRepository;
    FoyerRepository foyerRepository;

    @Override
    public List<Universite> retrieveAllUniversities() {
        return (List<Universite>) universiteRepository.findAll();
    }

    @Override
    public Universite addUniversity(Universite u) {
        return universiteRepository.save(u);
    }

    public void deleteUniversity(long idUniversity) {
        if (universiteRepository.existsById(idUniversity)) {
            universiteRepository.deleteById(idUniversity);
        } else {
            throw new EntityNotFoundException("Université non trouvée avec l'ID: " + idUniversity);
        }
    }

    @Override
    public Universite updateUniversity(Universite u) {
        return universiteRepository.save(u);
    }

    @Override
    public Universite retrieveUniversity(long idUniversity) {
        return universiteRepository.findById(idUniversity).orElse(null);
    }

    @Override
    public Universite desaffecterFoyerAUniversite(long idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite).orElse(null);
        if (universite != null) {
            universite.setFoyer(null);
            return universiteRepository.save(universite);
        }
        return null;
    }

    public List<Universite> filterUniversities(String nomUniversite, String adresse) {
        return universiteRepository.findByNomUniversiteContainingAndAdresseContaining(nomUniversite, adresse);
    }

    @Override
    public Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite) {
        Foyer foyer = foyerRepository.findById(idFoyer).orElse(null);
        Universite universite = universiteRepository.findByNomUniversiteLike(nomUniversite);
        universite.setFoyer(foyer);
        return universiteRepository.save(universite);
    }

    public List<Universite> retrouverUniversitesParFoyer(boolean avecFoyer) {
        if (avecFoyer) {
            return universiteRepository.findByFoyerIsNotNull();
        } else {
            return universiteRepository.findByFoyerIsNull();
        }
    }

    // Advanced service method with conditional logic
    public Universite processUniversityAction(long idUniversite, String actionType) {
        Universite universite = universiteRepository.findById(idUniversite).orElse(null);

        if (universite == null) {
            throw new EntityNotFoundException("Université non trouvée avec l'ID: " + idUniversite);
        }

        switch (actionType.toLowerCase()) {
            case "affecter_foyer":
                // Sample logic to assign a foyer
                Foyer foyer = foyerRepository.findById(1L) // Assuming 1L is a sample Foyer ID
                        .orElseThrow(() -> new EntityNotFoundException("Foyer not found"));
                universite.setFoyer(foyer);
                log.info("Foyer affected to Université with ID: {}", idUniversite);
                break;

            case "desaffecter_foyer":
                universite.setFoyer(null);
                log.info("Foyer removed from Université with ID: {}", idUniversite);
                break;

            case "update_adresse":
                // Example case to update the address conditionally
                if ("old_address".equalsIgnoreCase(universite.getAdresse())) {
                    universite.setAdresse("new_address");
                    log.info("Address updated for Université with ID: {}", idUniversite);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown action type: " + actionType);
        }

        return universiteRepository.save(universite);
    }
}
