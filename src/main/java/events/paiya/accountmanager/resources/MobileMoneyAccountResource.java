package events.paiya.accountmanager.resources;

import events.paiya.accountmanager.enumerations.MobileMoneyProvider;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MobileMoneyAccountResource extends CashAccountResource {
    private String countryPrefixNumber;
    private String phoneNumber;
    private MobileMoneyProvider mobileMoneyProvider;
}
