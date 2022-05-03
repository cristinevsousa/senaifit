package senaifit.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import senaifit.entities.Checkin;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Long> {

    public Optional<Checkin> findById(Long id);

    @Query(value = "SELECT SUM(c.tempo_atividade) FROM CHECKIN c WHERE c.cliente_id = :clienteId AND c.data_checkin BETWEEN :dataInicial AND :dataFinal", nativeQuery = true)
    public Optional<Integer> somaMinutos(@Param("clienteId") Long clienteId,
	    @Param("dataInicial") LocalDateTime dataInicial, @Param("dataFinal") LocalDateTime dataFinal);

    @Query(value = "SELECT COUNT(c.parceiro_id) FROM CHECKIN c WHERE c.parceiro_id = :parceiroId AND c.data_checkin BETWEEN :dataInicial AND :dataFinal", nativeQuery = true)
    public Optional<Integer> contaCheckinsParceiro(@Param("parceiroId") Long parceiroId,
	    @Param("dataInicial") LocalDateTime dataInicial, @Param("dataFinal") LocalDateTime dataFinal);

    @Query(value = "SELECT COUNT(c.cliente_id) FROM CHECKIN c WHERE c.cliente_id = :clienteId AND c.data_checkin BETWEEN :dataInicial AND :dataFinal", nativeQuery = true)
    public Optional<Integer> contaCheckinsCliente(@Param("clienteId") Long clienteId,
	    @Param("dataInicial") LocalDateTime dataInicial, @Param("dataFinal") LocalDateTime dataFinal);

    @Query(value = "SELECT c.cliente_id FROM CHECKIN c WHERE c.data_checkin BETWEEN :dataInicial AND :dataFinal", nativeQuery = true)
    public List<Long> listaIdsClientes(@Param("dataInicial") LocalDateTime dataInicial,
	    @Param("dataFinal") LocalDateTime dataFinal);

    @Query(value = "SELECT * FROM CHECKIN c WHERE c.data_checkin BETWEEN :dataInicial AND :dataFinal", nativeQuery = true)
    public List<Checkin> listaCheckinsPorData(@Param("dataInicial") LocalDateTime dataInicial,
	    @Param("dataFinal") LocalDateTime dataFinal);
}
