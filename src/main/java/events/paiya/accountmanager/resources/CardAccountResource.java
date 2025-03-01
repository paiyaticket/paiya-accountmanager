package events.paiya.accountmanager.resources;

import events.paiya.accountmanager.enumerations.CardProvider;
import jakarta.validation.constraints.Size;
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
public class CardAccountResource extends CashAccountResource {
    @Size(min = 16, max = 19)
    private String cardNumber;
    private String expirationDate;
    private CardProvider provider;
}
