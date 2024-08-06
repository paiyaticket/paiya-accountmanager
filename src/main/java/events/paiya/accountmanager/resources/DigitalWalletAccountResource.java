package events.paiya.accountmanager.resources;

import events.paiya.accountmanager.enumerations.DigitalWalletProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class DigitalWalletAccountResource  extends CashAccountResource {
    private String email;
    private String phoneNumber;
    private DigitalWalletProvider digitalWalletProvider;
}
