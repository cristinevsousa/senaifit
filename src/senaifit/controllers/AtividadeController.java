package senaifit.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AtividadeService atividadeService;

    @PostMapping("/atividade/")
    public ResponseEntity<String> salvaAtividade(@Valid @RequestBody AtividadeDTO atividadeDTO) {

	Atividade atividade = new Atividade();
	atividade.setNome(atividadeDTO.getNome());

	this.atividadeService.salvaAtividade(atividade);

	return new ResponseEntity<>("Atividade cadastrada com sucesso", HttpStatus.CREATED);

    }

    @GetMapping("/atividade/{id}")
    private ResponseEntity<Atividade> obtemAtividade(@PathVariable String id) {

	Optional<Atividade> atividade = this.atividadeService.obtemAtividade(Long.parseLong(id));

	return ResponseEntity.of(atividade);
    }

    @GetMapping("/atividade/{nome}")
    private ResponseEntity<Atividade> obtemAtividadePorNome(@PathVariable String nome) {

	Optional<Atividade> atividade = this.atividadeService.obtemAtividadePorNome(String.valueOf(nome));

	return ResponseEntity.of(atividade);
    }

    @DeleteMapping("/atividade/remove/{id}")
    private ResponseEntity<String> deletaAtividadePorId(@PathVariable String id) {

	this.atividadeService.deletaAtividadePorId(Long.parseLong(id));

	return new ResponseEntity<>("Atividade removida com sucesso", HttpStatus.ACCEPTED);
    }

}
