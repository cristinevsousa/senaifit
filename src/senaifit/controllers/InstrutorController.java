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

import senaifit.DTO.InstrutorDTO;
import senaifit.entities.Instrutor;
import senaifit.services.InstrutorService;

@RestController
public class InstrutorController {

    @Autowired
    private InstrutorService instrutorService;

    @PostMapping("/instrutor/")
    public ResponseEntity<String> salvaInstrutor(@Valid @RequestBody InstrutorDTO instrutorDTO) {

	Instrutor instrutor = new Instrutor();
	instrutor.setEndereco(instrutorDTO.getEndereco());
	instrutor.setNome(instrutorDTO.getNome());
	instrutor.setNumRegistro(instrutorDTO.getNumRegistro());
	instrutor.setId(instrutorDTO.getId());
	instrutor.setAltura(instrutorDTO.getAltura());
	instrutor.setCpf(instrutorDTO.getCpf());
	instrutor.setDataNasc(instrutorDTO.getDataNasc());
	instrutor.setPeso(instrutorDTO.getPeso());
	instrutor.setSexo(instrutorDTO.getSexo());

	this.instrutorService.cadastraInstrutor(instrutor);

	return new ResponseEntity<>("Instrutor cadastrado com sucesso!", HttpStatus.CREATED);
    }

    @GetMapping("/instrutor/{id}")
    public ResponseEntity<Instrutor> obtemInstrutor(@PathVariable String id) {

	Optional<Instrutor> instrutor = this.instrutorService.obtemInstrutor(Long.parseLong(id));
	return ResponseEntity.of(instrutor);
    }

    @DeleteMapping("/instrutor/remove/{id}")
    private ResponseEntity<String> deletaInstrutorPorId(@PathVariable String id) {

	this.instrutorService.deletaInstrutorPorId(Long.parseLong(id));

	return new ResponseEntity<>("Instrutor removido com sucesso", HttpStatus.ACCEPTED);
    }
}
