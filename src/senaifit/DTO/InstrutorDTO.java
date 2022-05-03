package senaifit.DTO;

import lombok.Data;
import senaifit.entities.TipoUsuario;

@Data
public class InstrutorDTO extends PessoaDTO {

    private String numRegistro;

    public InstrutorDTO() {
	super(TipoUsuario.INSTRUTOR);
    }
}
