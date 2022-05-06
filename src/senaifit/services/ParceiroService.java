package senaifit.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import senaifit.DTO.ParceiroDTO;
import senaifit.entities.Parceiro;
import senaifit.repositories.ParceiroRepository;

@Service
public class ParceiroService {

    private ParceiroRepository parceiroRepo;

    public ParceiroService(ParceiroRepository parceiroRepo) {
	this.parceiroRepo = parceiroRepo;
    }

    public String cadastraParceiro(ParceiroDTO parceiroDTO) {

	Parceiro parceiro = new Parceiro();
	parceiro.setEndereco(parceiroDTO.getEndereco());
	parceiro.setNome(parceiroDTO.getNome());
	parceiro.setDataCadastro(parceiroDTO.getDataCadastro());
	parceiro.setId(parceiroDTO.getId());

	if (!(parceiro.getNome().isEmpty() && parceiro.equals(null))) {
	    this.parceiroRepo.save(parceiro);
	    return "Parceiro cadastrado com sucesso";
	}

	return "Erro ao cadastrar parceiro";
    }

    public Optional<Parceiro> obtemParceiro(long parceiroId) {

	Optional<Parceiro> parceiro = this.parceiroRepo.findById(parceiroId);

	if (parceiro.isPresent()) {
	    return parceiro;
	}
	return Optional.empty();
    }

    public boolean deletaParceiroPorId(long parceiroId) {

	Optional<Parceiro> parceiro = obtemParceiro(parceiroId);
	if (parceiro.isPresent()) {
	    this.parceiroRepo.deleteById(parceiroId);
	    return true;
	}
	return false;
    }
}
