package senaifit.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import senaifit.entities.Instrutor;

@Repository
public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {

    public Optional<Instrutor> findById(Long id);

}
