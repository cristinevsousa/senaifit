package senaifit.DTO;

import lombok.Data;
import senaifit.entities.Endereco;
import senaifit.entities.TipoUsuario;

@Data
public abstract class UsuarioDTO {

    private Long id;
    private String nome;
    private Endereco endereco;
    private TipoUsuario tipoUsuario;

    public UsuarioDTO(TipoUsuario tipoUsuario) {
	this.tipoUsuario = tipoUsuario;
    }
}
