package senaifit.DTO;

import java.time.LocalDate;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import senaifit.entities.Sexo;
import senaifit.entities.TipoUsuario;

@Data
@MappedSuperclass
public abstract class PessoaDTO extends UsuarioDTO {

    private String cpf;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Past
    private LocalDate dataNasc;

    private Sexo sexo;

    private double peso;

    private double altura;

    public PessoaDTO(TipoUsuario tipoUsuario) {
	super(tipoUsuario);
    }
}
