package senaifit.controllers;

import java.util.List;
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

import senaifit.DTO.CheckinDTO;
import senaifit.entities.Atividade;
import senaifit.entities.Checkin;
import senaifit.entities.Cliente;
import senaifit.entities.Parceiro;
import senaifit.services.AtividadeService;
import senaifit.services.CheckinService;
import senaifit.services.ClienteService;
import senaifit.services.ParceiroService;

@RestController
public class CheckinController {

    @Autowired
    private CheckinService checkinService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ParceiroService parceiroService;

    @Autowired
    private AtividadeService atividadeService;

    @PostMapping("/checkin/")
    public ResponseEntity<String> salvaCheckin(@Valid @RequestBody CheckinDTO checkinDTO) {

	long clienteId = checkinDTO.getClienteId();

	Optional<Cliente> cliente = this.clienteService.obtemCliente(clienteId);

	if (cliente.isEmpty()) {
	    return new ResponseEntity<>("Requisiçao inválida, CLIENTE não encontrado!", HttpStatus.BAD_REQUEST);
	}

	long parceiroId = checkinDTO.getParceiroId();

	Optional<Parceiro> parceiro = this.parceiroService.obtemParceiro(parceiroId);

	if (parceiro.isEmpty()) {
	    return new ResponseEntity<>("Requisiçao inválida, PARCEIRO não encontrado!", HttpStatus.BAD_REQUEST);
	}

	Optional<Atividade> atividade = this.atividadeService.obtemAtividade(1);

	if (atividade.isEmpty()) {
	    return new ResponseEntity<>("Requisiçao inválida, ATIVIDADE não encontrada!", HttpStatus.BAD_REQUEST);
	}

	Checkin checkin = new Checkin();

	String msg = checkinService.verificaCheckin(cliente, checkin);

	checkin.setCliente(cliente.get());
	checkin.setParceiro(parceiro.get());
	checkin.setAtividade(atividade.get());
	checkin.setId(clienteId);

	this.checkinService.salvaCheckin(checkin);

	return new ResponseEntity<>("Checkin realizado com sucesso! " + msg, HttpStatus.CREATED);
    }

    @GetMapping("/checkins/")
    public ResponseEntity<List<Checkin>> listaCheckins(@PathVariable String id) {

	List<Checkin> checkins = this.checkinService.listaTodosCheckins();

	return ResponseEntity.ok(checkins);
    }

    @GetMapping("/checkins/{mes}/{ano}")
    public ResponseEntity<List<Checkin>> listaCheckinsPorData(@PathVariable String mes, @PathVariable String ano) {

	List<Checkin> checkins = this.checkinService.listaCheckinsPorData(Integer.parseInt(mes), Integer.parseInt(ano));

	return ResponseEntity.ok(checkins);
    }

    @DeleteMapping("/checkin/remove/{id}")
    private ResponseEntity<String> deletaCheckinPorId(@PathVariable String id) {

	this.checkinService.deletaCheckinPorId(Long.parseLong(id));

	return new ResponseEntity<>("Checkin removido com sucesso", HttpStatus.ACCEPTED);
    }
}
