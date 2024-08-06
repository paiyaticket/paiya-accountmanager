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
    @Size(max = 9)
    private String banqueCode;
    @Size(min = 9, max = 12)
    private String accountNumber;
    @Size(min = 3, max = 4)
    private String checkNumber;
    @Size(min = 27, max = 30)
    private String iban;
    @Size(min = 8, max = 12)
    private String bicSwift;
}
