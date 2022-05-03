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

import senaifit.DTO.ParceiroDTO;
import senaifit.entities.Parceiro;
import senaifit.services.ParceiroService;

@RestController
public class ParceiroController {

    @Autowired
    private ParceiroService parceiroService;

    @PostMapping("/parceiro/")
    public ResponseEntity<String> salvaParceiro(@Valid @RequestBody ParceiroDTO parceiroDTO) {

	Parceiro parceiro = new Parceiro();
	parceiro.setEndereco(parceiroDTO.getEndereco());
	parceiro.setNome(parceiroDTO.getNome());
	parceiro.setDataCadastro(parceiroDTO.getDataCadastro());
	parceiro.setId(parceiroDTO.getId());

	this.parceiroService.cadastraParceiro(parceiro);

	return new ResponseEntity<>("Parceiro cadastrado com sucesso!", HttpStatus.CREATED);
    }

    @GetMapping("/parceiro/{id}")
    public ResponseEntity<Parceiro> obtemParceiro(@PathVariable String id) {

	Optional<Parceiro> parceiro = this.parceiroService.obtemParceiro(Long.parseLong(id));
	return ResponseEntity.of(parceiro);
    }

    @DeleteMapping("/parceiro/remove/{id}")
    private ResponseEntity<String> deletaParceiroPorId(@PathVariable String id) {

	this.parceiroService.deletaParceiroPorId(Long.parseLong(id));

	return new ResponseEntity<>("Parceiro removido com sucesso", HttpStatus.ACCEPTED);
    }
}
