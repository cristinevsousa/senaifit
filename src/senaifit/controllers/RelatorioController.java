package senaifit.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import senaifit.services.RelatorioService;

@RestController
public class RelatorioController {

    private RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
	this.relatorioService = relatorioService;
    }

    @GetMapping("/relatorios/parceiros/{id}/{mes}-{ano}")
    public ResponseEntity<String> contaCheckinsParceiro(@PathVariable String id, @PathVariable String mes,
	    @PathVariable String ano) {

	Optional<Integer> numCheckinsMes = this.relatorioService.contaCheckinsParceiro(Long.parseLong(id),
		Integer.parseInt(mes), Integer.parseInt(ano));

	if (numCheckinsMes.isPresent()) {
	    String valor = String.valueOf(numCheckinsMes.get());
	    return new ResponseEntity<>(valor, HttpStatus.OK);

	}
	return new ResponseEntity<>("Algo deu errado", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/relatorios/clientes/{id}/{mes}-{ano}")
    public ResponseEntity<String> contaCheckinsCliente(@PathVariable String id, @PathVariable String mes,
	    @PathVariable String ano) {

	Optional<Integer> numCheckinsMes = this.relatorioService.contaCheckinsCliente(Long.parseLong(id),
		Integer.parseInt(mes), Integer.parseInt(ano));

	if (numCheckinsMes.isPresent()) {
	    String valor = String.valueOf(numCheckinsMes.get());
	    return new ResponseEntity<>(valor, HttpStatus.OK);

	}
	return new ResponseEntity<>("Algo deu errado", HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/relatorios/clientes/metas-atingidas-todas-semanas/{mes}-{ano}")
    public ResponseEntity<String> contaMetasMesDentroSemanas(@PathVariable String mes, @PathVariable String ano) {

	Optional<Integer> numMetasMes = this.relatorioService.contaMetasMesDentroSemanas(Integer.parseInt(mes),
		Integer.parseInt(ano));

	if (numMetasMes.isPresent()) {
	    String valor = String.valueOf(numMetasMes.get());
	    return new ResponseEntity<>(valor, HttpStatus.OK);
	}
	return new ResponseEntity<>("Algo deu errado", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/relatorios/clientes/metas-atingidas-mes-media-semanas/{mes}-{ano}")
    public ResponseEntity<String> contaMetasMesComMediaSemanas(@PathVariable String mes, @PathVariable String ano) {

	Optional<Integer> numMetasMes = this.relatorioService.contaMetasMesComMediaSemanas(Integer.parseInt(mes),
		Integer.parseInt(ano));

	if (numMetasMes.isPresent()) {
	    String valor = String.valueOf(numMetasMes.get());
	    return new ResponseEntity<>(valor, HttpStatus.OK);
	}
	return new ResponseEntity<>("Algo deu errado", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/relatorios/clientes/metas-atingidas-minutos-mes/{mes}/{ano}")
    public ResponseEntity<String> contaMetasMinutosMes(@PathVariable String mes, @PathVariable String ano) {

	Optional<Integer> numMetasMes = this.relatorioService.contaMetasMinutosMes(Integer.parseInt(mes),
		Integer.parseInt(ano));

	if (numMetasMes.isPresent()) {
	    String valor = String.valueOf(numMetasMes.get());
	    return new ResponseEntity<>(valor, HttpStatus.OK);
	}
	return new ResponseEntity<>("Algo deu errado", HttpStatus.BAD_REQUEST);
    }
}
