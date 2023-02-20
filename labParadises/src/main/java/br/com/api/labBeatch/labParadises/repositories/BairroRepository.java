package br.com.api.labBeatch.labParadises.repositories;

import br.com.api.labBeatch.labParadises.models.Bairro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BairroRepository extends JpaRepository<Bairro,Long> {
    Bairro findByNome(String nome);

}
