package events.paiya.accountmanager.domains;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount extends CashAccount{
    private String bankCode;
    private String accountNumber;
    private String succusale;
    private String iban;
    private String bicSwift;
}
