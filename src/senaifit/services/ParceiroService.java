package senaifit.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import senaifit.entities.Parceiro;
import senaifit.repositories.ParceiroRepository;

@Service
public class ParceiroService {

    @Autowired
    private ParceiroRepository parceiroRepo;

    public void cadastraParceiro(@Valid Parceiro parceiro) {

	this.parceiroRepo.save(parceiro);
    }

    public Optional<Parceiro> obtemParceiro(long parceiroId) {

	return this.parceiroRepo.findById(parceiroId);
    }

    public void deletaParceiroPorId(long id) {

	this.parceiroRepo.deleteById(id);
    }
}
