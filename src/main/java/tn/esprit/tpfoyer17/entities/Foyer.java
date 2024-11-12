package tn.esprit.tpfoyer17.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;  
import java.util.ArrayList;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Foyer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    long idFoyer;
    public long getIdBloc() {
        return idFoyer;
    }

    public void setIdBloc(long idBloc) {
        this.idFoyer = idBloc;
    }
    String nomFoyer;
    public String getNomBloc() {
        return nomFoyer;
    }

    public void setNomBloc(String nomFoyer) {
        this.nomFoyer = nomFoyer;
    }

    long capaciteFoyer;
    public long getCapaciteBloc() {
        return capaciteFoyer;
    }

    public void setCapaciteBloc(long capaciteFoyer) {
        this.capaciteFoyer = capaciteFoyer;
    }

    @ToString.Exclude
    @JsonIgnore
    @OneToOne(mappedBy = "foyer")
    Universite universite;

    @ToString.Exclude
    //@JsonIgnore
    @OneToMany(mappedBy = "foyer", cascade = CascadeType.ALL)
    Set<Bloc> blocs;
    
    public List<Bloc> getBlocs() {
        return new ArrayList<>(blocs);  
    }



}
