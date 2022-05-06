package senaifit.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import senaifit.DTO.AtividadeDTO;
import senaifit.entities.Atividade;
import senaifit.repositories.AtividadeRepository;

@Service
public class AtividadeService {

    private AtividadeRepository atividadeRepo;

    public AtividadeService(AtividadeRepository atividadeRepo) {
	this.atividadeRepo = atividadeRepo;
    }

    public String cadastraAtividade(@Valid @RequestBody AtividadeDTO atividadeDTO) {

	Atividade atividade = new Atividade();
	atividade.setNome(atividadeDTO.getNome());

	if (!(atividade.getNome().isEmpty() && atividade.equals(null))) {
	    this.atividadeRepo.save(atividade);
	    return "Atividade cadastrada com sucesso";
	}
	return "Erro ao cadastrar atividade";
    }

    public Optional<Atividade> obtemAtividade(long atividadeId) {

	Optional<Atividade> atividade = this.atividadeRepo.findById(atividadeId);

	if (atividade.isPresent()) {
	    return atividade;
	}
	return Optional.empty();
    }

    public boolean deletaAtividadePorId(long atividadeId) {

	Optional<Atividade> atividade = obtemAtividade(atividadeId);
	if (atividade.isPresent()) {
	    this.atividadeRepo.deleteById(atividadeId);
	    return true;
	}
	return false;
    }
}
