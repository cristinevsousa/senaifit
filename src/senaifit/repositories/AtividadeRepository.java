package senaifit.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import senaifit.entities.Atividade;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

    public Optional<Atividade> findById(Long id);

    public Optional<Atividade> findByNome(String nome);
}
