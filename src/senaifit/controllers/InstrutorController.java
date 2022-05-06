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

import senaifit.DTO.InstrutorDTO;
import senaifit.entities.Instrutor;
import senaifit.services.InstrutorService;

@RestController
public class InstrutorController {

    private InstrutorService instrutorService;

    public InstrutorController(InstrutorService instrutorService) {
	this.instrutorService = instrutorService;
    }

    @PostMapping("/instrutor/")
    public ResponseEntity<String> salvaInstrutor(@Valid @RequestBody InstrutorDTO instrutor) {

	String msg = this.instrutorService.cadastraInstrutor(instrutor);

	if (msg.contains("Erro")) {
	    return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
	}
	return new ResponseEntity<>(msg, HttpStatus.CREATED);
    }

    @GetMapping("/instrutor/{id}")
    private ResponseEntity<Instrutor> obtemInstrutor(@PathVariable String id) {

	return this.instrutorService.obtemInstrutor(Long.parseLong(id)).map(record -> ResponseEntity.ok().body(record))
		.orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/instrutor/{id}")
    private ResponseEntity<String> deletaInstrutorPorId(@PathVariable String id) {

	boolean retorno = this.instrutorService.deletaInstrutorPorId(Long.parseLong(id));
	if (retorno == true) {
	    return new ResponseEntity<>("Instrutor removido com sucesso", HttpStatus.ACCEPTED);
	}
	return new ResponseEntity<>("Id não encontrado", HttpStatus.NOT_FOUND);
    }
}
