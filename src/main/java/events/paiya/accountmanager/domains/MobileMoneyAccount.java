package events.paiya.accountmanager.domains;

import events.paiya.accountmanager.enumerations.MobileMoneyProvider;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MobileMoneyAccount extends CashAccount{
    private String countryPrefixNumber;
    private String phoneNumber;
    private MobileMoneyProvider mobileMoneyProvider;

    @Override
    public String toString() {
        return "MobileMoneyAccount{" +
                "countryPrefixNumber='" + countryPrefixNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", mobileMoneyProvider=" + mobileMoneyProvider +
                "} " + super.toString();
    }
}
