package events.paiya.accountmanager.domains;

import events.paiya.accountmanager.enumerations.CardProvider;
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
public class CardAccount extends CashAccount{
    private String cardNumber;
    private String expirationDate;
    private CardProvider provider;
}
