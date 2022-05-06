package senaifit.DTO;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import senaifit.entities.Endereco;
import senaifit.entities.Sexo;
import senaifit.entities.TipoUsuario;

@Data
public class InstrutorDTO {

    private Long id;

    @NotEmpty
    private String nome;

    @NotNull
    private Endereco endereco;

    @NotEmpty
    private String cpf;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Past
    @NotNull
    private LocalDate dataNasc;

    @NotEmpty
    private Sexo sexo;

    @NotNull
    private double peso;

    @NotNull
    private double altura;

    @NotEmpty
    private String numRegistro;

    @NotNull
    private TipoUsuario tipoUsuario;

    public InstrutorDTO() {
	this.tipoUsuario = TipoUsuario.INSTRUTOR;
    }
}
