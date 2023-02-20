package br.com.api.labBeatch.labParadises.repositories;
import br.com.api.labBeatch.labParadises.models.Praia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PraiaRepository extends JpaRepository<Praia,Long> {
    Praia findByNome(String nome);


}
