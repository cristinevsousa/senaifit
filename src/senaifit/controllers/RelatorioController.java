package senaifit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import senaifit.services.RelatorioService;

@RestController
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/relatorios/parceiro/{id}/{mes}/{ano}")
    public ResponseEntity<Integer> contaCheckinsParceiro(@PathVariable String id, @PathVariable String mes,
	    @PathVariable String ano) {

	int numCheckinsMes = this.relatorioService.contaCheckinsParceiro(Long.parseLong(id), Integer.parseInt(mes),
		Integer.parseInt(ano));

	return ResponseEntity.ok(numCheckinsMes);
    }

    @GetMapping("/relatorios/cliente/{id}/{mes}/{ano}")
    public ResponseEntity<Integer> contaCheckinsCliente(@PathVariable String id, @PathVariable String mes,
	    @PathVariable String ano) {

	int numCheckinsMes = this.relatorioService.contaCheckinsCliente(Long.parseLong(id), Integer.parseInt(mes),
		Integer.parseInt(ano));

	return ResponseEntity.ok(numCheckinsMes);

    }

    @GetMapping("/relatorios/clientes/metas-atingidas/semana-a-semana/mes/{mes}/{ano}")
    public ResponseEntity<Integer> contaMetasMesDentroSemanas(@PathVariable String mes, @PathVariable String ano) {

	int numMetasMes = this.relatorioService.contaMetasMesDentroSemanas(Integer.parseInt(mes),
		Integer.parseInt(ano));

	return ResponseEntity.ok(numMetasMes);
    }

    @GetMapping("/relatorios/clientes/metas-atingidas/media/mes/{mes}/{ano}")
    public ResponseEntity<Integer> contaMetasMesComMediaSemana(@PathVariable String mes, @PathVariable String ano) {

	int numMetasMes = this.relatorioService.contaMetasMesComMediaSemana(Integer.parseInt(mes),
		Integer.parseInt(ano));

	return ResponseEntity.ok(numMetasMes);
    }

    @GetMapping("/relatorios/clientes/metas-atingidas/minutos/mes/{mes}/{ano}")
    public ResponseEntity<Integer> contaMetasMinutosMes(@PathVariable String mes, @PathVariable String ano) {

	int numMetasMes = this.relatorioService.contaMetasMinutosMes(Integer.parseInt(mes), Integer.parseInt(ano));

	return ResponseEntity.ok(numMetasMes);
    }
}
