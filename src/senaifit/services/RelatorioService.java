package senaifit.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelatorioService {

    private static final int TEMPO_GRUPO1 = 45;
    private static final int TEMPO_GRUPO2 = 60;
    private static final int TEMPO_GRUPO3 = 30;
    private static final int META_GRUPO1 = 45;
    private static final int META_GRUPO2 = 60;
    private static final int META_GRUPO3 = 30;

    @Autowired
    private CheckinService checkinService;

    public int contaCheckinsParceiro(long id, int mes, int ano) {

	int proximoMes = mes + 1;
	LocalDateTime dataInicial = LocalDateTime.of(ano, mes, 1, 0, 0, 0);

	if (proximoMes == 13) {
	    proximoMes = 1;
	    ano += 1;
	}

	LocalDateTime dataFinal = LocalDateTime.of(ano, proximoMes, 1, 0, 0, 0);

	int numCheckins = this.checkinService.contaCheckinsParceiro(id, dataInicial, dataFinal);

	return numCheckins;
    }

    public int contaCheckinsCliente(long id, int mes, int ano) {

	int proximoMes = mes + 1;
	LocalDateTime dataInicial = LocalDateTime.of(ano, mes, 1, 0, 0, 0);

	if (proximoMes == 13) {
	    proximoMes = 1;
	    ano += 1;
	}
	LocalDateTime dataFinal = LocalDateTime.of(ano, proximoMes, 1, 0, 0, 0);

	int numCheckins = this.checkinService.contaCheckinsCliente(id, dataInicial, dataFinal);

	return numCheckins;
    }

    public int contaMetasMesDentroSemanas(int mes, int ano) {

	int proximoMes = mes + 1;
	LocalDateTime dataInicial = LocalDateTime.of(ano, mes, 1, 0, 0, 0);

	if (proximoMes == 13) {
	    proximoMes = 1;
	    ano += 1;
	}
	LocalDateTime dataFinal = LocalDateTime.of(ano, proximoMes, 1, 0, 0, 0);

	List<Long> clientesAtivos = listaClientesAtivos(mes, ano);

	int totalUsuarios = 0;
	for (long id : clientesAtivos) {

	    dataFinal = dataInicial.plusDays(7);
	    int somaSemana1 = this.checkinService.somaMinutos(id, dataInicial, dataFinal);

	    dataInicial = dataFinal;
	    dataFinal = dataInicial.plusDays(7);
	    int somaSemana2 = this.checkinService.somaMinutos(id, dataInicial, dataFinal);

	    dataInicial = dataFinal;
	    dataFinal = dataInicial.plusDays(7);
	    int somaSemana3 = this.checkinService.somaMinutos(id, dataInicial, dataFinal);

	    dataInicial = dataFinal;
	    dataFinal = dataInicial.plusDays(7);
	    int somaSemana4 = this.checkinService.somaMinutos(id, dataInicial, dataFinal);

	    int somaMinutos = somaSemana1 + somaSemana2 + somaSemana3 + somaSemana4;

	    if (somaMinutos % TEMPO_GRUPO1 == 0 && somaSemana1 >= META_GRUPO1 && somaSemana2 >= META_GRUPO1
		    && somaSemana3 >= META_GRUPO1 && somaSemana4 >= META_GRUPO1) {
		totalUsuarios += 1;

	    } else if (somaMinutos % TEMPO_GRUPO2 == 0 && somaSemana1 >= META_GRUPO2 && somaSemana2 >= META_GRUPO2
		    && somaSemana3 >= META_GRUPO2 && somaSemana4 >= META_GRUPO2) {
		totalUsuarios += 1;

	    } else if (somaMinutos % TEMPO_GRUPO3 == 0 && somaSemana1 >= META_GRUPO3 && somaSemana2 >= META_GRUPO3
		    && somaSemana3 >= META_GRUPO3 && somaSemana4 >= META_GRUPO3) {
		totalUsuarios += 1;
	    }
	}

	return totalUsuarios;
    }

    public int contaMetasMesComMediaSemana(int mes, int ano) {

	int proximoMes = mes + 1;
	LocalDateTime dataInicial = LocalDateTime.of(ano, mes, 1, 0, 0, 0);

	if (proximoMes == 13) {
	    proximoMes = 1;
	    ano += 1;
	}
	LocalDateTime dataFinal = LocalDateTime.of(ano, proximoMes, 1, 0, 0, 0);

	List<Long> clientesAtivos = listaClientesAtivos(mes, ano);

	int totalUsuarios = 0;
	for (long id : clientesAtivos) {

	    int numCheckins = this.checkinService.contaCheckinsCliente(id, dataInicial, dataFinal);
	    int somaMinutos = this.checkinService.somaMinutos(id, dataInicial, dataFinal);
	    int tempoAtividade = somaMinutos / numCheckins;
	    int mediaSemana = somaMinutos / 4;

	    if ((tempoAtividade == TEMPO_GRUPO1 && mediaSemana >= META_GRUPO1)
		    || (tempoAtividade == TEMPO_GRUPO2 && mediaSemana >= META_GRUPO2)
		    || (tempoAtividade == TEMPO_GRUPO3 && mediaSemana >= META_GRUPO3)) {
		totalUsuarios += 1;
	    }
	}

	return totalUsuarios;
    }

    public int contaMetasMinutosMes(int mes, int ano) {

	int proximoMes = mes + 1;
	LocalDateTime dataInicial = LocalDateTime.of(ano, mes, 1, 0, 0, 0);

	if (proximoMes == 13) {
	    proximoMes = 1;
	    ano += 1;
	}
	LocalDateTime dataFinal = LocalDateTime.of(ano, proximoMes, 1, 0, 0, 0);

	List<Long> clientesAtivos = listaClientesAtivos(mes, ano);

	int totalUsuarios = 0;
	for (long id : clientesAtivos) {

	    int numCheckins = this.checkinService.contaCheckinsCliente(id, dataInicial, dataFinal);
	    int somaMinutos = this.checkinService.somaMinutos(id, dataInicial, dataFinal);
	    int tempoAtividade = somaMinutos / numCheckins;

	    int metaGrupo1 = META_GRUPO1 * 4;
	    int metaGrupo2 = META_GRUPO2 * 4;
	    int metaGrupo3 = META_GRUPO3 * 4;

	    if ((tempoAtividade == TEMPO_GRUPO1 && somaMinutos >= metaGrupo1)
		    || (tempoAtividade == TEMPO_GRUPO2 && somaMinutos >= metaGrupo2)
		    || (tempoAtividade == TEMPO_GRUPO3 && somaMinutos >= metaGrupo3)) {
		totalUsuarios += 1;
	    }
	}

	return totalUsuarios;
    }

    public List<Long> listaClientesAtivos(int mes, int ano) {

	int proximoMes = mes + 1;
	LocalDateTime dataInicial = LocalDateTime.of(ano, mes, 1, 0, 0, 0);

	if (proximoMes == 13) {
	    proximoMes = 1;
	    ano += 1;
	}
	LocalDateTime dataFinal = LocalDateTime.of(ano, proximoMes, 1, 0, 0, 0);

	List<Long> idsDuplicados = this.checkinService.listaIdsClientes(dataInicial, dataFinal);

	List<Long> ids = new ArrayList<Long>();

	for (int i = 0; i < idsDuplicados.size(); i++) {
	    long item = idsDuplicados.get(i);

	    if (!(ids.contains(item))) {
		ids.add(item);
	    }
	}

	return ids;
    }
}
