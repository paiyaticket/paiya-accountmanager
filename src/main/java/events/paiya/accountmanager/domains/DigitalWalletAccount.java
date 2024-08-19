package events.paiya.accountmanager.domains;

import events.paiya.accountmanager.enumerations.DigitalWalletProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DigitalWalletAccount extends CashAccount{
    private String email;
    private String phoneNumber;
    private DigitalWalletProvider digitalWalletProvider;
}
