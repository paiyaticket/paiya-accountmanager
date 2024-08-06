package events.paiya.accountmanager.domains;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount extends CashAccount{
    private String banqueCode;
    private String accountNumber;
    private String checkNumber;
    private String iban;
    private String bicSwift;
}
