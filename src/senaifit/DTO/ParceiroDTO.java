package senaifit.DTO;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import senaifit.entities.Endereco;
import senaifit.entities.TipoUsuario;

@Data
public class ParceiroDTO {

    private Long id;

    @NotEmpty
    private String nome;

    @NotNull
    private Endereco endereco;

    @NotEmpty
    private TipoUsuario tipoUsuario;

    @NotNull
    private LocalDateTime dataCadastro;

    public ParceiroDTO(@NotEmpty TipoUsuario tipoUsuario, @NotNull LocalDateTime dataCadastro) {
	this.tipoUsuario = tipoUsuario;
	this.dataCadastro = dataCadastro;
    }
}
