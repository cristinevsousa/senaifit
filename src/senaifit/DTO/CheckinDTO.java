package senaifit.DTO;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CheckinDTO {

    private long id;

    @NotNull
    private long clienteId;

    @NotNull
    private long parceiroId;
}
