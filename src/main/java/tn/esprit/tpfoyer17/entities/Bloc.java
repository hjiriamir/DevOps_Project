package tn.esprit.tpfoyer17.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Set;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bloc implements Serializable {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    long idBloc;
    public long getIdBloc() {
        return idBloc;
    }

    public void setIdBloc(long idBloc) {
        this.idBloc = idBloc;
    }
    String nomBloc;
    public String getNomBloc() {
        return nomBloc;
    }

    public void setNomBloc(String nomBloc) {
        this.nomBloc = nomBloc;
    }
    long capaciteBloc;
    public long getCapaciteBloc() {
        return capaciteBloc;
    }

    public void setCapaciteBloc(long capaciteBloc) {
        this.capaciteBloc = capaciteBloc;
    }

    @ToString.Exclude
    @ManyToOne
    @JsonIgnore
    Foyer foyer;
    public void setFoyer(Foyer foyer) {
        this.foyer = foyer;
    }
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "bloc")
    private Set<Chambre> chambres;
    private Bloc(Builder builder) {
        this.nomBloc = builder.nomBloc;
        this.capaciteBloc = builder.capaciteBloc;
        this.foyer = builder.foyer;
        this.chambres = builder.chambres;
    }

    // Sous-classe Builder
    public static class Builder {
        private String nomBloc;
        private long capaciteBloc;
        private Foyer foyer;
        private Set<Chambre> chambres;

        public Builder nomBloc(String nomBloc) {
            this.nomBloc = nomBloc;
            return this;
        }

        public Builder capaciteBloc(long capaciteBloc) {
            this.capaciteBloc = capaciteBloc;
            return this;
        }

        public Builder foyer(Foyer foyer) {
            this.foyer = foyer;
            return this;
        }

        public Builder chambres(Set<Chambre> chambres) {
            this.chambres = chambres;
            return this;
        }

        // Méthode build pour créer l'instance de Bloc
        public Bloc build() {
            return new Bloc(this);
        }
    }

}
