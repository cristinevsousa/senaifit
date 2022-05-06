package senaifit.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class RelatorioService {

    private static final int TEMPO_GRUPO1 = 45;
    private static final int TEMPO_GRUPO2 = 60;
    private static final int TEMPO_GRUPO3 = 30;
    private static final int META_GRUPO1 = 45;
    private static final int META_GRUPO2 = 60;
    private static final int META_GRUPO3 = 30;

    private CheckinService checkinService;

    public RelatorioService(CheckinService checkinService) {
	this.checkinService = checkinService;
    }

    public LocalDateTime geraDataInicialMes(int mes, int ano) {

	LocalDateTime dataInicial = LocalDateTime.of(ano, mes, 1, 0, 0, 0);

	return dataInicial;
    }

    public LocalDateTime geraDataFinalMes(int mes, int ano) {

	int proximoMes = mes + 1;

	if (proximoMes == 13) {
	    proximoMes = 1;
	    ano += 1;
	}
	LocalDateTime dataFinal = LocalDateTime.of(ano, proximoMes, 1, 0, 0, 0);
	return dataFinal;
    }

    public Optional<Integer> contaCheckinsParceiro(long id, int mes, int ano) {

	LocalDateTime dataInicial = geraDataInicialMes(mes, ano);

	LocalDateTime dataFinal = geraDataFinalMes(mes, ano);

	Optional<Integer> numCheckins = this.checkinService.contaCheckinsParceiro(id, dataInicial, dataFinal);

	if (numCheckins.isPresent()) {
	    return numCheckins;
	}
	return null;
    }

    public Optional<Integer> contaCheckinsCliente(long id, int mes, int ano) {

	LocalDateTime dataInicial = geraDataInicialMes(mes, ano);

	LocalDateTime dataFinal = geraDataFinalMes(mes, ano);

	Optional<Integer> numCheckins = this.checkinService.contaCheckinsCliente(id, dataInicial, dataFinal);

	if (numCheckins.isPresent()) {
	    return numCheckins;
	}
	return null;
    }

    public Optional<Integer> contaMetasMesDentroSemanas(int mes, int ano) {

	LocalDateTime dataInicial = geraDataInicialMes(mes, ano);

	LocalDateTime dataFinal = geraDataFinalMes(mes, ano);

	List<Long> clientesAtivos = listaClientesAtivos(mes, ano);

	if (clientesAtivos == null) {
	    return Optional.empty();
	}

	int totalUsuarios = 0;
	for (long id : clientesAtivos) {

	    dataFinal = dataInicial.plusDays(7);
	    Optional<Integer> somaSemana1 = this.checkinService.somaMinutos(id, dataInicial, dataFinal);

	    dataInicial = dataFinal;
	    dataFinal = dataInicial.plusDays(7);
	    Optional<Integer> somaSemana2 = this.checkinService.somaMinutos(id, dataInicial, dataFinal);

	    dataInicial = dataFinal;
	    dataFinal = dataInicial.plusDays(7);
	    Optional<Integer> somaSemana3 = this.checkinService.somaMinutos(id, dataInicial, dataFinal);

	    dataInicial = dataFinal;
	    dataFinal = dataInicial.plusDays(7);
	    Optional<Integer> somaSemana4 = this.checkinService.somaMinutos(id, dataInicial, dataFinal);

	    if (somaSemana1.isPresent() && somaSemana2.isPresent() && somaSemana3.isPresent()
		    && somaSemana4.isPresent()) {

		int somaMinutos = somaSemana1.get() + somaSemana2.get() + somaSemana3.get() + somaSemana4.get();

		if (somaMinutos % TEMPO_GRUPO1 == 0 && somaSemana1.get() >= META_GRUPO1
			&& somaSemana2.get() >= META_GRUPO1 && somaSemana3.get() >= META_GRUPO1
			&& somaSemana4.get() >= META_GRUPO1) {
		    totalUsuarios += 1;

		} else if (somaMinutos % TEMPO_GRUPO2 == 0 && somaSemana1.get() >= META_GRUPO2
			&& somaSemana2.get() >= META_GRUPO2 && somaSemana3.get() >= META_GRUPO2
			&& somaSemana4.get() >= META_GRUPO2) {
		    totalUsuarios += 1;

		} else if (somaMinutos % TEMPO_GRUPO3 == 0 && somaSemana1.get() >= META_GRUPO3
			&& somaSemana2.get() >= META_GRUPO3 && somaSemana3.get() >= META_GRUPO3
			&& somaSemana4.get() >= META_GRUPO3) {
		    totalUsuarios += 1;
		}
	    }
	    return Optional.of(totalUsuarios);
	}
	return null;
    }

    public Optional<Integer> contaMetasMesComMediaSemanas(int mes, int ano) {

	LocalDateTime dataInicial = geraDataInicialMes(mes, ano);

	LocalDateTime dataFinal = geraDataFinalMes(mes, ano);

	List<Long> clientesAtivos = listaClientesAtivos(mes, ano);

	if (clientesAtivos == null) {
	    return null;
	}

	int totalUsuarios = 0;
	for (long id : clientesAtivos) {

	    Optional<Integer> numCheckins = this.checkinService.contaCheckinsCliente(id, dataInicial, dataFinal);
	    Optional<Integer> somaMinutos = this.checkinService.somaMinutos(id, dataInicial, dataFinal);

	    if (numCheckins.isPresent() && somaMinutos.isPresent()) {
		int tempoAtividade = somaMinutos.get() / numCheckins.get();
		int mediaSemana = somaMinutos.get() / 4;

		if ((tempoAtividade == TEMPO_GRUPO1 && mediaSemana >= META_GRUPO1)
			|| (tempoAtividade == TEMPO_GRUPO2 && mediaSemana >= META_GRUPO2)
			|| (tempoAtividade == TEMPO_GRUPO3 && mediaSemana >= META_GRUPO3)) {
		    totalUsuarios += 1;
		}
	    }
	    return Optional.of(totalUsuarios);
	}
	return null;
    }

    public Optional<Integer> contaMetasMinutosMes(int mes, int ano) {

	LocalDateTime dataInicial = geraDataInicialMes(mes, ano);

	LocalDateTime dataFinal = geraDataFinalMes(mes, ano);

	List<Long> clientesAtivos = listaClientesAtivos(mes, ano);

	if (clientesAtivos == null) {
	    return null;
	}

	int totalUsuarios = 0;
	for (long id : clientesAtivos) {

	    Optional<Integer> numCheckins = this.checkinService.contaCheckinsCliente(id, dataInicial, dataFinal);
	    Optional<Integer> somaMinutos = this.checkinService.somaMinutos(id, dataInicial, dataFinal);

	    if (numCheckins.isPresent() && somaMinutos.isPresent()) {
		int tempoAtividade = somaMinutos.get() / numCheckins.get();

		int metaGrupo1 = META_GRUPO1 * 4;
		int metaGrupo2 = META_GRUPO2 * 4;
		int metaGrupo3 = META_GRUPO3 * 4;

		if ((tempoAtividade == TEMPO_GRUPO1 && somaMinutos.get() >= metaGrupo1)
			|| (tempoAtividade == TEMPO_GRUPO2 && somaMinutos.get() >= metaGrupo2)
			|| (tempoAtividade == TEMPO_GRUPO3 && somaMinutos.get() >= metaGrupo3)) {
		    totalUsuarios += 1;
		}
		return Optional.of(totalUsuarios);
	    }
	}
	return null;
    }

    public List<Long> listaClientesAtivos(int mes, int ano) {

	LocalDateTime dataInicial = geraDataInicialMes(mes, ano);

	LocalDateTime dataFinal = geraDataFinalMes(mes, ano);

	Optional<List<Long>> idsDuplicados = this.checkinService.listaIdsClientes(dataInicial, dataFinal);

	if (idsDuplicados.isPresent()) {

	    List<Long> ids = new ArrayList<Long>();

	    for (int i = 0; i < idsDuplicados.get().size(); i++) {
		long item = idsDuplicados.get().get(i);

		if (!(ids.contains(item))) {
		    ids.add(item);
		}
	    }

	    return ids;
	}
	return null;
    }
}
