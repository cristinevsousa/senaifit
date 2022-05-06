package senaifit.DTO;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import senaifit.entities.Endereco;
import senaifit.entities.Sexo;

@Data
public class ClienteDTO {

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

    @NotNull
    private Sexo sexo;

    @NotNull
    private double peso;

    @NotNull
    private double altura;
}
