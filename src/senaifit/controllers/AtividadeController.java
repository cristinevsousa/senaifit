package senaifit.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import senaifit.DTO.AtividadeDTO;
import senaifit.entities.Atividade;
import senaifit.services.AtividadeService;

@RestController
public class AtividadeController {

    private AtividadeService atividadeService;

    public AtividadeController(AtividadeService atividadeService) {
	this.atividadeService = atividadeService;
    }

    @PostMapping("/atividade/")
    public ResponseEntity<String> cadastraAtividade(@Valid @RequestBody AtividadeDTO atividade) {

	String msg = this.atividadeService.cadastraAtividade(atividade);

	if (msg.contains("Erro")) {
	    return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
	}
	return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    @GetMapping("/atividade/{id}")
    private ResponseEntity<Atividade> obtemAtividade(@PathVariable String id) {

	return this.atividadeService.obtemAtividade(Long.parseLong(id)).map(record -> ResponseEntity.ok().body(record))
		.orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/atividade/{id}")
    private ResponseEntity<String> deletaAtividadePorId(@PathVariable String id) {

	boolean retorno = this.atividadeService.deletaAtividadePorId(Long.parseLong(id));
	if (retorno == true) {
	    return new ResponseEntity<>("Atividade removida com sucesso", HttpStatus.ACCEPTED);
	}
	return new ResponseEntity<>("Id não encontrado", HttpStatus.NOT_FOUND);
    }
}
