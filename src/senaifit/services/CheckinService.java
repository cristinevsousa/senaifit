package senaifit.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senaifit.entities.Checkin;
import senaifit.entities.Cliente;
import senaifit.entities.FaixaEtaria;
import senaifit.repositories.CheckinRepository;

@Service
public class CheckinService {

    private static final int TEMPO_GRUPO1 = 45;
    private static final int TEMPO_GRUPO2 = 60;
    private static final int TEMPO_GRUPO3 = 30;
    private static final int META_GRUPO1 = 45;
    private static final int META_GRUPO2 = 60;
    private static final int META_GRUPO3 = 30;

    @Autowired
    private CheckinRepository checkinRepo;

    public void salvaCheckin(@Valid Checkin checkin) {
	this.checkinRepo.save(checkin);
    }

    public int somaMinutos(long id, LocalDateTime dataInicial, LocalDateTime dataFinal) {

	Optional<Integer> resultado = this.checkinRepo.somaMinutos(id, dataInicial, dataFinal);

	if (resultado.isPresent()) {
	    return resultado.orElse(0);
	}

	return resultado.get();
    }

    public int contaCheckinsCliente(long id, LocalDateTime dataInicial, LocalDateTime dataFinal) {

	Optional<Integer> resultado = this.checkinRepo.contaCheckinsCliente(id, dataInicial, dataFinal);

	if (resultado.isEmpty()) {
	    return resultado.orElse(0);
	}

	return resultado.get();
    }

    public int contaCheckinsParceiro(long id, LocalDateTime dataInicial, LocalDateTime dataFinal) {

	Optional<Integer> resultado = this.checkinRepo.contaCheckinsParceiro(id, dataInicial, dataFinal);

	if (resultado.isEmpty()) {
	    return resultado.orElse(0);
	}

	return resultado.get();
    }

    public List<Checkin> listaCheckinsPorData(int mes, int ano) {

	int proximoMes = mes + 1;
	LocalDateTime dataInicial = LocalDateTime.of(ano, mes, 1, 0, 0, 0);

	if (proximoMes == 13) {
	    proximoMes = 1;
	    ano += 1;
	}
	LocalDateTime dataFinal = LocalDateTime.of(ano, proximoMes, 1, 0, 0, 0);

	return this.checkinRepo.listaCheckinsPorData(dataInicial, dataFinal);
    }

    public List<Long> listaIdsClientes(LocalDateTime dataInicial, LocalDateTime dataFinal) {

	return this.checkinRepo.listaIdsClientes(dataInicial, dataFinal);
    }

    public List<Checkin> listaTodosCheckins() {

	return this.checkinRepo.findAll();
    }

    public int geraTempoSugerido(Optional<Cliente> cliente, Checkin checkin) {

	int tempoSugerido = 0;
	FaixaEtaria faixaEtaria = setFaixaEtaria(cliente, checkin);

	if (faixaEtaria == FaixaEtaria.GRUPO1) {
	    tempoSugerido = TEMPO_GRUPO1;
	    checkin.setTempoAtividade(tempoSugerido);
	} else if (faixaEtaria == FaixaEtaria.GRUPO2) {
	    tempoSugerido = TEMPO_GRUPO2;
	    checkin.setTempoAtividade(tempoSugerido);
	} else if (faixaEtaria == FaixaEtaria.GRUPO3) {
	    tempoSugerido = TEMPO_GRUPO3;
	    checkin.setTempoAtividade(tempoSugerido);
	}

	return tempoSugerido;
    }

    public FaixaEtaria setFaixaEtaria(Optional<Cliente> cliente, Checkin checkin) {

	LocalDate dataHoje = LocalDate.now();

	LocalDate dataNasc = cliente.get().getDataNasc();

	Period periodo = Period.between(dataNasc, dataHoje);
	int idade = periodo.getYears();

	FaixaEtaria faixaEtaria = null;

	if (idade < 14 || idade >= 14 && idade < 18) {
	    faixaEtaria = FaixaEtaria.GRUPO1;
	    checkin.setFaixaEtaria(faixaEtaria);
	} else if (idade >= 18 && idade <= 60) {
	    faixaEtaria = FaixaEtaria.GRUPO2;
	    checkin.setFaixaEtaria(faixaEtaria);
	} else if (idade > 60) {
	    faixaEtaria = FaixaEtaria.GRUPO3;
	    checkin.setFaixaEtaria(faixaEtaria);
	}

	return faixaEtaria;
    }

    public String verificaCheckin(Optional<Cliente> cliente, Checkin checkin) {

	Long clienteId = cliente.get().getId();

	LocalDateTime dataHoje = LocalDateTime.now();
	LocalDateTime dataInicial = dataHoje.minusDays(6);

	int minutosSemana = this.somaMinutos(clienteId, dataInicial, dataHoje);

	int tempoSugerido = geraTempoSugerido(cliente, checkin);

	String msgAviso = "Cuidado! O excesso de atividade física também pode fazer mal para sua saúde!";
	String msgSugestao = "Sua recomendação de atividade é: Caminhada" + ", " + tempoSugerido + " minutos";

	if (tempoSugerido == TEMPO_GRUPO1 && minutosSemana > META_GRUPO1) {
	    return msgAviso;

	} else if (tempoSugerido == TEMPO_GRUPO2 && minutosSemana > META_GRUPO2) {
	    return msgAviso;

	} else if (tempoSugerido == TEMPO_GRUPO3 && minutosSemana > META_GRUPO3) {
	    return msgAviso;

	} else {
	    return msgSugestao;
	}
    }

    public void deletaCheckinPorId(long id) {

	this.checkinRepo.deleteById(id);
    }
}
