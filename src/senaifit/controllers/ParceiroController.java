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

import senaifit.DTO.ParceiroDTO;
import senaifit.entities.Parceiro;
import senaifit.services.ParceiroService;

@RestController
public class ParceiroController {

    private ParceiroService parceiroService;

    public ParceiroController(ParceiroService parceiroService) {
	this.parceiroService = parceiroService;
    }

    @PostMapping("/parceiro/")
    public ResponseEntity<String> cadastraParceiro(@Valid @RequestBody ParceiroDTO parceiro) {

	String msg = this.parceiroService.cadastraParceiro(parceiro);

	if (msg.contains("Erro")) {
	    return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
	}
	return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    @GetMapping("/parceiro/{id}")
    private ResponseEntity<Parceiro> obtemParceiro(@PathVariable String id) {

	return this.parceiroService.obtemParceiro(Long.parseLong(id)).map(record -> ResponseEntity.ok().body(record))
		.orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/parceiro/{id}")
    private ResponseEntity<String> deletaParceiroPorId(@PathVariable String id) {

	boolean retorno = this.parceiroService.deletaParceiroPorId(Long.parseLong(id));
	if (retorno == true) {
	    return new ResponseEntity<>("Parceiro removido com sucesso", HttpStatus.ACCEPTED);
	}
	return new ResponseEntity<>("Id não encontrado", HttpStatus.NOT_FOUND);
    }
}
