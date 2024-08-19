package events.paiya.accountmanager.resources;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountResource extends CashAccountResource {
    @Size(max = 3)
    private String bankCode; // numero d'institution
    @Size(min = 7, max = 12)
    private String accountNumber; // numero de compte
    @Size(max = 5)
    private String succusale; // numero de succussale
    @Size(min = 14, max = 34)
    private String iban; //  numero de compte dans le systeme europeen
}
