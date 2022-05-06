package senaifit.DTO;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class AtividadeDTO {

    private Long id;

    @NotEmpty
    private String nome;
}
