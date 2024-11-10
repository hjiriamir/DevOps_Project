package tn.esprit.tpfoyer17;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableScheduling
@SpringBootApplication
@EntityScan(basePackages = "tn.esprit.tpfoyer17.entities")
@EnableJpaRepositories(basePackages = "tn.esprit.tpfoyer17.repositories")
public class TpFoyer17Application {

    public static void main(String[] args) {
        SpringApplication.run(TpFoyer17Application.class, args);
    }

}
