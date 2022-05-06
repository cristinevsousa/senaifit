package senaifit.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import senaifit.DTO.CheckinDTO;
import senaifit.entities.Atividade;
import senaifit.entities.Checkin;
import senaifit.entities.Cliente;
import senaifit.entities.FaixaEtaria;
import senaifit.entities.Parceiro;
import senaifit.repositories.CheckinRepository;

@Service
public class CheckinService {

    private static final int TEMPO_GRUPO1 = 45;
    private static final int TEMPO_GRUPO2 = 60;
    private static final int TEMPO_GRUPO3 = 30;
    private static final int META_GRUPO1 = 45;
    private static final int META_GRUPO2 = 60;
    private static final int META_GRUPO3 = 30;

    private CheckinRepository checkinRepo;

    private ClienteService clienteService;

    private ParceiroService parceiroService;

    private AtividadeService atividadeService;

    public CheckinService(CheckinRepository checkinRepo, ClienteService clienteService, ParceiroService parceiroService,
	    AtividadeService atividadeService) {
	this.checkinRepo = checkinRepo;
	this.clienteService = clienteService;
	this.parceiroService = parceiroService;
	this.atividadeService = atividadeService;
    }

    public String salvaCheckin(CheckinDTO checkinDTO) {

	long clienteId = checkinDTO.getClienteId();
	Optional<Cliente> cliente = this.clienteService.obtemCliente(clienteId);

	if (cliente.isEmpty()) {
	    return "Requisiçao inválida, CLIENTE não encontrado!";
	}

	long parceiroId = checkinDTO.getParceiroId();
	Optional<Parceiro> parceiro = this.parceiroService.obtemParceiro(parceiroId);

	if (parceiro.isEmpty()) {
	    return "Requisiçao inválida, PARCEIRO não encontrado!";
	}

	long atividadeId = 1;
	Optional<Atividade> atividade = this.atividadeService.obtemAtividade(atividadeId);

	if (atividade.isEmpty()) {
	    return "Requisiçao inválida, ATIVIDADE não encontrada!";
	}

	Checkin checkin = new Checkin();

	String msgCheckin = this.verificaCheckin(cliente, checkin);

	checkin.setCliente(cliente.get());
	checkin.setParceiro(parceiro.get());
	checkin.setAtividade(atividade.get());
	checkin.setId(clienteId);

	this.checkinRepo.save(checkin);

	return "Checkin realizado com sucesso! " + msgCheckin;
    }

    public Optional<Checkin> obtemCheckin(long checkinId) {

	Optional<Checkin> checkin = this.checkinRepo.findById(checkinId);

	if (checkin.isPresent()) {
	    return checkin;
	}
	return Optional.empty();
    }

    public boolean deletaCheckinPorId(long checkinId) {

	Optional<Checkin> checkin = obtemCheckin(checkinId);
	if (checkin.isPresent()) {
	    this.checkinRepo.deleteById(checkinId);
	    return true;
	}
	return false;
    }

    public Optional<Integer> somaMinutos(long clienteId, LocalDateTime dataInicial, LocalDateTime dataFinal) {

	Optional<Integer> resultado = this.checkinRepo.somaMinutos(clienteId, dataInicial, dataFinal);

	if (resultado.isPresent()) {
	    return resultado;
	}
	return Optional.of(0);
    }

    public Optional<Integer> contaCheckinsCliente(long clienteId, LocalDateTime dataInicial, LocalDateTime dataFinal) {

	Optional<Integer> resultado = this.checkinRepo.contaCheckinsCliente(clienteId, dataInicial, dataFinal);

	return resultado;
    }

    public Optional<Integer> contaCheckinsParceiro(long parceiroId, LocalDateTime dataInicial,
	    LocalDateTime dataFinal) {

	Optional<Integer> resultado = this.checkinRepo.contaCheckinsParceiro(parceiroId, dataInicial, dataFinal);

	return resultado;
    }

    public Optional<List<Long>> listaIdsClientes(LocalDateTime dataInicial, LocalDateTime dataFinal) {

	Optional<List<Long>> idsCliente = this.checkinRepo.listaIdsClientes(dataInicial, dataFinal);
	return idsCliente;
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

	Optional<Integer> minutosSemana = this.somaMinutos(clienteId, dataInicial, dataHoje);

	if (minutosSemana.isPresent()) {

	    int tempoSugerido = geraTempoSugerido(cliente, checkin);

	    String msgAviso = "Cuidado! O excesso de atividade física também pode fazer mal para sua saúde!";
	    String msgSugestao = "Sua recomendação de atividade é: Caminhada" + ", " + tempoSugerido + " minutos";

	    if (tempoSugerido == TEMPO_GRUPO1 && minutosSemana.get() > META_GRUPO1) {
		return msgAviso;

	    } else if (tempoSugerido == TEMPO_GRUPO2 && minutosSemana.get() > META_GRUPO2) {
		return msgAviso;

	    } else if (tempoSugerido == TEMPO_GRUPO3 && minutosSemana.get() > META_GRUPO3) {
		return msgAviso;
	    } else {
		return msgSugestao;
	    }
	}
	return null;
    }
}
