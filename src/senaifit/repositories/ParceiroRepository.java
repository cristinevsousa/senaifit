package senaifit.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import senaifit.entities.Parceiro;

@Repository
public interface ParceiroRepository extends JpaRepository<Parceiro, Long> {

    public Optional<Parceiro> findById(Long id);

}
